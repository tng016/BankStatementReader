/**
 * Created by tzeyangng on 27/2/17.
 */
public class forTrials {
    public static void main (String[] args){
        int numberOfBits = 3;
        int binaryNumber = (int) (Math.pow(2,numberOfBits)-1);
        String binaryString = Integer.toBinaryString(binaryNumber);
        System.out.println(binaryString);
    }
}
