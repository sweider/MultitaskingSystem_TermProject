/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.taskdispatcher;

import multitaskdinamicprioritydispatchingmodel.core.executabletask.ExecutableTask;
import multitaskdinamicprioritydispatchingmodel.core.usertask.IUserTask;

/**
 *
 * @author alex
 */
public interface IUserToExecutableTaskTransformer {

    ExecutableTask transformTask(IUserTask userTask);
    
}
