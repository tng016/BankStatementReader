package Algorithms;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tzeyangng on 28/2/17.
 */
public class isDeposit {
    public ArrayList<Integer> negativeList;
    public ArrayList<Integer> amountList;
    public int target;

    public isDeposit(ArrayList<Integer> amountList, int previousBalance, int currentBalance){
        negativeList = new ArrayList<>();
        this.amountList = amountList;
        prepareAmountList();
        prepareTarget(previousBalance,currentBalance);
    }

    public boolean solve(ArrayList<Integer> amountList,int target){

        if (amountList.isEmpty()){
            return false;
        }

        int current = amountList.remove(0);


        if (current > target){
            if (solve(amountList,target)){
                return true;
            }
        }

        else if (current == target){
            negativeList.add(current);
            return true;
        }

        else {
            negativeList.add(current);
            return solve(amountList,target-current);
        }
        return false;
    }

    public void prepareAmountList(){
        Collections.sort(amountList);
        Collections.reverse(amountList);
    }

    public void prepareTarget(int previousBalance, int currentBalance){
        int balanceDifference,sumAmounts = 0;
        balanceDifference = currentBalance - previousBalance;
        for (Integer b : amountList) {
            sumAmounts += b;
        }
        target = (sumAmounts - balanceDifference)/2;
    }

    public ArrayList<Integer> getNegativeList() {
        return negativeList;
    }

    public ArrayList<Integer> getAmountList() {
        return amountList;
    }

    public int getTarget() {
        return target;
    }
}
