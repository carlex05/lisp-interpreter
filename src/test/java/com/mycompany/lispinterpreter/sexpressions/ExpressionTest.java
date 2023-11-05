/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.lispinterpreter.sexpressions;

import com.mycompany.lispinterpreter.LispInterpreter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Carlex
 */
public class ExpressionTest {
    
    public ExpressionTest() {
    }

    @Test
    public void testEvaluate() {
        Expression actual = (Expression)LispInterpreter.tokenize("(+ 1 2)");
        Map<Atom, BiFunction<Map, List<SExpression>, Object>> context = new HashMap<>();
        context.put(new Atom("+", AtomType.SYMBOL), (context2, params) -> ((BigInteger)((Atom)params.get(0)).evaluate(context2)).add((BigInteger)((Atom)params.get(1)).evaluate(context2)));
        assertEquals(BigInteger.valueOf(3), actual.evaluate(context));
    }
    
}
