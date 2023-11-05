package com.mycompany.lispinterpreter.sexpressions;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public interface SExpression {
    
    
    
    Object evaluate(Map<Atom, BiFunction<Map, List<SExpression>, Object>> context);
    
}
