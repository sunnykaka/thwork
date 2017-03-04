package com.akkafun.thwork;


import com.akkafun.thwork.roman.RomanCalculateException;
import org.junit.Test;
import static com.akkafun.thwork.roman.RomanCalculator.calculateRomanSymbol;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Unit test for simple RomanCalculator.
 */
public class RomanCalculatorTest {

    @Test
    public void testCalculateCorrectRomanSymbols() {

        assertThat(calculateRomanSymbol("MCMXLIV"), is(1944L));

        assertThat(calculateRomanSymbol("MCMXLIVIII"), is(1947L));

        assertThat(calculateRomanSymbol("V"), is(5L));

        assertThat(calculateRomanSymbol("IV"), is(4L));

        assertThat(calculateRomanSymbol("VI"), is(6L));

        assertThat(calculateRomanSymbol("M"), is(1000L));

        assertThat(calculateRomanSymbol("CL"), is(150L));

        assertThat(calculateRomanSymbol("XIV"), is(14L));

        assertThat(calculateRomanSymbol("XVI"), is(16L));

        assertThat(calculateRomanSymbol("CCC"), is(300L));

        assertThat(calculateRomanSymbol("II"), is(2L));

        assertThat(calculateRomanSymbol("MMMD"), is(3500L));

        assertThat(calculateRomanSymbol("MDCLXVI"), is(1666L));

        assertThat(calculateRomanSymbol("MCMLXIV"), is(1964L));

        assertThat(calculateRomanSymbol(""), is(0L));

    }

    @Test
    public void testCalculateIncorrectRomanSymbols() {

        assertCalculatorExceptionHappened(null);

        assertCalculatorExceptionHappened("MCMLXIVA");

        assertCalculatorExceptionHappened("HMCMLXIV");

        assertCalculatorExceptionHappened("MCMLOXIV");

        assertCalculatorExceptionHappened("F");

        assertCalculatorExceptionHappened("MCML9XIV");

    }

    @Test
    public void testRomanSymbolsMustRepeatCorrectly() {

        assertCalculatorExceptionHappened("DD");

        assertCalculatorExceptionHappened("LL");

        assertCalculatorExceptionHappened("VV");

        assertCalculatorExceptionHappened("VVV");

        assertCalculatorExceptionHappened("IIII");

        assertCalculatorExceptionHappened("XXXX");

        assertCalculatorExceptionHappened("CCCC");

        assertCalculatorExceptionHappened("MMMM");

        assertCalculatorExceptionHappened("MCMXLIVIIII");

        assertCalculatorExceptionHappened("VVII");

        assertCalculatorExceptionHappened("VVIIII");

        assertCalculatorExceptionHappened("XXXVV");

        assertCalculatorExceptionHappened("XXXXV");

    }

    @Test
    public void testRomanSymbolsOrderMustCorrect() {

        assertCalculatorExceptionHappened("DM");

        assertCalculatorExceptionHappened("MDCLXVIVXLCDM");

        assertCalculatorExceptionHappened("LXVIM");

        assertCalculatorExceptionHappened("MXLCMIV");

        assertCalculatorExceptionHappened("LCIV");

        assertCalculatorExceptionHappened("CMMXLIV");

    }

    private void assertCalculatorExceptionHappened(String symbols) {

        try {

            calculateRomanSymbol(symbols);

            throw new AssertionError(String.format(
                    "Exception was expected when calculate symbols %s, but nothing happened. ", symbols));

        } catch (RomanCalculateException ignore) {
            System.out.println("expected error: " + ignore.getMessage());
        }

    }


}
