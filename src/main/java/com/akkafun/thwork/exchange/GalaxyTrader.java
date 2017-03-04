package com.akkafun.thwork.exchange;

import com.akkafun.thwork.roman.RomanCalculator;
import com.akkafun.thwork.roman.RomanSymbol;
import com.akkafun.thwork.utils.Pair;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liubin on 2017/3/5.
 */
public class GalaxyTrader {

    private static final Logger logger = LoggerFactory.getLogger(GalaxyTrader.class);

    private Map<String, RomanSymbol> etWordToRomanMap = new HashMap<>();

    private Map<String, Double> mineralPriceMap = new HashMap<>();

    /**
     * answer questions
     * @param lines
     * @return
     */
    public List<String> answer(List<String> lines) {

        if(lines == null || lines.isEmpty()) return new ArrayList<>();

        List<String> answers = new ArrayList<>();

        lines.stream().map(this::prepareLine).forEach(line -> processLine(answers, line));

        return answers;
    }

    /**
     * remove ? and ？in sentences
     * @param line
     * @return
     */
    private String prepareLine(String line) {

        String result = line;

        if(line.contains("?")){
            result = result.replace("?", "");
        } else if(line.contains("？")) {
            result = result.replace("?", "");
        }
        return result.trim();
    }

    /**
     * process one line
     * @param answers final answer
     * @param line input line
     */
    private void processLine(List<String> answers, String line) {

        String[] tokens = line.split(" ");
        int length = tokens.length;

        try {

            if(length == 3 && RomanCalculator.isSymbolExist(tokens[2])) {
                //e.g. glob is I
                processRomanSentence(tokens);

            } else if(tokens.length > 4 && tokens[length - 1].equalsIgnoreCase("Credits")
                    && StringUtils.isNumeric(tokens[length - 2])) {

                //e.g. glob glob Silver is 34 Credits
                processMineralPriceSentence(line, tokens);

            } else if(tokens[0].equalsIgnoreCase("how") && tokens[1].equalsIgnoreCase("much") && length > 3) {

                //e.g. how much is pish tegj glob glob
                String answer = processHowMuchSentence(line, tokens);
                answers.add(answer);

            } else if(tokens[0].equalsIgnoreCase("how") && tokens[1].equalsIgnoreCase("many") && length > 5){

                //e.g. how many Credits is glob prok Silver
                String answer = processHowManySentence(line, tokens);
                answers.add(answer);

            } else {

                //unknown sentence
                answers.add("I have no idea what you are talking about");

            }

        } catch (BadSentenceException e) {
            logger.warn(e.getMessage());
            answers.add("I have no idea what you are talking about");
        }


    }

    /**
     * process sentence like 'how many Credits is glob prok Silver'
     * @param line
     * @param tokens
     * @return
     */
    private String processHowManySentence(String line, String[] tokens) {
        String mineral = tokens[tokens.length - 1];
        if(!mineralPriceMap.containsKey(mineral.toUpperCase())) {
            throw new BadSentenceException(
                    String.format("Unknown mineral %s in sentence %s", mineral, line));
        }
        double mineralPrice = mineralPriceMap.get(mineral.toUpperCase());

        Pair<String, String> pair = retrieveETWordsAndSymbolsFromTokens(line, tokens, 4, tokens.length - 1);
        String etWords = pair.getLeft();
        String symbols = pair.getRight();

        //calculate the number of minerals
        long values = RomanCalculator.calculateRomanSymbol(symbols);

        //calculate total credits
        long credits = Math.round(mineralPrice * values);

        return String.format("%s %s is %d Credits", etWords, mineral, credits);
    }

    /**
     * process sentence like 'how much is pish tegj glob glob'
     * @param line
     * @param tokens
     */
    private String processHowMuchSentence(String line, String[] tokens) {

        Pair<String, String> pair = retrieveETWordsAndSymbolsFromTokens(line, tokens, 3, tokens.length);
        String etWords = pair.getLeft();
        String symbols = pair.getRight();
        //calculate the number
        long values = RomanCalculator.calculateRomanSymbol(symbols);

        return String.format("%s is %d", etWords, values);
    }

    /**
     * process sentence like 'glob glob Silver is 34 Credits'
     * @param line
     * @param tokens
     */
    private void processMineralPriceSentence(String line, String[] tokens) {

        int length = tokens.length;
        int credits = Integer.parseInt(tokens[length - 2]);

        String mineral = tokens[length - 4];
        String symbols = retrieveETWordsAndSymbolsFromTokens(line, tokens, 0, length - 4).getRight();
        //calculate number of mineral
        long mineralCount = RomanCalculator.calculateRomanSymbol(symbols);
        if (mineralCount > 0L) {
            ////calculate mineral unit price
            double mineralPrice = new BigDecimal(credits).divide(
                    new BigDecimal(mineralCount), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            mineralPriceMap.put(mineral.toUpperCase(), mineralPrice);
        }
    }

    /**
     * process sentence like 'glob is I'
     * @param tokens
     */
    private void processRomanSentence(String[] tokens) {
        String etWord = tokens[0].toLowerCase();
        String symbol = tokens[2].toUpperCase();

        etWordToRomanMap.put(etWord, RomanCalculator.getRomanBySymbol(symbol));
    }

    /**
     * read ET words and translate to roman symbols
     * @param line sentence
     * @param tokens token array
     * @param start start index in array, include
     * @param end end index in array, exclude
     * @return left: ET words in the sentence, right: roman symbols which was translated by ET words
     */
    private Pair<String, String> retrieveETWordsAndSymbolsFromTokens(String line, String[] tokens, int start, int end) {
        String symbols = "";
        String etWords = "";
        for(int i = start; i < end; i++) {
            String etWord = tokens[i];
            RomanSymbol roman = etWordToRomanMap.get(etWord);
            if(roman == null) {
                throw new BadSentenceException(String.format("Unknown ET word %s in sentence %s", etWord, line));
            }
            symbols += roman.getSymbol();
            etWords += etWord;
            if(i < end - 1) {
                etWords += " ";
            }
        }

        return new Pair<>(etWords, symbols);
    }

}
