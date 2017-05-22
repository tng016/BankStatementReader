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

    public boolean solve(ArrayList<Integer> amountNewList,int target){

        ArrayList<Integer> amountNewsList = (ArrayList<Integer>)amountNewList.clone();

        if (amountNewsList.isEmpty()){
            if (target == 0){
                return true;
            }
            else{
                return false;
            }
        }

        int current = amountNewsList.remove(0);


        if (current > target){
            return (solve(amountNewsList,target));
        }

        else {
            negativeList.add(current);
            if(solve(amountNewsList,target-current)){
                return true;
            }
            else{
                negativeList.remove(negativeList.size()-1);
                return(solve(amountNewsList,target));
            }
        }
    }

    public boolean solve2(ArrayList<Integer> amountList,int target){

        return true;
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
