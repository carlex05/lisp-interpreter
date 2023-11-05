/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.SSet;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Carlex
 */
public class SSetProcessorTest {

    /**
     * Test of processSExpression method, of class SSetProcessor.
     */
    @Test
    public void testProcessSExpression_empty() {
        SSet actual = (SSet)new SSetProcessor().processSExpression("#{}");
        assertTrue(actual.isEmpty());
    }
    
    @Test
    public void testProcessSExpression_oneElement() {
        SSet actual = (SSet)new SSetProcessor().processSExpression("#{1}");
        assertFalse(actual.isEmpty());
        assertTrue(actual.contains(new Atom(BigInteger.ONE, AtomType.BIG_INTEGER)));
    }
    
    @Test
    public void testProcessSExpression_threeElement() {
        SSet actual = (SSet)new SSetProcessor().processSExpression("#{1 n \"Hello\"}");
        assertEquals(3, actual.size());
        assertTrue(actual.contains(new Atom(BigInteger.ONE, AtomType.BIG_INTEGER)));
        assertTrue(actual.contains(new Atom("N", AtomType.SYMBOL)));
        assertTrue(actual.contains(new Atom("Hello", AtomType.STRING)));
    }

    /**
     * Test of isProcessable method, of class SSetProcessor.
     */
    @Test
    public void testIsProcessable_success() {
        SSetProcessor processor = new SSetProcessor();
        assertTrue(processor.isProcessable("#{2 3 4}"));
    }
    
    @Test
    public void testIsProcessable_notSuccess() {
        SSetProcessor processor = new SSetProcessor();
        assertFalse(processor.isProcessable("(2 3 4)"));
    }
    
}
