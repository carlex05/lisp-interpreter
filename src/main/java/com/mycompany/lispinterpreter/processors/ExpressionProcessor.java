package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.Expression;
import com.mycompany.lispinterpreter.LispInterpreter;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import com.mycompany.lispinterpreter.TokenUtils;
import com.mycompany.lispinterpreter.processors.SExpressionProcessor;
import java.util.List;

/**
 *
 * @author Carlex
 */
public class ExpressionProcessor implements SExpressionProcessor{

    @Override
    public SExpression processSExpression(String expression) {
        List<String> tokens = TokenUtils.splitTokens(expression.substring(1, expression.length()-1));
        final String symbolKey = tokens.get(0).toUpperCase();
        tokens.remove(0);
        List<SExpression> params = tokens.stream().map(LispInterpreter::tokenize).toList();
        return new Expression(new Atom(symbolKey, AtomType.SYMBOL), params);
    }

    @Override
    public Boolean isProcessable(String expression) {
        return expression.startsWith("(");
    }
    
}
