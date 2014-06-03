/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.taskscheduller;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import multitaskdinamicprioritydispatchingmodel.core.cpuemulator.ICpuEmulator;
import multitaskdinamicprioritydispatchingmodel.core.executabletask.ExecutableTask;
import multitaskdinamicprioritydispatchingmodel.core.executabletask.IExecutableTask;
import multitaskdinamicprioritydispatchingmodel.core.executabletask.ITaskForMultiQueueSystem;
import multitaskdinamicprioritydispatchingmodel.core.executabletask.TaskExecutingState;
import multitaskdinamicprioritydispatchingmodel.core.system.ISystemTime;
import multitaskdinamicprioritydispatchingmodel.core.taskdispatcher.AbstractQueuelikeEndlessWorkableUnit;


public class MultiQueueAdaptivityTaskScheduller extends AbstractQueuelikeEndlessWorkableUnit implements ITaskScheduller, Runnable {

    private static final int QUEUE_LVLS = 5;
    private static final int FIRST_LVL_QUEUE_TIME_QUANT = 50;
    private static final float EACH_LVL_QUANT_MULTIPLIER = 1.5f;
    private static final int WAITING_TIME = 10;    
    
    private final ISystemTime systemTime;
    private final ICpuEmulator cpu;
    
    private List<Queue<ITaskForMultiQueueSystem>> queueSystem;
    private List<ITaskForMultiQueueSystem> tasksBuffer;
    
    private boolean continueWorking;
    private int currentQueueIndex;

    public MultiQueueAdaptivityTaskScheduller(ISystemTime time, ICpuEmulator cpuEmulator) {
        super(MultiQueueAdaptivityTaskScheduller.WAITING_TIME);
        this.tasksBuffer = new ArrayList<>();
        this.queueSystem = new ArrayList<>();
        for (int i = 0; i < QUEUE_LVLS; i++){
            this.queueSystem.add(new ConcurrentLinkedQueue<ITaskForMultiQueueSystem>());
        }
        this.systemTime = time;
        this.cpu = cpuEmulator;
        this.continueWorking = true;
    }
    
    @Override
    public synchronized void scheduleTask(ExecutableTask task) {
        this.tasksBuffer.add(task);
    }

    @Override
    protected boolean isMainQueueEmpty() {
        for(Queue queue : this.queueSystem)
            if (!queue.isEmpty())
                return false;
        return true;
    }

    @Override
    protected void processExistingEntities() {
        ITaskForMultiQueueSystem nextTask = getNext();
        while(nextTask != null){
            executeTask(nextTask);
            nextTask = getNext();
        }
    }
    
    private ITaskForMultiQueueSystem getNext(){
        if(this.currentQueueIndex == 0){
            if(this.queueSystem.get(this.currentQueueIndex).isEmpty()){
                if(this.tasksBuffer.isEmpty()){
                    this.currentQueueIndex++;
                    return getNext();
                }
                else{
                    this.updateFromBuffer();
                    return getNext();
                }
            }
            else{
                return getTaskFromCurrentQueue();
            }
        }
        else{
            if(this.tasksBuffer.isEmpty()){
                if(this.queueSystem.get(this.currentQueueIndex).isEmpty()){
                    if(this.isAtLastLvl()){
                        return null;
                    }
                    else{
                        this.currentQueueIndex++;
                        return getNext();
                    }
                }
                else{
                    return getTaskFromCurrentQueue();
                }
            }
            else{
                this.updateFromBuffer();
                return getNext();
            }
        }
    }

    private ITaskForMultiQueueSystem getTaskFromCurrentQueue() {
        return this.queueSystem.get(this.currentQueueIndex).poll();
    }
    
    private boolean isAtLastLvl(){
        return this.currentQueueIndex >= MultiQueueAdaptivityTaskScheduller.QUEUE_LVLS - 1;
    }
    
    private void executeTask(ITaskForMultiQueueSystem task){
        this.cpu.executeTask(task, getTimeQuantForCurrentQueue());
        switch (task.getExecutingState()) {
            case FINISHED: task.setLastQueueLvl(this.currentQueueIndex); break;
            case NOT_FINISHED: this.addToNextLvlQueue(task); break;
            default: assert false : "Check the cpuEmulator implementation. U should not be here!";
        }
        task.setExecutingState(TaskExecutingState.READY);
    }
    
    private void addToNextLvlQueue(ITaskForMultiQueueSystem task){
        if(this.isAtLastLvl()) { this.queueSystem.get(this.currentQueueIndex).add(task); }
        else { this.queueSystem.get(this.currentQueueIndex + 1).add(task); }
    }
    
    private int getTimeQuantForCurrentQueue(){
        float result = MultiQueueAdaptivityTaskScheduller.FIRST_LVL_QUEUE_TIME_QUANT;
        for(int i = 1; i <= MultiQueueAdaptivityTaskScheduller.QUEUE_LVLS; i++)
            result *= MultiQueueAdaptivityTaskScheduller.EACH_LVL_QUANT_MULTIPLIER;
        return (int) result;
    }

    @Override
    protected synchronized void updateFromBuffer() {
        this.queueSystem.get(0).addAll(this.tasksBuffer);
        this.tasksBuffer.clear();
        this.currentQueueIndex = 0;
    }
}
