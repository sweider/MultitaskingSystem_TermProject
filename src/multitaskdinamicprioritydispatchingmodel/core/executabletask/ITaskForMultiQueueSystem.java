/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.executabletask;

/**
 *
 * @author alex
 */
public interface ITaskForMultiQueueSystem extends IExecutableTask{
    static final int NOT_EXECUTED_YET = -1;
    int getLastQueueLvl();
    void setLastQueueLvl(int lvl);
}
