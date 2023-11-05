package com.mycompany.lispinterpreter.processors;

import com.mycompany.lispinterpreter.LispInterpreter;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import com.mycompany.lispinterpreter.sexpressions.SMap;
import com.mycompany.lispinterpreter.TokenUtils;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlex
 */
public class SMapProcessor implements SExpressionProcessor {

    @Override
    public SExpression processSExpression(String expression) {
        List<String> tokens = TokenUtils.splitTokens(expression.substring(1, expression.length()-1));
        List<SExpression> sexpressions = tokens.stream().map(LispInterpreter::tokenize).toList();
        SMap map = new SMap(Map.of());
        for(int i = 0; i<sexpressions.size();i=i+2){
            map.put(sexpressions.get(i), sexpressions.get(i+1));
        }
        return map;
    }

    @Override
    public Boolean isProcessable(String expression) {
        return expression.startsWith("{");
    }
    
}
