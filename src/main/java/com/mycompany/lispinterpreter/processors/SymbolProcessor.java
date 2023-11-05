package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import com.mycompany.lispinterpreter.processors.SExpressionProcessor;

/**
 *
 * @author Carlex
 */
public class SymbolProcessor implements SExpressionProcessor {

    @Override
    public SExpression processSExpression(String expression) {
        final String symbolKey = expression.toUpperCase();
        
        return new Atom(symbolKey, AtomType.SYMBOL);
    }

    @Override
    public Boolean isProcessable(String expression) {
        return true;
    }
    
}
