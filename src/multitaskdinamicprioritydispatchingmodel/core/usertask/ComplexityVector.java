/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.usertask;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;


public class ComplexityVector implements IComplexityVector {

    private final HashMap<ComplexityParam, Integer> storage;
    public ComplexityVector(){
        this.storage = new HashMap<>();
    }
        
    @Override
    public void addParam(ComplexityParam param, int value) {
        this.storage.put(param, value);
    }

    @Override
    public Set<Entry<ComplexityParam, Integer>> getValues() {
        return this.storage.entrySet();
    }
    
}
