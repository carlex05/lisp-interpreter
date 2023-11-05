package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import com.mycompany.lispinterpreter.sexpressions.SMap;
import java.math.BigInteger;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Carlex
 */
public class SMapProcessorTest {
    
    public SMapProcessorTest() {
    }

    @Test
    public void testProcessSExpression_empty() {
        SMap actual = (SMap)new SMapProcessor().processSExpression("{}");
        assertTrue(actual.isEmpty());
    }
    
    @Test
    public void testProcessSExpression_oneKeyValue() {
        SMap actual = (SMap)new SMapProcessor().processSExpression("{:key \"My value\"}");
        assertFalse(actual.isEmpty());
        assertEquals(new Atom("My value", AtomType.STRING), actual.get(new Atom(":KEY", AtomType.SYMBOL)));
    }
    
    @Test
    public void testProcessSExpression_threeKeyValues() {
        SMap actual = (SMap)new SMapProcessor().processSExpression("{:key \"My value\" {2 3} 1 \"Hi\" David}");
        assertFalse(actual.isEmpty());
        assertEquals(new Atom("My value", AtomType.STRING), actual.get(new Atom(":KEY", AtomType.SYMBOL)));
        assertEquals(new Atom(BigInteger.ONE, AtomType.BIG_INTEGER), actual.get(new SMap(Map.of(new Atom(BigInteger.valueOf(2), AtomType.BIG_INTEGER), new Atom(BigInteger.valueOf(3), AtomType.BIG_INTEGER)))));
        assertEquals(new Atom("DAVID", AtomType.SYMBOL), actual.get(new Atom("Hi", AtomType.STRING)));
    }

    @Test
    public void testIsProcessable_notSuccess() {
        assertFalse(new SMapProcessor().isProcessable("()"));
        assertFalse(new SMapProcessor().isProcessable("#{}"));
        assertFalse(new SMapProcessor().isProcessable("[]"));
    }
    
    @Test
    public void testIsProcessable_successEmpty() {
        assertTrue(new SMapProcessor().isProcessable("{}"));
    }
    
}
