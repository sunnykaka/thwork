package com.akkafun.thwork.roman;

import com.akkafun.thwork.utils.Pair;

/**
 * Created by liubin on 2017/3/4.
 */
public class RomanSymbol {

    private String symbol;

    private int value;

    private int maxRepeatTime;

    public RomanSymbol(String symbol, int value, int maxRepeatTime) {
        this.symbol = symbol;
        this.value = value;
        this.maxRepeatTime = maxRepeatTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }

    public int getMaxRepeatTime() {
        return maxRepeatTime;
    }

    /**
     * calculate roman values, maybe need combine next symbol
     * @param next next symbol
     * @return pair, left: is this symbol subtracted by next symbol, right: calculate result
     */
    public Pair<Boolean, Integer> calculateValue(RomanSymbol next) {
        boolean subtracted = isSubtractedByNext(next.getSymbol());
        int value;
        if (subtracted) {
            value = next.getValue() - getValue();
        } else {
            value = getValue();
        }
        return new Pair<>(subtracted, value);
    }

    /**
     * is this symbol subtracted by next symbol
     * @param nextSymbol next symbol
     * @return
     */
    private boolean isSubtractedByNext(String nextSymbol) {
        switch (symbol) {
            case "I":
                return nextSymbol.equalsIgnoreCase("V") || nextSymbol.equalsIgnoreCase("X");
            case "X":
                return nextSymbol.equalsIgnoreCase("L") || nextSymbol.equalsIgnoreCase("C");
            case "C":
                return nextSymbol.equalsIgnoreCase("D") || nextSymbol.equalsIgnoreCase("M");
            default:
                return false;
        }
    }

}
