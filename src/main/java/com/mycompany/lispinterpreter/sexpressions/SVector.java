package com.mycompany.lispinterpreter.sexpressions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author Carlex
 */
public class SVector extends ArrayList<SExpression> implements SExpression {
    
    public SVector(List<SExpression> elements){
        this.addAll(elements);
    }

    @Override
    public Object evaluate(Map<Atom, BiFunction<Map, List<SExpression>, Object>> context) {
        return this;
    }
    
}
