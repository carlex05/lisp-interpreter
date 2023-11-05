package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import java.math.BigDecimal;

/**
 *
 * @author Carlex
 */
public class BigDecimalAtomProcessor implements SExpressionProcessor{

    @Override
    public SExpression processSExpression(String expression) {
        return new Atom(new BigDecimal(expression), AtomType.BIG_DECIMAL);
    }

    @Override
    public Boolean isProcessable(String expression) {
        char[] characters = expression.toCharArray();
        boolean isDecimal = false;
        for(int index = 0; index < characters.length; index++){
            boolean isDigit = Character.isDigit(characters[index]);
            boolean isNegativeSignAtStart = index == 0 && characters[index] == '-';
            boolean isFirstDecimalPoint = !isDecimal && characters[index] == '.';
            if(isFirstDecimalPoint)
                isDecimal = true;
            if(!isDigit && !isNegativeSignAtStart && !isFirstDecimalPoint){
                return false;
            }
        }
        return isDecimal;
    }
    
}
