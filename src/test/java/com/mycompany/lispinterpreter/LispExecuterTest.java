package com.mycompany.lispinterpreter;

import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Carlex
 */
public class LispExecuterTest {

    public LispExecuterTest() {
    }
    
    @Test
    public void testExecute_fibonacci() {
        SExpression expression = LispInterpreter.tokenize("(defn fibonacci [n] (if (<= n 1) n (+ (fibonacci (- n 1)) (fibonacci (- n 2)))))");
        LispExecuter executer = new LispExecuter();
        executer.execute(expression);
        expression = LispInterpreter.tokenize("(fibonacci 7)");
        Object actual = executer.execute(expression);
        assertEquals(BigInteger.valueOf(13), actual);
    }

    @Test
    public void testExecute_addIntegers() {
        SExpression expression = LispInterpreter.tokenize("(+ 2.5 3.2)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigDecimal.valueOf(5.7), actual);
    }

    @Test
    public void testExecute_addIntegers2() {
        SExpression expression = LispInterpreter.tokenize("(+ 3 5)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigInteger.valueOf(8), actual);
    }

    @Test
    public void testExecute_addIntegers3() {
        SExpression expression = LispInterpreter.tokenize("(+ 3 5 9223372036854775799)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigInteger.valueOf(9223372036854775807L), actual);
    }

    @Test
    public void testExecute_addDecimals3() {
        SExpression expression = LispInterpreter.tokenize("(+ 3 5.0 9223372036854775799)");
        Object actual = new LispExecuter().execute(expression);
        BigDecimal expected = BigDecimal.valueOf(9223372036854775807L);
        assertEquals(0, expected.compareTo((BigDecimal) actual), "Expected: <" + expected + "> but was: <" + actual + ">");
    }

    @Test
    public void testExecute_addIntegers4() {
        SExpression expression = LispInterpreter.tokenize("(+ 3 5 78 90)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigInteger.valueOf(176), actual);
    }

    @Test
    public void testExecute_addIntegers5() {
        SExpression expression = LispInterpreter.tokenize("(+ 3 5 7.8 90)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(0, BigDecimal.valueOf(105.8).compareTo((BigDecimal) actual));
    }

    @Test
    public void testExecute_addIntegers6() {
        SExpression expression = LispInterpreter.tokenize("(+ 3.1 5.2 7.8 90.5)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigDecimal.valueOf(106.6), actual);
    }

    @Test
    public void testExecute_substractionIntegersWithOnePositiveParam() {
        SExpression expression = LispInterpreter.tokenize("(- 2)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigInteger.valueOf(-2), actual);
    }

    @Test
    public void testExecute_substractionIntegersWithOnePositiveParamBigDecimal() {
        SExpression expression = LispInterpreter.tokenize("(- 2.7)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigDecimal.valueOf(-2.7), actual);
    }

    @Test
    public void testExecute_substractionIntegersWithOneNegativeParam() {
        SExpression expression = LispInterpreter.tokenize("(- -2)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigInteger.valueOf(2), actual);
    }

    @Test
    public void testExecute_substraction2Parameters() {
        SExpression expression = LispInterpreter.tokenize("(- 3 -5)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigInteger.valueOf(8), actual);
    }

    @Test
    public void testExecute_substraction3Parameters() {
        SExpression expression = LispInterpreter.tokenize("(- 3 5 6)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigInteger.valueOf(-8), actual);
    }

    @Test
    public void testExecute_substractionNParameters() {
        SExpression expression = LispInterpreter.tokenize("(- 3 5 78 -90)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigInteger.valueOf(10), actual);
    }

    @Test
    public void testExecute_substractionNParametersBigDecimal() {
        SExpression expression = LispInterpreter.tokenize("(- 3.9 5.8 7.8 -90.2)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigDecimal.valueOf(80.5), actual);
    }

    @Test
    public void testExecute_substractionNParametersBigDecimalAndBigInteger() {
        SExpression expression = LispInterpreter.tokenize("(- 3 5.8 7 -90.2)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigDecimal.valueOf(80.4), actual);
    }

    @Test
    public void testExecute_multiplicationEmpty() {
        SExpression expression = LispInterpreter.tokenize("(*)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigInteger.valueOf(1), actual);
    }

    @Test
    public void testExecute_multiplicationIntegers2() {
        SExpression expression = LispInterpreter.tokenize("(* 3)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigInteger.valueOf(3), actual);
    }

    @Test
    public void testExecute_multiplicationIntegers3() {
        SExpression expression = LispInterpreter.tokenize("(* 3 5)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigInteger.valueOf(15), actual);
    }

    @Test
    public void testExecute_multiplicationIntegers4() {
        SExpression expression = LispInterpreter.tokenize("(* 3 5 78 90)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(BigInteger.valueOf(105300), actual);
    }

    @Test
    public void testExecute_multiplicationDouble2() {
        SExpression expression = LispInterpreter.tokenize("(* .2)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(0, BigDecimal.valueOf(0.2).compareTo((BigDecimal) actual));
    }

    @Test
    public void testExecute_multiplicationDouble3() {
        SExpression expression = LispInterpreter.tokenize("(* 3. 5)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(0, BigDecimal.valueOf(15).compareTo((BigDecimal) actual));
    }

    @Test
    public void testExecute_multiplicationDouble4() {
        SExpression expression = LispInterpreter.tokenize("(* 3 5 7.8 9.0)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(0, BigDecimal.valueOf(1053).compareTo((BigDecimal) actual));
    }

    @Test
    public void testExecute_divisionOneParam() {
        SExpression expression = LispInterpreter.tokenize("(/ 2)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(0, BigDecimal.valueOf(0.5).compareTo((BigDecimal) actual));
    }

    @Test
    public void testExecute_division2Integers() {
        SExpression expression = LispInterpreter.tokenize("(/ 6 2)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(0, BigDecimal.valueOf(3).compareTo((BigDecimal) actual));
    }

    @Test
    public void testExecute_division3Integers() {
        SExpression expression = LispInterpreter.tokenize("(/ 16 2 4)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(0, BigDecimal.valueOf(2).compareTo((BigDecimal) actual));
    }

    @Test
    public void testExecute_divisionNIntegers() {
        SExpression expression = LispInterpreter.tokenize("(/ 20 5 1 2)");
        Object actual = new LispExecuter().execute(expression);
        assertEquals(0, BigDecimal.valueOf(2).compareTo((BigDecimal) actual));
    }

    @Test
    public void testExecute_defnDouble() {
        SExpression expression = LispInterpreter.tokenize("(defn double [n] (* 2 n))");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertEquals("DOUBLE", actual);
        assertNotNull(executer.context.get(new Atom<>("DOUBLE", AtomType.SYMBOL)));
        SExpression doubleFn = LispInterpreter.tokenize("(double 2)");
        Object doubleActual = executer.execute(doubleFn);
        assertEquals(BigInteger.valueOf(4), doubleActual);
    }

    @Test
    public void testExecute_defnDoublen() {
        SExpression expression = LispInterpreter.tokenize("(defn doublen [n] (* 2 n))");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertEquals("DOUBLEN", actual);
        assertNotNull(executer.context.get(new Atom<>("DOUBLEN", AtomType.SYMBOL)));
        SExpression doubleFn = LispInterpreter.tokenize("(doublen 3)");
        Object doubleActual = executer.execute(doubleFn);
        assertEquals(BigInteger.valueOf(6), doubleActual);
    }

    @Test
    public void testExecute_defnDoubleDecimal() {
        SExpression expression = LispInterpreter.tokenize("(defn double [n] (* 2 n))");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertEquals("DOUBLE", actual);
        assertNotNull(executer.context.get(new Atom<>("DOUBLE", AtomType.SYMBOL)));
        SExpression doubleFn = LispInterpreter.tokenize("(double 2.5)");
        Object doubleActual = executer.execute(doubleFn);
        assertEquals(0, BigDecimal.valueOf(5).compareTo((BigDecimal) doubleActual));
    }

    @Test
    public void testExecute_defnDoublenDecimal() {
        SExpression expression = LispInterpreter.tokenize("(defn doublen [n] (* 2 n))");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertEquals("DOUBLEN", actual);
        assertNotNull(executer.context.get(new Atom<>("DOUBLEN", AtomType.SYMBOL)));
        SExpression doubleFn = LispInterpreter.tokenize("(doublen 3.1)");
        Object doubleActual = executer.execute(doubleFn);
        assertEquals(0, BigDecimal.valueOf(6.2).compareTo((BigDecimal) doubleActual));
    }

    @Test
    public void testExecute_defnDoubleMultiplication() {
        SExpression expression = LispInterpreter.tokenize("(defn doubleMultiplication [a b c] (* 2 a b c))");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertEquals("DOUBLEMULTIPLICATION", actual);
        assertNotNull(executer.context.get(new Atom<>("DOUBLEMULTIPLICATION", AtomType.SYMBOL)));
        SExpression doubleFn = LispInterpreter.tokenize("(doubleMultiplication 3 5 10)");
        Object doubleActual = executer.execute(doubleFn);
        assertEquals(BigInteger.valueOf(300), doubleActual);
    }

    @Test
    public void testExecute_notTrue() {
        SExpression expression = LispInterpreter.tokenize("(not true)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertFalse((Boolean) actual);
    }

    @Test
    public void testExecute_notFalse() {
        SExpression expression = LispInterpreter.tokenize("(not false)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertTrue((Boolean) actual);
    }

    @Test
    public void testExecute_andFalse() {
        SExpression expression = LispInterpreter.tokenize("(and false true)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertFalse((Boolean) actual);
    }

    @Test
    public void testExecute_andTrue() {
        SExpression expression = LispInterpreter.tokenize("(and true true)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertTrue((Boolean) actual);
    }

    @Test
    public void testExecute_ifFalse() {
        SExpression expression = LispInterpreter.tokenize("(if false (format \"La condicion es verdadera\") (format \"La condicion es falsa\"))");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertEquals("La condicion es falsa", actual);
    }

    @Test
    public void testExecute_ifTrue() {
        SExpression expression = LispInterpreter.tokenize("(if true (format \"La condicion es verdadera\") (format \"La condicion es falsa\"))");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertEquals("La condicion es verdadera", actual);
    }

    @Test
    public void testExecute_correctFormat() {
        SExpression expression = LispInterpreter.tokenize("(Format \"Correct Format %s\" \"Test 1\")");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertEquals("Correct Format Test 1", actual);
    }

    @Test
    public void testExecute_correctFormat2() {
        SExpression expression = LispInterpreter.tokenize("(format \"%5d\" 3)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertEquals("    3", actual);
    }

    @Test
    public void testExecute_correctFormat3() {
        SExpression expression = LispInterpreter.tokenize("(format \"decimal %d  octal %o  hex %x  upper-case hex %X\" 63 63 63 63)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertEquals("decimal 63  octal 77  hex 3f  upper-case hex 3F", actual);
    }

    @Test
    public void testExecute_correctFormatPositional() {
        SExpression expression = LispInterpreter.tokenize("(format \"%2$d %1$s\" \"Positional arguments\" 23)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertEquals("23 Positional arguments", actual);
    }

    @Test
    public void testExecute_correctFormatWithMoreParams() {
        SExpression expression = LispInterpreter.tokenize("(Format \"Hola %s, tienes %s. Estos son los artículos que has comprado: %s. El total de artículos es: %d.\" \"Carlos\" \"33\" \"Televisor, perronejo\" 2500)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertEquals("Hola Carlos, tienes 33. Estos son los artículos que has comprado: Televisor, perronejo. El total de artículos es: 2500.", actual);
    }
    
    @Test
    public void testExecute_lessThanOrEqualToEmpty() {
        SExpression expression = LispInterpreter.tokenize("(<=)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertFalse((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanOrEqualToOneParam() {
        SExpression expression = LispInterpreter.tokenize("(<= -92654)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertTrue((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanOrEqualToTwoIncrementalParams() {
        SExpression expression = LispInterpreter.tokenize("(<= -92654 0.2)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertTrue((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanOrEqualToTwoNoIncrementalParams() {
        SExpression expression = LispInterpreter.tokenize("(<= 92654 0.2)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertFalse((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanOrEqualToThreeIncrementalParams() {
        SExpression expression = LispInterpreter.tokenize("(<= 0.5 1 1)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertTrue((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanOrEqualToThreeNoIncrementalParams() {
        SExpression expression = LispInterpreter.tokenize("(<= 1 1 -1.2)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertFalse((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanEmpty() {
        SExpression expression = LispInterpreter.tokenize("(<)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertFalse((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanWithOneParam() {
        SExpression expression = LispInterpreter.tokenize("(< -9223372036854775809)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertTrue((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanWithTwoIncrementalParams() {
        SExpression expression = LispInterpreter.tokenize("(< -9223372036854775809 9223372036854775807)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertTrue((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanWithTwoNoIncrementalParams() {
        SExpression expression = LispInterpreter.tokenize("(< 0.5 -1)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertFalse((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanWithThreeNoIncrementalParams() {
        SExpression expression = LispInterpreter.tokenize("(< 0.5 -1 0)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertFalse((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanWithThreeIncrementalParams() {
        SExpression expression = LispInterpreter.tokenize("(< 0.5 1 5)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertTrue((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanWithManyIncrementalParams() {
        SExpression expression = LispInterpreter.tokenize("(< 2 3 4 5 6)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertTrue((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanWithThreeNoIncrementalParams2() {
        SExpression expression = LispInterpreter.tokenize("(< 0.5 1 0)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertFalse((Boolean)actual);
    }
    
    @Test
    public void testExecute_lessThanWithThreeNoIncrementalParams3() {
        SExpression expression = LispInterpreter.tokenize("(< 5 6 0)");
        LispExecuter executer = new LispExecuter();
        Object actual = executer.execute(expression);
        assertFalse((Boolean)actual);
    }

}
