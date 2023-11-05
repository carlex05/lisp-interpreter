package com.mycompany.lispinterpreter;

import com.mycompany.lispinterpreter.sexpressions.Atom;
import com.mycompany.lispinterpreter.sexpressions.AtomType;
import com.mycompany.lispinterpreter.sexpressions.Expression;
import com.mycompany.lispinterpreter.sexpressions.SExpression;
import com.mycompany.lispinterpreter.sexpressions.SVector;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author Carlex
 */
public class LispExecuter {

    final Map<Atom, BiFunction<Map, List<SExpression>, Object>> context = new HashMap<>();

    public Object execute(SExpression expression) {
        context.put(new Atom("+", AtomType.SYMBOL), createAdditionFunction());
        context.put(new Atom("*", AtomType.SYMBOL), createMultiplicationFunction());
        context.put(new Atom("DEFN", AtomType.SYMBOL), createDefnFunction());
        context.put(new Atom("NOT", AtomType.SYMBOL), createNotFunction());
        context.put(new Atom("AND", AtomType.SYMBOL), createAndFunction());
        context.put(new Atom("TRUE", AtomType.SYMBOL), (contextBoolean, params) -> true);
        context.put(new Atom("FALSE", AtomType.SYMBOL), (contextBoolean, params) -> false);
        context.put(new Atom("-", AtomType.SYMBOL), createSubstractionFunction());
        context.put(new Atom("/", AtomType.SYMBOL), createDivisionFunction());
        context.put(new Atom("IF", AtomType.SYMBOL), createIfFunction());
        context.put(new Atom("FORMAT", AtomType.SYMBOL), createFormatFunction());
        context.put(new Atom("<", AtomType.SYMBOL), createLessThanFunction());
        context.put(new Atom("<=", AtomType.SYMBOL), createLessThanOrEqualToFunction());
        return expression.evaluate(context);
    }

    BiFunction<Map, List<SExpression>, Object> createAdditionFunction() {
        return (Map context, List<SExpression> params) -> {
            List<Number> numberParams = params.stream().map(param -> (Number) param.evaluate(context)).toList();
            boolean isDecimalOperation = numberParams.stream().anyMatch(param -> param instanceof BigDecimal);
            if (isDecimalOperation) {
                BigDecimal total = numberParams.stream().map(this::toBigDecimal).reduce(BigDecimal::add).get();
                return total;
            } else {
                BigInteger total = numberParams.stream().map(number -> (BigInteger) number).reduce(BigInteger::add).get();
                return total;
            }
        };
    }

    BiFunction<Map, List<SExpression>, Object> createSubstractionFunction() {
        return (Map context, List<SExpression> params) -> {
            List<Number> integerParams = params.stream().map(param -> (Number) param.evaluate(context)).toList();
            boolean isDecimalOperation = integerParams.stream().anyMatch(param -> param instanceof BigDecimal);
            if (isDecimalOperation) {
                BigDecimal minuend = toBigDecimal(integerParams.get(0));
                if (integerParams.size() == 1) {
                    return minuend.negate();
                }
                BigDecimal subtrahend = integerParams.subList(1, integerParams.size())
                        .stream().map(this::toBigDecimal).reduce(BigDecimal::add).get();
                return minuend.subtract(subtrahend);
            } else {
                BigInteger minuend = (BigInteger) integerParams.get(0);
                if (integerParams.size() == 1) {
                    return minuend.negate();
                }
                BigInteger subtrahend = integerParams.subList(1, integerParams.size())
                        .stream().map(number -> (BigInteger) number).reduce(BigInteger::add).get();
                return minuend.subtract(subtrahend);
            }

        };
    }

    BiFunction<Map, List<SExpression>, Object> createDivisionFunction() {
        return (Map context, List<SExpression> params) -> {
            List<BigDecimal> integerParams = params.stream().map(param -> (Number) param.evaluate(context)).map(this::toBigDecimal).toList();
            BigDecimal numerator = integerParams.get(0);
            if (integerParams.size() == 1) {
                return BigDecimal.ONE.divide(numerator);
            }
            BigDecimal denominator = integerParams.subList(1, integerParams.size())
                    .stream().reduce(BigDecimal::multiply).get();
            return numerator.divide(denominator);
        };
    }

    BiFunction<Map, List<SExpression>, Object> createMultiplicationFunction() {
        return (Map context, List<SExpression> params) -> {
            List<Number> numbersParams = params.stream().map(param -> (Number) param.evaluate(context)).toList();
            boolean isDecimalOperation = numbersParams.stream().anyMatch(param -> param instanceof BigDecimal);
            if (isDecimalOperation) {
                return numbersParams.stream().map(this::toBigDecimal).reduce(BigDecimal::multiply).get();
            } else {
                return numbersParams.stream().map(number -> (BigInteger) number)
                        .reduce(BigInteger::multiply).orElse(BigInteger.ONE);
            }

        };
    }

    BigDecimal toBigDecimal(Number number) {
        if (number instanceof BigDecimal) {
            return (BigDecimal) number;
        }
        return BigDecimal.valueOf(number.longValue());
    }

    BiFunction<Map, List<SExpression>, Object> createDefnFunction() {
        return (Map context, List<SExpression> params) -> {
            Atom symbolToCreate = (Atom) params.get(0);
            SVector parametersOfFunctionToCreate = (SVector) params.get(1);
            Expression bodyOfFunctionToCreate = (Expression) params.get(2);
            BiFunction<Map, List<SExpression>, Object> functionToCreate = (contextOfFunctionToCreate, paramsPassed) -> {
                Map<Atom, BiFunction<Map, List<SExpression>, Object>> newLocalContext = new HashMap<>(contextOfFunctionToCreate);
                for (int i = 0; i < parametersOfFunctionToCreate.size(); i++) {
                    final int iterator = i;
                    newLocalContext.put((Atom) parametersOfFunctionToCreate.get(i),
                            (contextOfSymbol, localParams) -> paramsPassed.get(iterator).evaluate(contextOfFunctionToCreate));
                }
                return bodyOfFunctionToCreate.evaluate(newLocalContext);
            };
            context.put(symbolToCreate, functionToCreate);

            return symbolToCreate.value();
        };
    }
    
    BiFunction<Map, List<SExpression>, Object> createLessThanOrEqualToFunction() {
        return (Map context, List<SExpression> params) -> {
            if(params.size() >= 2){
                List<Number> numberParams = params.stream().map(param -> (Number) param.evaluate(context)).toList();
                boolean isDecimalOperation = numberParams.stream().anyMatch(param -> param instanceof BigDecimal);
                if(isDecimalOperation){
                    for(int i = 0; i<numberParams.size()-1; i++){
                        BigDecimal left = toBigDecimal(numberParams.get(i));
                        BigDecimal right = toBigDecimal(numberParams.get(i+1));
                        if(left.compareTo(right) > 0) return false;
                    }
                    return true;
                }else{
                    for(int i = 0; i<numberParams.size()-1; i++){
                        BigInteger left = (BigInteger)numberParams.get(i);
                        BigInteger right = (BigInteger)numberParams.get(i+1);
                        if(left.compareTo(right) > 0) return false;
                    }
                    return true;
                }
            }
            return !params.isEmpty();
        };
    }
    
    BiFunction<Map, List<SExpression>, Object> createLessThanFunction() {
        return (Map context, List<SExpression> params) -> {
            if(params.size() >= 2){
                List<Number> numberParams = params.stream().map(param -> (Number) param.evaluate(context)).toList();
                boolean isDecimalOperation = numberParams.stream().anyMatch(param -> param instanceof BigDecimal);
                if(isDecimalOperation){
                    for(int i = 0; i<numberParams.size()-1; i++){
                        BigDecimal left = toBigDecimal(numberParams.get(i));
                        BigDecimal right = toBigDecimal(numberParams.get(i+1));
                        if(left.compareTo(right) >= 0) return false;
                    }
                    return true;
                }else{
                    for(int i = 0; i<numberParams.size()-1; i++){
                        BigInteger left = (BigInteger)numberParams.get(i);
                        BigInteger right = (BigInteger)numberParams.get(i+1);
                        if(left.compareTo(right) >= 0) return false;
                    }
                    return true;
                }
            }
            return !params.isEmpty();
        };
    }

    BiFunction<Map, List<SExpression>, Object> createNotFunction() {
        return (Map context, List<SExpression> params) -> {
            Boolean booleanParam = (Boolean) params.get(0).evaluate(context);
            return !booleanParam;
        };
    }

    BiFunction<Map, List<SExpression>, Object> createAndFunction() {
        return (Map context, List<SExpression> params) -> {
            Boolean booleanParam = (Boolean) params.get(0).evaluate(context);
            Boolean booleanParam2 = (Boolean) params.get(1).evaluate(context);
            return booleanParam && booleanParam2;
        };
    }

    BiFunction<Map, List<SExpression>, Object> createIfFunction() {
        return (Map context, List<SExpression> params) -> {
            Boolean booleanParam = (Boolean) params.get(0).evaluate(context);
            if (booleanParam) {
                return params.get(1).evaluate(context);
            }
            return params.get(2).evaluate(context);
        };
    }

    BiFunction<Map, List<SExpression>, Object> createFormatFunction() {
        return (Map context, List<SExpression> params) -> {
            String format = params.get(0).evaluate(context).toString();
            List<Object> values = params.stream().map(param -> {
                Object result = param.evaluate(context);
                if (result instanceof BigDecimal) {
                    return new BigInteger(((BigDecimal) result).toString());
                }
                return result;
            }).toList().subList(1, params.size());
            return String.format(format, values.toArray());
        };
    }

}
