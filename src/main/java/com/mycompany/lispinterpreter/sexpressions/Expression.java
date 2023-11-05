package com.mycompany.lispinterpreter.sexpressions;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @author Carlex
 */
public record Expression(
            Atom symbol,
            List<SExpression> params
        ) implements SExpression{

    @Override
    public Object evaluate(Map<Atom, BiFunction<Map, List<SExpression>, Object>> context) {
        return context.get(symbol).apply(context, params);
    }

}
