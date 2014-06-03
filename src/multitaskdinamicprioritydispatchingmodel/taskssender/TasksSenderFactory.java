/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.taskssender;

import multitaskdinamicprioritydispatchingmodel.core.taskdispatcher.ITaskDispatcher;
import multitaskdinamicprioritydispatchingmodel.core.usertask.IUserTaskFactory;

/**
 *
 * @author alex
 */
public class TasksSenderFactory {
    private final ITaskDispatcher dispatcher;
    private final IUserTaskFactory taskFactory;

    public TasksSenderFactory(ITaskDispatcher dispatcher, IUserTaskFactory taskFactory) {
        this.dispatcher = dispatcher;
        this.taskFactory = taskFactory;
    }
    
    public TasksSender getSender(){
        return new TasksSender(taskFactory, dispatcher);
    }
}
