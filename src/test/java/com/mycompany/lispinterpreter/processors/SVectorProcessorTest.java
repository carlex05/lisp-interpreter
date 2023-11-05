/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import com.mycompany.lispinterpreter.sexpressions.SVector;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Carlex
 */
public class SVectorProcessorTest {
    
    public SVectorProcessorTest() {
    }

    @Test
    public void testProcessSExpression_empty() {
        SVector actual = (SVector)new SVectorProcessor().processSExpression("[]");
        assertTrue(actual.isEmpty());
    }
    
    @Test
    public void testProcessSExpression_oneElement() {
        SVector actual = (SVector)new SVectorProcessor().processSExpression("[1]");
        assertFalse(actual.isEmpty());
        assertEquals(BigInteger.valueOf(1), ((Atom)actual.get(0)).value());
    }
    
    @Test
    public void testProcessSExpression_threeElement() {
        SVector actual = (SVector)new SVectorProcessor().processSExpression("[1 n \"Hello\"]");
        assertEquals(3, actual.size());
        assertEquals(BigInteger.valueOf(1), ((Atom)actual.get(0)).value());
        assertEquals("N", ((Atom)actual.get(1)).value());
        assertEquals(AtomType.SYMBOL, ((Atom)actual.get(1)).type());
        assertEquals("Hello", ((Atom)actual.get(2)).value());
        assertEquals(AtomType.STRING, ((Atom)actual.get(2)).type());
    }

    @Test
    public void testIsProcessable_notVector() {
        assertFalse(new SVectorProcessor().isProcessable("{2 3}"));
    }
    
    @Test
    public void testIsProcessable_vector() {
        assertTrue(new SVectorProcessor().isProcessable("[2 3]"));
    }
    
    @Test
    public void testIsProcessable_emptyVector() {
        assertTrue(new SVectorProcessor().isProcessable("[]"));
    }
    
}
