/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.usertask;

import java.util.Random;

/**
 *
 * @author alex
 */
public class UserTaskFactory implements IUserTaskFactory {
    private int currentId = -1;
    
    @Override
    public IUserTask getSimpleTask(){
        ComplexityVector cVector = new ComplexityVector();
        Random random = new Random();
        cVector.addParam(ComplexityParam.ADD, random.nextInt(50));
        cVector.addParam(ComplexityParam.MULT, random.nextInt(10));
        IUserTask result = new UserTask(cVector, TaskUrgency.NORMAL, ++currentId);
        return result;
    }
    @Override
    public IUserTask getNormalTask(){    
        ComplexityVector cVector = new ComplexityVector();
        Random random = new Random();
        cVector.addParam(ComplexityParam.ADD, random.nextInt(100));
        cVector.addParam(ComplexityParam.MULT, random.nextInt(50));
        cVector.addParam(ComplexityParam.CONDITIONS, random.nextInt(10));
        cVector.addParam(ComplexityParam.LOOPS, random.nextInt(15));
        IUserTask result = new UserTask(cVector, TaskUrgency.NORMAL, ++currentId);
        return result;
    }
    
    
    @Override
    public IUserTask getComplexTask(){
        ComplexityVector cVector = new ComplexityVector();
        Random random = new Random();
        cVector.addParam(ComplexityParam.ADD, random.nextInt(300));
        cVector.addParam(ComplexityParam.MULT, random.nextInt(150));
        cVector.addParam(ComplexityParam.CONDITIONS, random.nextInt(50));
        cVector.addParam(ComplexityParam.LOOPS, random.nextInt(50));
        cVector.addParam(ComplexityParam.GRAPHICAL_OUTPUT, random.nextInt(15));
        cVector.addParam(ComplexityParam.FILE_SYSTEM_USAGE, random.nextInt(15));
        IUserTask result = new UserTask(cVector, TaskUrgency.NORMAL, ++currentId);
        return result;
    }
}
