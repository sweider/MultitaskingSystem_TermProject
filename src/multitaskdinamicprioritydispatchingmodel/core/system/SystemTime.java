/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.system;


public class SystemTime implements ISystemTime, Runnable {
    //<editor-fold desc="singleton" defaultstate="collapsed">
    private static final SystemTime instance = new SystemTime();
    private SystemTime(){
        this.currentTime = 0;
    }
    public static SystemTime getInstance(){
        return SystemTime.instance;
    }
    //</editor-fold>
    
    private int currentTime;
    
    @Override
    public long getCurrentTime() {
        return this.currentTime;
    }

    @Override
    public void run() {
        while(true){
            this.currentTime += 1;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                assert false : "Wtf? oO";
            }
        }
    }
    
}
