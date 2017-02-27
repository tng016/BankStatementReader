package Misc;

import junit.framework.TestCase;

/**
 * Created by tzeyangng on 27/2/17.
 */
public class CalendarFactoryTest extends TestCase {
    public void testCreateCalendar() throws Exception {
        String input = "11 Nov 2016";
        String output = "2016-11-11";
        assertEquals(output,CalendarFactory.reformatDateString(input));
    }

}