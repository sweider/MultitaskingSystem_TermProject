/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.executabletask;


public class ExecutableTask implements ITaskForMultiQueueSystem, ITaskForLlfOrEdfSystem,  IExecutableTask {    

    //<editor-fold desc="Builder" defaultstate="collapsed">
    public static class Builder{
        private static final int NOT_SETTED = -1;
        private int neededTime;
        private int deadlineTime;
        private final int id;

        public Builder(int id) {
            this.neededTime = NOT_SETTED;
            this.deadlineTime = NOT_SETTED;
            this.id = id;
        }

        
        public void setNeededTime(int neededTime) {
            this.neededTime = neededTime;
        }

        public void setDeadlineTime(int deadlineTime) {
            this.deadlineTime = deadlineTime;
        }
        
        public ExecutableTask build(){
            assert this.neededTime != NOT_SETTED : "needed time must be setted";
            assert this.deadlineTime != NOT_SETTED : "deadline time must be setted";
            return new ExecutableTask(id,neededTime, neededTime, deadlineTime);
        }
    }
    //</editor-fold>
    
    private final int neededTime;    
    private int remainingTime;
    private final int deadlineTime;
    private TaskExecutingState executingState;
    private int lastQueueLvl;
    private final int id;

    private ExecutableTask(int id,int neededTime, int remainingTime, int deadlineTime) {
        this.neededTime = neededTime;
        this.remainingTime = remainingTime;
        this.deadlineTime = deadlineTime;
        this.executingState = TaskExecutingState.READY;
        this.lastQueueLvl = ITaskForMultiQueueSystem.NOT_EXECUTED_YET;
        this.id = id;
    }
    
    //<editor-fold desc="IExecutableTask impl" defaultstate="collapsed">
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public int getNeededTime() {
        return this.neededTime;
    }

    @Override
    public int getRemainingTime() {
        return this.remainingTime;
    }

    @Override
    public TaskExecutingState getExecutingState() {
        return this.executingState;
    }

    @Override
    public void setExecutingState(TaskExecutingState state) {
        this.executingState = state;
    }
    
    @Override
    public void substractExecutedTime(int time){
        int remaining = this.remainingTime - time;
        this.remainingTime = remaining - time < 0 ? 0 : remaining;        
    }
    //</editor-fold>
    
    //<editor-fold desc="ITaskForMultiQueueSystem impl" defaultstate="collapsed">
    @Override
    public int getLastQueueLvl() {
        return this.lastQueueLvl;
    }

    @Override
    public void setLastQueueLvl(int lvl) {
        this.lastQueueLvl = lvl;
    }
    //</editor-fold>
    
    //<editor-fold desc="ITaskForLlfEdfSystem impl" defaultstate="collapsed">
    @Override
    public int getDeadlineTime() {
        return this.deadlineTime;
    }
    //</editor-fold>
    
}
