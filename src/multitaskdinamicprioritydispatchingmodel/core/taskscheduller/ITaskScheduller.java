/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.taskscheduller;

import multitaskdinamicprioritydispatchingmodel.core.executabletask.ExecutableTask;

/**
 *
 * @author alex
 */
public interface ITaskScheduller {
    void scheduleTask(ExecutableTask task);
}
