package Algorithms;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by tzeyangng on 1/3/17.
 */
public class isDepositTest extends TestCase {
    public void testSolve() throws Exception {
        ArrayList<Integer> realList = new ArrayList<>();
        ArrayList<Integer> absList = new ArrayList<>();
        //int[] a = {23,-19,17,-13,-11,-7,5,3,-2,1};
        int sum=0;
        for (int i=0;i<10;i++){
            int random = (int)(Math.random()*200 - 100);
            realList.add(random);
            absList.add(Math.abs(random));
            sum += random;
        }

        System.out.println(realList.toString());
        System.out.println(absList.toString());
        System.out.println(sum);

        isDeposit uut = new isDeposit(absList,0,sum);
        System.out.println(uut.getAmountList().toString());
        System.out.println(uut.getTarget());
        uut.solve(uut.getAmountList(),uut.getTarget());

        ArrayList<Integer> uutList = uut.getNegativeList();
        System.out.println(uutList.toString());

        ArrayList<Integer> negList = new ArrayList<>();
        for(int i : realList){
            if (i<0){
                negList.add(Math.abs(i));
            }
        }
        System.out.println(negList.toString());
        Collections.sort(negList);
        Collections.sort(uutList);

        assertEquals(negList,uutList);

    }

}