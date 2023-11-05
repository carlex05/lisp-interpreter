package com.mycompany.lispinterpreter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Carlex
 */
public final class TokenUtils {

    public static List<String> splitTokens(String sTokens) {
        Pattern pattern = Pattern.compile("-?\\d+\\.?\\d*|[^\\s\"{}\\[\\]()#]+|\"[^\"]*\"|\\{[^\\}]*\\}|\\[[^\\]]*\\]|\\(|\\)|#\\{[^\\}]*\\}");
        Matcher matcher = pattern.matcher(sTokens);
        List<String> basicTokens = new ArrayList<>();
        while (matcher.find()) {
            basicTokens.add(matcher.group());
        }

        List<String> tokens = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        for (String token : basicTokens) {
            if (isOpeningBracket(token)) {
                stack.push(token);
            } else if (")".equals(token) || "]".equals(token) || "}".equals(token)) {
                StringBuilder sb = new StringBuilder();
                while (!isOpeningBracket(stack.peek())) {
                    String currentToken = stack.pop();
                    sb.insert(0, " ").insert(0, currentToken);
                }
                
                sb.insert(0, stack.pop());  // Agregar el bracket de apertura sin espacio
                sb.deleteCharAt(sb.length()-1).append(token);  // Agregar el bracket de cierre
                stack.push(sb.toString().trim()); // Limpia espacios extra al inicio/final
            } else {
                stack.push(token);
            }
        }
        while(!stack.isEmpty())
            tokens.add(0, stack.pop());
        return tokens;
    }


    private static boolean isOpeningBracket(String token) {
        return Arrays.asList("(", "[", "{", "#{").contains(token);
    }

}
