package com.mycompany.lispinterpreter.sexpressions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author Carlex
 */
public class SMap extends HashMap<SExpression, SExpression> implements SExpression{
    
    public SMap(Map<SExpression, SExpression> elements){
        this.putAll(elements);
    }

    @Override
    public Object evaluate(Map<Atom, BiFunction<Map, List<SExpression>, Object>> context) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
