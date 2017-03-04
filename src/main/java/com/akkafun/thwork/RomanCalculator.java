package com.akkafun.thwork;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liubin on 2017/3/4.
 */
public class RomanCalculator {

    public static final Map<String, Integer> ROMAN_SYMBOL_MAP = new HashMap<>();

    static {

        ROMAN_SYMBOL_MAP.put("I", 1);

        ROMAN_SYMBOL_MAP.put("V", 5);

        ROMAN_SYMBOL_MAP.put("X", 10);

        ROMAN_SYMBOL_MAP.put("L", 50);

        ROMAN_SYMBOL_MAP.put("C", 100);

        ROMAN_SYMBOL_MAP.put("D", 500);

        ROMAN_SYMBOL_MAP.put("M", 1000);

    }



}
