/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.cpuemulator;

import multitaskdinamicprioritydispatchingmodel.core.executabletask.IExecutableTask;

/**
 *
 * @author alex
 */
public interface ICpuEmulator {
    void executeTask(IExecutableTask task, int timeQuant);
}
