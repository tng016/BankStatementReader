package Budget;

import java.util.HashMap;

/**
 * Created by tzeyangng on 18/3/17.
 */
public class Budget {
    public static String[] type = {"","Accommodation", "Phone","Food","Groceries","Credit Card","Transport","Leisure",
            "Projects", "Army", "Insurance", "HolidaysF", "Misc Spending","Interest","Dividends" ,"Income"};

    public static void printCategories(){
        for (int i=1;i<type.length;i++){
            System.out.println("("+i+") "+type[i]);
        }
    }

    public static String parseType(int i){
        return type[i];
    }

}
