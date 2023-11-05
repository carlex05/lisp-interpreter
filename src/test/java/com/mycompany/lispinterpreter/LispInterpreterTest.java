package com.mycompany.lispinterpreter;

import com.mycompany.lispinterpreter.sexpressions.SExpression;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.Expression;
import com.mycompany.lispinterpreter.sexpressions.SVector;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Carlex
 */
public class LispInterpreterTest {
    
    

    /**
     * Test of tokenize method, of class LispInterpreter.
     */
    @Test
    public void testTokenize_paramIntPos() {
        SExpression actual = LispInterpreter.tokenize("1");
        assertTrue(actual instanceof Atom);
        assertEquals(BigInteger.ONE, ((Atom)actual).value());
        assertEquals(AtomType.BIG_INTEGER, ((Atom)actual).type());
    }
    
    @Test
    public void testTokenize_paramDoublePos() {
        SExpression actual = LispInterpreter.tokenize("1.2");
        assertTrue(actual instanceof Atom);
        assertEquals(AtomType.BIG_DECIMAL, ((Atom)actual).type());
        assertEquals(BigDecimal.valueOf(1.2), ((Atom)actual).value());
    }
    
    @Test
    public void testTokenize_paramIntNeg() {
        SExpression actual = LispInterpreter.tokenize("-2");
        assertTrue(actual instanceof Atom);
        assertEquals(AtomType.BIG_INTEGER, ((Atom)actual).type());
        assertEquals(BigInteger.valueOf(-2), ((Atom)actual).value());
    }
    
    @Test
    public void testTokenize_paramDoubleNeg() {
        SExpression actual = LispInterpreter.tokenize("-2.5");
        assertTrue(actual instanceof Atom);
        assertEquals(AtomType.BIG_DECIMAL, ((Atom)actual).type());
        assertEquals(BigDecimal.valueOf(-2.5), ((Atom)actual).value());
    }
    
    @Test
    public void testTokenize_paramString() {
        SExpression actual = LispInterpreter.tokenize("\"Hello, Coding Challenges\"");
        assertTrue(actual instanceof Atom);
        assertEquals("Hello, Coding Challenges", ((Atom)actual).value());
        assertEquals(AtomType.STRING, ((Atom)actual).type());
    }
    
    @Test
    public void testTokenize_paramSymbolUpperCase() {
        SExpression actual = LispInterpreter.tokenize(":CC");
        assertTrue(actual instanceof Atom);
        assertEquals(":CC", ((Atom)actual).value());
        assertEquals(AtomType.SYMBOL, ((Atom)actual).type());
    }
    
    @Test
    public void testTokenize_paramSymbolLowerCase() {
        SExpression actual = LispInterpreter.tokenize(":cc");
        assertTrue(actual instanceof Atom);
        assertEquals(":CC", ((Atom)actual).value());
        assertEquals(AtomType.SYMBOL, ((Atom)actual).type());
    }

    
    @Test
    public void testTokenize_paramExpression1() {
        SExpression actual = LispInterpreter.tokenize("(format t \"Hello, Coding Challenge World World\")");
        assertTrue(actual instanceof Expression);
        Expression actualExpression = ((Expression)actual);
        assertEquals("FORMAT", actualExpression.symbol().value());
        assertEquals(2, actualExpression.params().size());
        assertEquals("T", ((Atom)actualExpression.params().get(0)).value());
        assertEquals(AtomType.SYMBOL, ((Atom)actualExpression.params().get(0)).type());
        assertEquals("Hello, Coding Challenge World World", ((Atom)actualExpression.params().get(1)).value());
        assertEquals(AtomType.STRING, ((Atom)actualExpression.params().get(1)).type());
    }
    
    @Test
    public void testTokenize_paramExpressionWithSquareBraketAndTree() {
        SExpression actual = LispInterpreter.tokenize("(defn doublen [n] (* n 2))");
        assertTrue(actual instanceof Expression);
        Expression actualExpression = (Expression)actual;
        Atom defnSymbol = actualExpression.symbol();
        assertEquals("DEFN", defnSymbol.value());
        assertEquals(AtomType.SYMBOL, defnSymbol.type());
        assertEquals(3, actualExpression.params().size());
        assertEquals("DOUBLEN", ((Atom)actualExpression.params().get(0)).value());
        assertTrue(actualExpression.params().get(1) instanceof SVector);
        SVector vector = ((SVector)actualExpression.params().get(1));
        assertEquals(1, vector.size());
        assertTrue(actualExpression.params().get(2) instanceof Expression);
        Expression param2 = (Expression)((Expression)actual).params().get(2);
        assertEquals("*", param2.symbol().value());
    }
    
    @Test
    public void testSplitTokens_threeSimpleParams(){
        List<String> actual = TokenUtils.splitTokens("format t 5");
        assertEquals(3, actual.size());
        assertEquals("format", actual.get(0));
        assertEquals("t", actual.get(1));
        assertEquals("5", actual.get(2));
    }
    
    @Test
    public void testSplitTokens_fibonacciFn(){
        List<String> actual = TokenUtils.splitTokens("defn fibonacci [n] (if (<= n 1) n (+ (fibonacci (- n 1)) (fibonacci (- n 2))))");
        assertEquals(4, actual.size());
        assertEquals("defn", actual.get(0));
        assertEquals("fibonacci", actual.get(1));
        assertEquals("[n]", actual.get(2));
        assertEquals("(if (<= n 1) n (+ (fibonacci (- n 1)) (fibonacci (- n 2))))", actual.get(3));
    }
    
    @Test
    public void testSplitTokens_subFibonacciFn(){
        List<String> actual = TokenUtils.splitTokens("if (<= n 1) n (+ (fibonacci (- n 1)) (fibonacci (- n 2)))");
        assertEquals(4, actual.size());
        assertEquals("if", actual.get(0));
        assertEquals("(<= n 1)", actual.get(1));
        assertEquals("n", actual.get(2));
        assertEquals("(+ (fibonacci (- n 1)) (fibonacci (- n 2)))", actual.get(3));
    }
    
     @Test
    public void testSplitTokens_threeIntegersAndDoubleParams(){
        List<String> actual = TokenUtils.splitTokens("5.0 3.1 -15");
        assertEquals(3, actual.size());
        assertEquals("5.0", actual.get(0));
        assertEquals("3.1", actual.get(1));
        assertEquals("-15", actual.get(2));
    }
    
    @Test
    public void testSplitTokens_threeAdvancedParams(){
        List<String> actual = TokenUtils.splitTokens("format t \"Hello, Coding Challenge World World\"");
        assertEquals(3, actual.size());
        assertEquals("format", actual.get(0));
        assertEquals("t", actual.get(1));
        assertEquals("\"Hello, Coding Challenge World World\"", actual.get(2));
    }
    
    @Test
    public void testSplitTokens_threeAdvancedParamsWithSquareBracket(){
        List<String> actual = TokenUtils.splitTokens("defn doublen [n] (* n 2)");
        assertEquals(4, actual.size());
        assertEquals("defn", actual.get(0));
        assertEquals("doublen", actual.get(1));
        assertEquals("[n]", actual.get(2));
        assertEquals("(* n 2)", actual.get(3));
    }
    
    @Test
    public void testSplitTokens_threeAdvancedParamsWithBracket(){
        List<String> actual = TokenUtils.splitTokens("describe-person {:name \"Juan\" :age 30 :address \"123 Calle Principal\"}");
        assertEquals(2, actual.size());
        assertEquals("describe-person", actual.get(0));
        assertEquals("{:name \"Juan\" :age 30 :address \"123 Calle Principal\"}", actual.get(1));
    }
    
    
    @Test
    public void testSplitTokens_threeAdvancedParamsWithHashTagAndBracket(){
        List<String> actual = TokenUtils.splitTokens("contains? #{1 2 3 4 5} 3");
        assertEquals(3, actual.size());
        assertEquals("contains?", actual.get(0));
        assertEquals("#{1 2 3 4 5}", actual.get(1));
        assertEquals("3", actual.get(2));
    }
}
