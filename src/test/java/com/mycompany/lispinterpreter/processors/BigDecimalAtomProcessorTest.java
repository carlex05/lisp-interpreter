package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.LispInterpreter;
import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Carlex
 */
public class BigDecimalAtomProcessorTest {
    
    public BigDecimalAtomProcessorTest() {
    }

    @Test
    public void testProcessSExpression() {
    }

    @Test
    public void testIsProcessable_with2NegativeTag() {
        Boolean actual = new BigDecimalAtomProcessor().isProcessable("-1-2");
        assertFalse(actual);
    }
    
    @Test
    public void testIsProcessable_with1NegativeTagInIncorrectPlace() {
        Boolean actual = new BigDecimalAtomProcessor().isProcessable("8-2");
        assertFalse(actual);
    }
    
    @Test
    public void testIsProcessable_with2DecimalPoint() {
        Boolean actual = new BigDecimalAtomProcessor().isProcessable(".8.2");
        assertFalse(actual); 
    }
    
    @Test
    public void testIsProcessable_with1NegativeTagIncorrectType() {
        Boolean actual = new BigDecimalAtomProcessor().isProcessable("9223372036854775799");
        assertFalse(actual);
    }
    
    @Test
    public void testIsProcessable_with1DecimalPointAtStart() {
        Boolean actual = new BigDecimalAtomProcessor().isProcessable(".82");
        assertTrue(actual); 
    }
    
    @Test
    public void testIsProcessable_with1DecimalPointAtMiddle() {
        Boolean actual = new BigDecimalAtomProcessor().isProcessable("82.6");
        assertTrue(actual); 
    }
    
    @Test
    public void testIsProcessable_with1DecimalPointAtEnd() {
        Boolean actual = new BigDecimalAtomProcessor().isProcessable("82.");
        assertTrue(actual); 
    }
    
    
    
    @Test
    public void testTokenize_paramDoublePos() {
        SExpression actual = LispInterpreter.tokenize("1.2");
        assertTrue(actual instanceof Atom);
        assertEquals(AtomType.BIG_DECIMAL, ((Atom)actual).type());
        assertEquals(BigDecimal.valueOf(1.2), ((Atom)actual).value());
    }
    

    
    @Test
    public void testTokenize_paramDoubleNeg() {
        SExpression actual = LispInterpreter.tokenize("-2.5");
        assertTrue(actual instanceof Atom);
        assertEquals(AtomType.BIG_DECIMAL, ((Atom)actual).type());
        assertEquals(BigDecimal.valueOf(-2.5), ((Atom)actual).value());
    }
}
