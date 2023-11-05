package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.sexpressions.SExpression;

/**
 *
 * @author Carlex
 */
public interface SExpressionProcessor {
    
    SExpression processSExpression(String expression);
    Boolean isProcessable(String expression);
    
}
