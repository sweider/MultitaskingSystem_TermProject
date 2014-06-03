/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.usertask;

import java.util.HashMap;

/**
 *
 * @author alex
 */
public interface IComplexityVector {
    void addParam(ComplexityParam param, int value);
    HashMap<ComplexityParam, Integer> getValues();
}
