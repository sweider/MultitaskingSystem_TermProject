/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.usertask;

/**
 *
 * @author alex
 */
public interface IUserTask {
    IComplexityVector getComplexityVector();
    TaskUrgency getUrgency();
    int getId();
}
