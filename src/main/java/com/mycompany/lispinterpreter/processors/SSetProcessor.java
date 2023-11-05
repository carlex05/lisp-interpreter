package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.LispInterpreter;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import com.mycompany.lispinterpreter.sexpressions.SSet;
import com.mycompany.lispinterpreter.TokenUtils;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Carlex
 */
public class SSetProcessor implements SExpressionProcessor {

    @Override
    public SExpression processSExpression(String expression) {
        List<String> tokens = TokenUtils.splitTokens(expression.substring(2, expression.length()-1));
        return new SSet(tokens.stream().map(LispInterpreter::tokenize).collect(Collectors.toSet()));
    }

    @Override
    public Boolean isProcessable(String expression) {
        return expression.startsWith("#{");
    }
    
}
