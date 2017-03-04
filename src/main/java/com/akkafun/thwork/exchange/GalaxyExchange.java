package com.akkafun.thwork.exchange;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by liubin on 2017/3/5.
 */
public class GalaxyExchange {

    private static final Logger logger = LoggerFactory.getLogger(GalaxyExchange.class);

    private static final GalaxyExchange INSTANCE = new GalaxyExchange();

    private GalaxyExchange() {}

    public static GalaxyExchange getInstance() {
        return INSTANCE;
    }

    /**
     * start an intergalactic transactions by a file
     * @param filename
     */
    public void tradeByFile(String filename) {

        if(filename == null) {
            throw new GalaxyExchangeException("filename cannot be null");
        }

        List<String> lines;

        try {
            //read file
            URL url = Resources.getResource(filename);
            lines = Resources.readLines(url, Charsets.UTF_8);

        } catch (IOException e) {
            String errorMessage = "cannot find file: " + filename;
            logger.error(errorMessage, e);
            throw new GalaxyExchangeException(errorMessage);
        }

        //get answers
        List<String> answers = new GalaxyTrader().answer(lines);

        printAnswers(answers);

    }


    /**
     * print answer
     * @param answers
     */
    private void printAnswers(List<String> answers) {
        System.out.println("==========Test Output=========");
        answers.forEach(System.out::println);
    }

}
