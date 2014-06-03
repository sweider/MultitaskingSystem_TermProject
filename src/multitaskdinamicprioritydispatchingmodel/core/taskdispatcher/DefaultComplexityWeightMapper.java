/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.taskdispatcher;

import multitaskdinamicprioritydispatchingmodel.core.usertask.ComplexityParam;


public class DefaultComplexityWeightMapper extends AbstractComplexityWeightMapper implements IComplexityWeightMapper{

    public DefaultComplexityWeightMapper() {
        super();
    }

    
    @Override
    protected void initializeMapper() {
        this.addToStorage(ComplexityParam.ADD, 1);
        this.addToStorage(ComplexityParam.MULT, 3);
        this.addToStorage(ComplexityParam.CONDITIONS, 10);
        this.addToStorage(ComplexityParam.LOOPS, 13);
        this.addToStorage(ComplexityParam.GRAPHICAL_OUTPUT, 20);
        this.addToStorage(ComplexityParam.FILE_SYSTEM_USAGE, 25);
    }
    
}
