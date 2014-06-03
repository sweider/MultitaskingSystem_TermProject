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
public interface IExecutableTask {
    int getNeededTime();
    int getRemainingTime();
    TaskExecutingState getExecutingState();
    void setExecutingState(TaskExecutingState state);

    void substractExecutedTime(int time);
}
