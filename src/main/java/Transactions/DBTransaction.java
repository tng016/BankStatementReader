package Transactions;

import Budget.Budget;
import Misc.CalendarFactory;
import Misc.Money;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tzeyangng on 18/3/17.
 */
public class DBTransaction {
    private int id;
    private Date date;
    private int amount;
    private String details;
    private boolean isDeposit;
    private int type;

    public DBTransaction(int id, String date, int amount, String details, boolean isDeposit, int type) {
        this.id = id;
        try {
            this.date = CalendarFactory.generateDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.amount = amount;
        this.details = details;
        this.isDeposit = isDeposit;
        this.type = type;
    }
    public DBTransaction(int id, Date date, int amount, String details, boolean isDeposit, int type) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.details = details;
        this.isDeposit = isDeposit;
        this.type = type;
    }

    public void printTransaction(){
        System.out.println("Date of transaction: " + date);
        System.out.println("Details of transaction: " + details);
        System.out.println("Amount Transferred: " + Money.printMoney(amount));
        System.out.println("Type: " + Budget.type[type]);
        System.out.println("Is Deposit: " + isDeposit);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int isDeposit() {
        return isDeposit?1:0;
    }

    public void setDeposit(boolean deposit) {
        isDeposit = deposit;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String generateDateString(){
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String out = sdf2.format(date);
        return out;
    }
}
