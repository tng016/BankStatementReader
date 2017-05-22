package Misc;

/**
 * Created by tzeyangng on 18/3/17.
 */
public class Money {
    public static String printMoney(int DBmoney){
        String s = Integer.toString(DBmoney);
        int len = s.length();
        if(len>2){
            return "$" + s.substring(0,len - 2) + "." +s.substring(len-2);
        }
        if(len == 1){
            return "$0.0"+s;
        }
        if(len == 2){
            return "$0."+s;
        }
        return "Error printing value of money!";
    }
}
