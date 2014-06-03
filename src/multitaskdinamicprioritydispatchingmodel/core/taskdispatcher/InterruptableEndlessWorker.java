/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.taskdispatcher;

/**
 *
 * @author alex
 */
public abstract class InterruptableEndlessWorker {
    protected boolean continueWorking;

    public InterruptableEndlessWorker() {
    }

    public void sendStopWorkingSignal() {
        this.continueWorking = false;
    }

    public final void run() {
        while (true) {
            if (!this.continueWorking) {
                finishWorkWhenPossible();
                return;
            }
            normalCicle();
        }
    }

    protected abstract void normalCicle();

    protected abstract void finishWorkWhenPossible();
    
}
