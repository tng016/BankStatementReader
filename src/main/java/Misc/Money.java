package Misc;

/**
 * Created by tzeyangng on 18/3/17.
 */
public class Money {
    public static String printMoney(int DBmoney){
        String s = Integer.toString(DBmoney);
        int len = s.length();
        return "$";
    }
}
