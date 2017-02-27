package Misc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tzeyangng on 27/2/17.
 */
public class CalendarFactory {
    public static String reformatDateString(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("YYYY-MM-dd");
        Date date = sdf.parse(s);
        String out = sdf2.format(date);
        return out;
    }

}
