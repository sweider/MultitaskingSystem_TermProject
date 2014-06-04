/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.launcher;

import java.util.ArrayList;
import java.util.List;
import multitaskdinamicprioritydispatchingmodel.core.cpuemulator.CpuEmulator;
import multitaskdinamicprioritydispatchingmodel.core.cpuemulator.ICpuEmulator;
import multitaskdinamicprioritydispatchingmodel.core.system.ISystemTime;
import multitaskdinamicprioritydispatchingmodel.core.system.SystemTime;
import multitaskdinamicprioritydispatchingmodel.core.taskdispatcher.AbstractQueuelikeEndlessWorkableUnit;
import multitaskdinamicprioritydispatchingmodel.core.taskdispatcher.ComplexityToTimeTransformer;
import multitaskdinamicprioritydispatchingmodel.core.taskdispatcher.DefaultComplexityWeightMapper;
import multitaskdinamicprioritydispatchingmodel.core.taskdispatcher.IComplexityWeightMapper;
import multitaskdinamicprioritydispatchingmodel.core.taskdispatcher.TaskDispatcher;
import multitaskdinamicprioritydispatchingmodel.core.taskscheduller.ITaskScheduller;
import multitaskdinamicprioritydispatchingmodel.core.taskscheduller.MultiQueueAdaptivityTaskScheduller;
import multitaskdinamicprioritydispatchingmodel.core.taskscheduller.ShortestJobNextSheduller;
import multitaskdinamicprioritydispatchingmodel.core.usertask.IUserTaskFactory;
import multitaskdinamicprioritydispatchingmodel.core.usertask.UserTaskFactory;
import multitaskdinamicprioritydispatchingmodel.taskssender.TasksSenderFactory;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author alex
 */
public class LauncherBackground {
    private static final int SENDERS_COUNT = 10;

    public static void launch(String schedullerName) throws InterruptedException {
        DOMConfigurator.configure("./log4j.xml");  
        
        ICpuEmulator cpuEmulator = new CpuEmulator();
        ISystemTime systemTime = SystemTime.getInstance();                
        ITaskScheduller scheduller;
        switch (schedullerName) {
            case "Multiqueue system":
                scheduller =  new MultiQueueAdaptivityTaskScheduller(systemTime, cpuEmulator);
                break;
            case "Shortest job first": 
                scheduller = new ShortestJobNextSheduller(cpuEmulator);
                break;
            default:
                throw new AssertionError();
        }
        
        Thread schedullerThread = new Thread((Runnable) scheduller);
        schedullerThread.setName("Scheduller");
        schedullerThread.start();
        
        IComplexityWeightMapper mapper = new DefaultComplexityWeightMapper();
        ComplexityToTimeTransformer complexityTransformer = new ComplexityToTimeTransformer(mapper);
        TaskDispatcher dispatcher = new TaskDispatcher(scheduller, complexityTransformer, systemTime);
        Thread dispatcherThread = new Thread((Runnable) dispatcher); 
        dispatcherThread.setName("Dispatcher");
        dispatcherThread.start();
        
        IUserTaskFactory tasksFactory = new UserTaskFactory();
        TasksSenderFactory sendersFactory = new TasksSenderFactory(dispatcher, tasksFactory);
        
        List<Thread> sendersThreads = new ArrayList<>();
        for(int i = 0; i < SENDERS_COUNT; i++){
            Thread senderThread = new Thread(sendersFactory.getSender());
            senderThread.setName("Sender#" + i);
            sendersThreads.add(senderThread);
            senderThread.start();
        }
        
        for(Thread thread : sendersThreads){
            thread.join();
            System.out.println(thread.getName() + " finished");
        }
        
        dispatcher.sendStopWorkingSignal();
        dispatcherThread.join();
        System.out.println(dispatcherThread.getName() + " finished");
        
        ((AbstractQueuelikeEndlessWorkableUnit)scheduller).sendStopWorkingSignal();
        schedullerThread.join();
        System.out.println(schedullerThread.getName() + " finished");
        
        System.out.println("System successfully finished work");
    }
    
}
