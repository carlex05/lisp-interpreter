package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.LispInterpreter;
import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Carlex
 */
public class BigIntegerAtomProcessorTest {
    
    public BigIntegerAtomProcessorTest() {
    }

    @Test
    public void testProcessSExpression() {
        Atom actual = (Atom)new BigIntegerAtomProcessor().processSExpression("8");
        assertEquals(AtomType.BIG_INTEGER, actual.type());
        assertEquals(BigInteger.valueOf(8), actual.value());
    }
    
    @Test
    public void testProcessSExpression_Max() {
        Atom actual = (Atom)new BigIntegerAtomProcessor().processSExpression("9223372036854775807");
        assertEquals(AtomType.BIG_INTEGER, actual.type());
        assertEquals(BigInteger.valueOf(9223372036854775807L), actual.value());
    }

    @Test
    public void testIsProcessable() {
    }
    
    @Test
    public void testTokenize_paramIntNeg() {
        SExpression actual = LispInterpreter.tokenize("-2");
        assertTrue(actual instanceof Atom);
        assertEquals(AtomType.BIG_INTEGER, ((Atom)actual).type());
        assertEquals(BigInteger.valueOf(-2), ((Atom)actual).value());
    }
    
    @Test
    public void testTokenize_paramIntPos() {
        SExpression actual = LispInterpreter.tokenize("1");
        assertTrue(actual instanceof Atom);
        assertEquals(BigInteger.valueOf(1), ((Atom)actual).value());
        assertEquals(AtomType.BIG_INTEGER, ((Atom)actual).type());
    }
    
        @Test
    public void testIsProcessable_with2NegativeTag() {
        Boolean actual = new BigIntegerAtomProcessor().isProcessable("-1-2");
        assertFalse(actual);
    }
    
    @Test
    public void testIsProcessable_withNegativeTag() {
        Boolean actual = new BigIntegerAtomProcessor().isProcessable("-");
        assertFalse(actual);
    }
    
    @Test
    public void testIsProcessable_with1NegativeTagInIncorrectPlace() {
        Boolean actual = new BigIntegerAtomProcessor().isProcessable("8-2");
        assertFalse(actual);
    }
    
    @Test
    public void testIsProcessable_with2DecimalPoint() {
        Boolean actual = new BigIntegerAtomProcessor().isProcessable(".8.2");
        assertFalse(actual); 
    }
    
    @Test
    public void testIsProcessable_with1DecimalPoint() {
        Boolean actual = new BigIntegerAtomProcessor().isProcessable("8.2");
        assertFalse(actual); 
    }
    
    @Test
    public void testIsProcessable_withIntegers() {
        Boolean actual = new BigIntegerAtomProcessor().isProcessable("9223372036854775799");
        assertTrue(actual); 
    }
    
    
    
}
