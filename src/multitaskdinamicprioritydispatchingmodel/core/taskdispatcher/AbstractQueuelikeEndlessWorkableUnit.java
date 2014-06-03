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
public abstract class AbstractQueuelikeEndlessWorkableUnit {
    private boolean continueWorking;
    private final int WAITING_TIME;

   protected AbstractQueuelikeEndlessWorkableUnit(int waitingTime) {
        this.continueWorking = true;
        this.WAITING_TIME = waitingTime;
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

    protected void normalCicle(){
        this.waitForEntities();
        this.processExistingEntities();
        this.updateFromBuffer();
    };

    protected  void finishWorkWhenPossible(){
         while(true){
            this.processExistingEntities();
            this.updateFromBuffer();
            if(this.isMainQueueEmpty()) return;
        }
    }
    
    protected abstract boolean isMainQueueEmpty();

    protected abstract void processExistingEntities();

    protected abstract void updateFromBuffer();

    protected void waitForEntities() {
        while(this.isMainQueueEmpty()){
            try {
                Thread.sleep(this.WAITING_TIME);
                this.updateFromBuffer();
            } catch (InterruptedException ex) { 
                ex.printStackTrace(); 
            }
        }
    }
}
