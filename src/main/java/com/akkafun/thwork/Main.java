package com.akkafun.thwork;

import com.akkafun.thwork.exchange.GalaxyExchange;

/**
 * Hello world!
 *
 */
public class Main {

    public static void main(String[] args ) {

        GalaxyExchange.getInstance().tradeByFile("data.txt");
    }

}
