package com.mycompany.lispinterpreter.sexpressions;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

/**
 *
 * @author Carlex
 */
public class SSet extends HashSet<SExpression> implements SExpression {
    public SSet(Set<SExpression> elements){
        this.addAll(elements);
    }

    @Override
    public Object evaluate(Map<Atom, BiFunction<Map, List<SExpression>, Object>> context) {
        return this;
    }
}
