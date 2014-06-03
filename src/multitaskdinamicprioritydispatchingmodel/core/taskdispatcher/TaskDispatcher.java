/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.taskdispatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import multitaskdinamicprioritydispatchingmodel.core.executabletask.ExecutableTask;
import multitaskdinamicprioritydispatchingmodel.core.system.ISystemTime;
import multitaskdinamicprioritydispatchingmodel.core.taskscheduller.ITaskScheduller;
import multitaskdinamicprioritydispatchingmodel.core.usertask.IUserTask;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class TaskDispatcher extends AbstractQueuelikeEndlessWorkableUnit implements ITaskDispatcher, Runnable {
    private static final Logger logger  = LogManager.getLogger(TaskDispatcher.class);
    private static final int WAITING_TIME = 10;
    private final ITaskScheduller taskScheduller;
    private final IUserToExecutableTaskTransformer taskTransformer;
    
    private final List<IUserTask> userTasks;
    private final List<IUserTask> tasksBuffer;

    public TaskDispatcher(ITaskScheduller taskScheduller, IComplexityToTimeTransformer complexityToTimeTransformer, ISystemTime systemTime) {
        super(TaskDispatcher.WAITING_TIME);
        this.taskScheduller = taskScheduller;
        this.taskTransformer = new UserTaskToExecutableTaskTransformer(complexityToTimeTransformer, systemTime);
        this.userTasks = new ArrayList<>();
        List<IUserTask> accummulatedTasksList = new ArrayList<>();
        this.tasksBuffer = Collections.synchronizedList(accummulatedTasksList);
        logger.info("Dispatcher created");
    }    
        
    @Override
    public synchronized void handleTask(IUserTask task) {
        this.tasksBuffer.add(task);
        logger.info("Added task#" + task.getId());
    }
    
    @Override
    protected void processExistingEntities() {
        for(IUserTask task : this.userTasks){
            ExecutableTask exTask = this.taskTransformer.transformTask(task);
            this.taskScheduller.scheduleTask(exTask);
            logger.info("Task#" + exTask.getId() + " sent to scheduller");
        }
        this.userTasks.clear();
    }
    
    @Override
     protected synchronized void updateFromBuffer(){
        this.userTasks.addAll(this.tasksBuffer);
        this.tasksBuffer.clear();
    }

    @Override
    protected boolean isMainQueueEmpty() {
        return this.userTasks.isEmpty();
    }
    
}
