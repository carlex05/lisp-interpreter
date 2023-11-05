package com.mycompany.lispinterpreter.sexpressions;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public record Atom<T>(
    T value,
    AtomType type
) implements SExpression {

    @Override
    public Object evaluate(Map<Atom, BiFunction<Map, List<SExpression>, Object>> context) {
        switch(type){
            case BIG_DECIMAL:
            case BIG_INTEGER:
            case STRING:
                return value;
            case SYMBOL:
                return context.get(this).apply(context, List.of());
            default: 
                throw new IllegalStateException();
        }
    }


}
