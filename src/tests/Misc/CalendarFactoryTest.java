package Misc;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tzeyangng on 27/2/17.
 */
public class CalendarFactoryTest extends TestCase {
    public void testCreateCalendar() throws Exception {
        String input = "11 Nov 2016";
        String output = "2016-11-11";
        assertEquals(output,CalendarFactory.reformatDateString(input));
    }

    public void testGenerateDate() throws Exception{
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf2.parse("2017-04-04");
        String out = sdf2.format(date);
        System.out.print(out);
    }
}