package com.akkafun.thwork.roman;

import com.akkafun.thwork.utils.Pair;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * calculate roman symbols
 * Created by liubin on 2017/3/4.
 */
public class RomanCalculator {

    private static final Map<String, RomanSymbol> ROMAN_SYMBOL_MAP = new HashMap<>();

    static {

        ROMAN_SYMBOL_MAP.put("I", new RomanSymbol("I", 1, 2));

        ROMAN_SYMBOL_MAP.put("V", new RomanSymbol("V", 5, 0));

        ROMAN_SYMBOL_MAP.put("X", new RomanSymbol("X", 10, 2));

        ROMAN_SYMBOL_MAP.put("L", new RomanSymbol("L", 50, 0));

        ROMAN_SYMBOL_MAP.put("C", new RomanSymbol("C", 100, 2));

        ROMAN_SYMBOL_MAP.put("D", new RomanSymbol("D", 500, 0));

        ROMAN_SYMBOL_MAP.put("M", new RomanSymbol("M", 1000, 2));

    }

    /**
     * calculate result of symbols
     * @param romanSymbols roman symbols
     * @return calculate result
     * @throws RomanCalculateException
     */
    public static long calculateRomanSymbol(String romanSymbols) throws RomanCalculateException {

        if(romanSymbols == null) {
            throw new RomanCalculateException("Symbols cannot be null");
        }

        List<String> symbols = new ArrayList<>(romanSymbols.length());
        for(int i = 0; i < romanSymbols.length(); i++) {
            symbols.add(String.valueOf(romanSymbols.charAt(i)));
        }

        validate(symbols);

        boolean skip = false;
        long result = 0;
        int lastValue = Integer.MAX_VALUE;
        String lastSymbols = null;

        for(int i = 0; i < symbols.size(); i ++) {

            if(skip) {
                //this symbol has been combined by previous symbol, need ignore
                skip = false;
                continue;
            }

            String symbol = symbols.get(i);
            RomanSymbol roman = ROMAN_SYMBOL_MAP.get(symbol);
            int currentValue;
            String currentSymbols;

            if(i == symbols.size() - 1) {

                //it's the last symbol in list
                currentValue = roman.getValue();
                currentSymbols = roman.getSymbol();

            } else {

                RomanSymbol nextRoman = ROMAN_SYMBOL_MAP.get(symbols.get(i + 1));
                //calculate roman values, maybe need combine next symbol
                Pair<Boolean, Integer> calculateValue = roman.calculateValue(nextRoman);
                if(calculateValue.getLeft()) {
                    //need to skip next symbol
                    skip = true;
                    currentSymbols = roman.getSymbol() + nextRoman.getSymbol();
                } else {
                    currentSymbols = roman.getSymbol();
                }
                currentValue = calculateValue.getRight();

            }

            //check if symbols is start with the largest values
            if(currentValue > lastValue) {
                throw new RomanCalculateException(String.format("Symbols are not start with the largest values. " +
                        "[%s, %d] is after [%s, %d]", currentSymbols, currentValue, lastSymbols, lastValue));
            }
            result += currentValue;
            lastValue = currentValue;
            lastSymbols = currentSymbols;
        };

        return result;
    }

    /**
     * calculate result of symbols
     * @param symbols roman symbols
     * @return calculate result
     * @throws RomanCalculateException
     */
//    public static long calculateRomanSymbol(String symbols) throws RomanCalculateException {
//        return calculateRomanSymbol(
//                symbols == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(symbols.split(""))));
//    }

    /**
     * 1. check all symbols are known.
     * 2. check symbols are repeat correctly.
     * @param symbols roman symbols
     */
    private static void validate(List<String> symbols) {

        String lastSymbol = null;
        int repeatTime = 0;

        for (String symbol : symbols) {

            final RomanSymbol roman = ROMAN_SYMBOL_MAP.get(symbol);

            //check all symbols are known.
            if (roman == null) {
                throw new RomanCalculateException(String.format("Unknown symbol %s in list", symbol));
            }

            //The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more.
            //(They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.)
            // "D", "L", and "V" can never be repeated.
            if (lastSymbol != null && symbol.equalsIgnoreCase(lastSymbol)) {
                repeatTime++;
            } else {
                repeatTime = 0;
            }
            if (repeatTime > 0 && roman.getMaxRepeatTime() < repeatTime) {
                throw new RomanCalculateException(String.format(
                        "Symbol %s has repeat %d times, which max repeat time is %d",
                        symbol, repeatTime, roman.getMaxRepeatTime()));
            }

            lastSymbol = symbol;
        }

    }


    public static boolean isSymbolExist(String symbol) {

        if(StringUtils.isBlank(symbol)) return false;

        return RomanCalculator.ROMAN_SYMBOL_MAP.keySet().contains(symbol.toUpperCase());

    }

    public static RomanSymbol getRomanBySymbol(String symbol) {

        if(StringUtils.isBlank(symbol)) return null;

        return RomanCalculator.ROMAN_SYMBOL_MAP.get(symbol.toUpperCase());
    }


}
