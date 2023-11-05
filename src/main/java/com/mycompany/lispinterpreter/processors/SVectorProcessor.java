package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.LispInterpreter;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import com.mycompany.lispinterpreter.sexpressions.SVector;
import com.mycompany.lispinterpreter.TokenUtils;
import com.mycompany.lispinterpreter.processors.SExpressionProcessor;
import java.util.List;

/**
 *
 * @author Carlex
 */
public class SVectorProcessor implements SExpressionProcessor {

    @Override
    public SExpression processSExpression(String expression) {
        List<String> tokens = TokenUtils.splitTokens(expression.substring(1, expression.length()-1));
        List<SExpression> params = tokens.stream().map(LispInterpreter::tokenize).toList();
        SVector vector = new SVector(params);
        return vector;
    }

    @Override
    public Boolean isProcessable(String expression) {
        return expression.startsWith("[");
    }
    
}
