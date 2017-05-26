package Budget;

import java.util.HashMap;

/**
 * Created by tzeyangng on 18/3/17.
 */
public class Budget {

    public static String[] type = {"Unknown","Credit Card","Cash Withdrawal","Accommodation","Education","Projects","Phone","Food",
            "Groceries","Transport","Personal","Gifts","Charity","Holidays", "Insurance","Misc Spending",
            "","Army", "Interest","Dividends" ,"Income"};

    public static int count = type.length;

    public static void printCategories(){
        for (int i=0;i<type.length;i++){
            System.out.println("("+i+") "+type[i]);
        }
    }

    public static String parseType(int i){
        return type[i];
    }

}
