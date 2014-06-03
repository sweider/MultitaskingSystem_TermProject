/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.taskdispatcher;

import java.util.Map.Entry;
import multitaskdinamicprioritydispatchingmodel.core.usertask.ComplexityParam;
import multitaskdinamicprioritydispatchingmodel.core.usertask.IComplexityVector;


public class ComplexityToTimeTransformer implements IComplexityToTimeTransformer {
    private static final float WEIGHT_TO_TIME_COEFFICIENT = 1.5f;
    private IComplexityWeightMapper mapper;

    public ComplexityToTimeTransformer(IComplexityWeightMapper mapper) {
        this.mapper = mapper;
    }

    public void setMapper(IComplexityWeightMapper mapper) {
        this.mapper = mapper;
    }
       
    @Override
    public int getTime(IComplexityVector complexityVector) {
        int weight = 0;
        for(Entry<ComplexityParam, Integer> entry : complexityVector.getValues()){
            weight += this.mapper.getWeight(entry.getKey()) * entry.getValue();
        }
        int result = (int) (weight * ComplexityToTimeTransformer.WEIGHT_TO_TIME_COEFFICIENT) + 1;
        return result;
    }
    
}
