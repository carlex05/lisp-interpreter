package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import com.mycompany.lispinterpreter.processors.SExpressionProcessor;

/**
 *
 * @author Carlex
 */
public class StringAtomProcessor implements SExpressionProcessor {

    @Override
    public SExpression processSExpression(String expression) {
        return new Atom(expression.substring(1, expression.length()-1), AtomType.STRING);   
    }

    @Override
    public Boolean isProcessable(String expression) {
        return expression.startsWith("\"");
    }
    
}
