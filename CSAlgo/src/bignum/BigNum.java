package bignum;

import java.util.ArrayList;

public class BigNum {

    ArrayList<Character> charlist;
    String strNum;

    public BigNum(String number){
        strNum = number;
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

        while(i > 0 || j > 0){
            if(i == 0){
                tempSum = carry + bigs.charlist.get(j) - 48;
                j--;
            }
            else if(j == 0){
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

            result = (tempSum%10) + result;
        }

        if(carry == 1) result = '1' + result;


        return new BigNum(result);
    }
}
