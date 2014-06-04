/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.taskscheduller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import multitaskdinamicprioritydispatchingmodel.core.cpuemulator.ICpuEmulator;
import multitaskdinamicprioritydispatchingmodel.core.executabletask.ExecutableTask;
import multitaskdinamicprioritydispatchingmodel.core.executabletask.IExecutableTask;
import multitaskdinamicprioritydispatchingmodel.core.taskdispatcher.AbstractQueuelikeEndlessWorkableUnit;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class ShortestJobNextSheduller extends AbstractQueuelikeEndlessWorkableUnit implements ITaskScheduller, Runnable {
    private static final Logger logger = LogManager.getLogger(ShortestJobNextSheduller.class);
    private static final int WAITING_TIME = 20;
    private final List<IExecutableTask> tasks;
    private final List<IExecutableTask> buffer;
    private final ICpuEmulator cpu;

    public ShortestJobNextSheduller(ICpuEmulator cpu) {
        super(WAITING_TIME);
        this.cpu = cpu;
        ArrayList<IExecutableTask> buffer = new ArrayList<>();
        this.buffer = Collections.synchronizedList(buffer);
        this.tasks = new ArrayList<>();
        logger.info("Scheduller created");
    }
    
    
    
    @Override
    public void scheduleTask(ExecutableTask task) {
        for(int i = 0; i < this.tasks.size(); i++){
            if(task.getNeededTime() <= this.tasks.get(i).getNeededTime()){
                this.tasks.add(i, task);
                logger.info("Task#" + task.getId() + " inserted at index#" + i);
                return;
            }
        }
         this.tasks.add(task);   
         logger.info("Task#" + task.getId() + " inserted in the end of queue");
    }

    
    
    @Override
    protected boolean isMainQueueEmpty() {
        return this.tasks.isEmpty();
    }

    @Override
    protected void processExistingEntities() {
        while(!this.tasks.isEmpty()){                        
            IExecutableTask task = this.tasks.get(0);
            logger.info("Task#" + task.getId() + " start executing");
            this.cpu.executeTask(task, task.getNeededTime());
            this.tasks.remove(task);
            logger.info("Task#" + task.getId() + " executed");
        }
    }

    @Override
    protected void updateFromBuffer() {
        //do nothing
    }
    
}
