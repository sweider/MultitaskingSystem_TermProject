/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.taskdispatcher;

import multitaskdinamicprioritydispatchingmodel.core.executabletask.ExecutableTask;
import multitaskdinamicprioritydispatchingmodel.core.system.ISystemTime;
import multitaskdinamicprioritydispatchingmodel.core.usertask.IUserTask;

/**
 *
 * @author alex
 */
public class UserTaskToExecutableTaskTransformer implements IUserToExecutableTaskTransformer {
    
    private final static int URGENT_DEADLINE_DELTA = 1000;
    private final static int NORMAL_DEADLINE_DELTA = 2000;
    private final static int NON_URGENT_DEADLINE_DELTA = 5000;
            
    private final IComplexityToTimeTransformer complexityTransformer;
    private final ISystemTime systemTime;

    public UserTaskToExecutableTaskTransformer(IComplexityToTimeTransformer complexityTransformer, ISystemTime time) {
        this.complexityTransformer = complexityTransformer;
        this.systemTime = time;
    }
    
    @Override
    public ExecutableTask transformTask(IUserTask userTask){
        ExecutableTask.Builder etBuilder = new ExecutableTask.Builder(userTask.getId());
        
        int neededTime = this.complexityTransformer.getTime(userTask.getComplexityVector());
        etBuilder.setNeededTime(neededTime);
        
        int deadlineTime;
        switch (userTask.getUrgency()) {
            case NORMAL: deadlineTime = NORMAL_DEADLINE_DELTA ;break;
            case NOT_URGENT: deadlineTime = NON_URGENT_DEADLINE_DELTA;break;
            case URGENT: deadlineTime = URGENT_DEADLINE_DELTA; break;
            default:
                throw new AssertionError("U should not be here *jedy*");
        }
        deadlineTime += neededTime + this.systemTime.getCurrentTime();
        etBuilder.setDeadlineTime(deadlineTime);
        return etBuilder.build();
    }
}
