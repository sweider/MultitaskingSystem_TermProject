/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.taskdispatcher;

import java.util.HashMap;
import multitaskdinamicprioritydispatchingmodel.core.usertask.ComplexityParam;

/**
 *
 * @author alex
 */
abstract class AbstractComplexityWeightMapper {
    private final HashMap<ComplexityParam, Integer> storage;
    
    protected AbstractComplexityWeightMapper(){
        this.storage = new HashMap<>();
        initializeMapper();
    }
    
    protected abstract void initializeMapper();
    
    protected void addToStorage(ComplexityParam param, Integer weight){
        this.storage.put(param, weight);
    }
    
    public int getWeight(ComplexityParam param){
        assert this.storage != null : "Storage should not be null";
        assert param != null : "Param should not b enull";
        return this.storage.get(param);
    }
}
