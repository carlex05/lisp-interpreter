package com.mycompany.lispinterpreter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author Carlex
 */
public class ReplConsole {
    
    static LispExecuter executer = new LispExecuter();
    
    public static void main(String... args) throws IOException {
        if(args != null && args.length >= 2){
            if(args[0].equals("-f")){
                String filename = args[1];
                String script = Files.readString(Path.of(filename));
                executeScript(script);
                return;
            }
        }
        while(true){
            Optional<String> command = readFromStandardPromptInput();
            if(command.isPresent() && !command.get().isEmpty()){
                if(command.get().equals("\\q"))
                    return;
                getOut().println(executeRepl(command.get()).orElse("Error"));
            }
        }
    }
    
    static void executeScript(String script){
        String composedScript = "("+ script + ")";
        List<String> commands = TokenUtils.splitTokens(script);
        commands.forEach(ReplConsole::executeRepl);
    }
    
    static Optional<String> executeRepl(String command){
        try {
            return Optional.of(executer.execute(LispInterpreter.tokenize(command)).toString());
        } catch (Exception e) {
            return Optional.empty();
        }
        
    }

    static Optional<String> readFromStandardPromptInput() {
        getOut().print("cclisp> ");
        return Optional.of(new Scanner(getIn()).nextLine());
    }
    
    static InputStream getIn(){
        return System.in;
    }
    
    static PrintStream getOut(){
        return System.out;
    }

}
