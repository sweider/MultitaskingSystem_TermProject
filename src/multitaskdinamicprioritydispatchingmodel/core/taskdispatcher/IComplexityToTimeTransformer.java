/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.taskdispatcher;

import multitaskdinamicprioritydispatchingmodel.core.usertask.IComplexityVector;

/**
 *
 * @author alex
 */
public interface IComplexityToTimeTransformer {
    int getTime(IComplexityVector complexityVector);
}
