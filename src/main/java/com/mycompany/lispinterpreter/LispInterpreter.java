package com.mycompany.lispinterpreter;

import com.mycompany.lispinterpreter.sexpressions.SExpression;
import com.mycompany.lispinterpreter.processors.SVectorProcessor;
import com.mycompany.lispinterpreter.processors.SymbolProcessor;
import com.mycompany.lispinterpreter.processors.BigDecimalAtomProcessor;
import com.mycompany.lispinterpreter.processors.BigIntegerAtomProcessor;
import com.mycompany.lispinterpreter.processors.ExpressionProcessor;
import com.mycompany.lispinterpreter.processors.StringAtomProcessor;
import com.mycompany.lispinterpreter.processors.SExpressionProcessor;
import com.mycompany.lispinterpreter.processors.SMapProcessor;
import com.mycompany.lispinterpreter.processors.SSetProcessor;
import java.util.List;

/**
 *
 * @author Carlex
 */
public class LispInterpreter {
    
    static List<SExpressionProcessor> processors = List.of(new SVectorProcessor(),
            new ExpressionProcessor(),
            new BigDecimalAtomProcessor(),
            new StringAtomProcessor(),
            new SSetProcessor(),
            new SMapProcessor(),
            new BigIntegerAtomProcessor(),
            new SymbolProcessor());

    public static SExpression tokenize(final String expression){
        return processors.stream()
                .filter(processor -> processor.isProcessable(expression))
                .findFirst()
                .map(processor -> processor.processSExpression(expression))
                .get();
    }
    
    
}
