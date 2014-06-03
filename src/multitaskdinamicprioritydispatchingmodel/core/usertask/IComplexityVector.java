/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multitaskdinamicprioritydispatchingmodel.core.usertask;

import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author alex
 */
public interface IComplexityVector {
    void addParam(ComplexityParam param, int value);
    Set<Entry<ComplexityParam, Integer>> getValues();
}
