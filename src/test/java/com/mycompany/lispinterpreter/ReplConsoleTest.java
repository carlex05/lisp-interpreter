package com.mycompany.lispinterpreter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Carlex
 */
public class ReplConsoleTest {

    @Test
    public void testReplPrompt() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        System.setOut(out);
        System.setIn(new ByteArrayInputStream("(+ 1 2)".getBytes()));
        Optional<String> actual = ReplConsole.readFromStandardPromptInput();
        assertEquals("cclisp> ", baos.toString());
        assertEquals("(+ 1 2)", actual.get());
    }
    
    @Test
    public void testReplPrompt_2() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        System.setOut(out);
        System.setIn(new ByteArrayInputStream("(+ 1)".getBytes()));
        Optional<String> actual = ReplConsole.readFromStandardPromptInput();
        assertEquals("cclisp> ", baos.toString());
        assertEquals("(+ 1)", actual.get());
    }
    
    @Test
    public void testExecuteRepl(){
        Optional<String> actual = ReplConsole.executeRepl("(+ 1)");
        assertEquals("1", actual.get());
    }
    
    @Test
    public void testExecuteRepl_2(){
        Optional<String> actual = ReplConsole.executeRepl("(+ 1 2.0)");
        assertEquals("3.0", actual.get());
    }
    
    @Test
    public void testExecuteRepl_error(){
        Optional<String> actual = ReplConsole.executeRepl("+ 1 2.0");
        assertFalse(actual.isPresent());
    }
    
    public void testExecuteScript(){
        String script = """
                        (defn hello []
                          (println "Hello Coding Challenge World"))
                        
                        (defn doublen [n]
                          (* n 2))
                        
                        (defn fib [n]
                          (if (< n 2)
                            n
                            (+ (fib (- n 1))
                               (fib (- n 2)))))
                        
                        (defn fact [n]
                          (if (<= n 1)
                            1
                            (* n (fact (- n 1)))))
                        
                        (hello)
                        
                        (println (format "The double of 5 is %d" (doublen 5)))
                        
                        (println (format "Factorial of 5 is %d" (fact 5)))
                        
                        (println (format "The 7th number of the Fibonacci sequence is %d" (fib 7)))
                        """;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        System.setOut(out);
        ReplConsole.executeScript(script);
        assertEquals("Hello Coding Challenge World" + System.lineSeparator() + 
                "The double of 5 is 10" + System.lineSeparator() + 
                "Factorial of 5 is 120" + System.lineSeparator() + 
                "The 7th number of the Fibonacci sequence is 13" + System.lineSeparator(), baos.toString());
    }
    
}
