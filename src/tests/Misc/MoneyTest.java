package Misc;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tzeyangng on 18/3/17.
 */
public class MoneyTest {
    @Test
    public void strToInt() throws Exception {
        assertEquals(111123456,Money.strToInt("1,111,234.56"));
    }

    @Test
    public void printMoney() throws Exception {
        assertEquals("$32.00",Money.printMoney(3200));
    }

}