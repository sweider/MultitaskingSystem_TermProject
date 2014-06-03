/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.usertask;


public class UserTask implements IUserTask {

    private final ComplexityVector complexity;
    private final TaskUrgency urgency;
    private final int id;

    public UserTask(ComplexityVector complexity, TaskUrgency urgency, int id) {
        this.complexity = complexity;
        this.urgency = urgency;
        this.id = id;
    }
    
    @Override
    public IComplexityVector getComplexityVector() {
        return this.complexity;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public TaskUrgency getUrgency() {
        return this.urgency;
    }
    
}
