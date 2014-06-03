/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.cpuemulator;

import java.util.logging.Level;
import java.util.logging.Logger;
import multitaskdinamicprioritydispatchingmodel.core.executabletask.IExecutableTask;
import multitaskdinamicprioritydispatchingmodel.core.executabletask.TaskExecutingState;


public class CpuEmulator implements ICpuEmulator {

    @Override
    public void executeTask(IExecutableTask task, int timeQuant) {
        try {
            Thread.sleep(0,timeQuant);
            task.substractExecutedTime(timeQuant);
            if(task.getRemainingTime() > 0) task.setExecutingState(TaskExecutingState.NOT_FINISHED);
            else task.setExecutingState(TaskExecutingState.FINISHED);
        } catch (InterruptedException ex) {
            Logger.getLogger(CpuEmulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
}
