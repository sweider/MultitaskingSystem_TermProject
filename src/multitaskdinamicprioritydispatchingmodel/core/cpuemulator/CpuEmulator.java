/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.cpuemulator;

import multitaskdinamicprioritydispatchingmodel.core.executabletask.IExecutableTask;
import multitaskdinamicprioritydispatchingmodel.core.executabletask.TaskExecutingState;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class CpuEmulator implements ICpuEmulator {
private static final Logger logger = LogManager.getLogger(CpuEmulator.class);
    @Override
    public void executeTask(IExecutableTask task, int timeQuant) {
        logger.info(timeQuant + " timeUnits for executing task#" + task.getId());
        try {
            Thread.sleep(0,timeQuant);
            task.substractExecutedTime(timeQuant);
            if(task.getRemainingTime() > 0) { 
                logger.warn("Task not finished!");
                task.setExecutingState(TaskExecutingState.NOT_FINISHED); 
            }
            else {
                logger.info("Task finished!");
                task.setExecutingState(TaskExecutingState.FINISHED);
            }
        } catch (InterruptedException ex) {
            logger.error("Executing interrupted!!", ex);
        }
    }


    
}
