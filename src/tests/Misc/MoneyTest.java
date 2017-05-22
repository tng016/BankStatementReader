package Misc;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tzeyangng on 18/3/17.
 */
public class MoneyTest {
    @Test
    public void printMoney() throws Exception {
        assertEquals("$32.00",Money.printMoney(3200));
    }

}