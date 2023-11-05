package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Carlex
 */
public class BigIntegerAtomProcessor implements SExpressionProcessor {

    @Override
    public SExpression processSExpression(String expression) {
        return new Atom(new BigInteger(expression), AtomType.BIG_INTEGER);
    }

    @Override
    public Boolean isProcessable(String expression) {
        char[] characters = expression.toCharArray();
        for (int index = 0; index < characters.length; index++) {
            boolean isDigit = Character.isDigit(characters[index]);
            boolean isNegativeSignAtStart = index == 0 && characters[index] == '-';
            if (!isDigit && !isNegativeSignAtStart) {
                return false;
            }
        }
        return !expression.isEmpty() && !expression.equals("-");
    }

}
