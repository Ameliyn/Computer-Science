package bignum;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BigNum {

    ArrayList<Character> charlist;
    String strNum;

    public BigNum(String number){
        if(number.equals("")) strNum = "0";
        else strNum = number;
        charlist = new ArrayList<Character>();
        for(char c: number.toCharArray()){
            charlist.add(c);
        }
    }

    @Override
    public String toString(){
        return strNum;
    }

    public BigNum times(BigNum bigs){
        return bigs;
    }

    public BigNum plus(BigNum bigs){
        int carry = 0;
        int tempSum;
        String result = "";

        int i = charlist.size()-1;
        int j = bigs.charlist.size()-1;

        while(!(j==-1 && i==-1)){
            if(i < 0 && j>=0){
                tempSum = carry + bigs.charlist.get(j) - 48;
                j--;
            }
            else if(j < 0 && i>=0){
                tempSum = carry + charlist.get(i) - 48;
                i--;
            }
            else {
                tempSum = carry + bigs.charlist.get(j) + charlist.get(i) - 48*2;
                i--;
                j--;
            }
            if(tempSum / 10 != 0) carry = 1;
            else carry = 0;

            result = ((tempSum)%10) + result;
        }

        if(carry == 1) result = '1' + result;


        return new BigNum(result);
    }

    public static void main(String[] args){
        String inp = "12345";
        ArrayList<Character> newcharlist = new ArrayList<Character>();
        for(char c: inp.toCharArray()){
            newcharlist.add(c);
        }

        StdOut.println(new BigNum("1234").plus(new BigNum("111")));
    }
}
