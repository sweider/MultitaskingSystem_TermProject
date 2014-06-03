/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.taskssender;

import java.util.ArrayList;
import java.util.List;
import multitaskdinamicprioritydispatchingmodel.core.taskdispatcher.ITaskDispatcher;
import multitaskdinamicprioritydispatchingmodel.core.usertask.IUserTask;
import multitaskdinamicprioritydispatchingmodel.core.usertask.IUserTaskFactory;

/**
 *
 * @author alex
 */
public class TasksSender implements Runnable {
    private static final int TASKS_COUNT = 5;
    
    private final IUserTaskFactory tasksFactory;
    private final ITaskDispatcher taskDispatcher;    
    private final List<IUserTask> tasks;

    public TasksSender(IUserTaskFactory tasksFactory, ITaskDispatcher taskDispatcher) {
        this.tasksFactory = tasksFactory;
        this.taskDispatcher = taskDispatcher;
        this.tasks = new ArrayList<>();
        createTasks();
    }
    
    @Override
    public void run() {
        for(IUserTask task : this.tasks){
            this.taskDispatcher.handleTask(task);
        }
    }

    private void createTasks() {
        for(int i = 0; i < TasksSender.TASKS_COUNT; i++){
            for(int j = 0; j < 3; j++)
                this.tasks.add(this.tasksFactory.getSimpleTask());
            this.tasks.add(this.tasksFactory.getNormalTask());
            this.tasks.add(this.tasksFactory.getComplexTask());
        }
    }
}
