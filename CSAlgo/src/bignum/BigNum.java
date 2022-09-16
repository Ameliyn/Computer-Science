package bignum;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BigNum {

    String number;

    public BigNum(String number){
        if(number.equals("")) this.number = "0";
        else this.number = number;
    }

    @Override
    public String toString(){
        return number;
    }

    //this is an attempt at speeding up the times function
    public BigNum times2(BigNum other){
        if(other.number.length() == 0 || number.length() == 0) return new BigNum("0");

        char[] selfChars = number.toCharArray();
        char[] otherChars = other.number.toCharArray();
        int carry = 0;
        char[] resultList = new char[selfChars.length+ otherChars.length];
        int selfZeroes = 0; int otherZeroes = 0;

        for(int i = number.length()-1; i >= 0; i--){

            otherZeroes = 0;
            for(int j = other.number.length()-1; j >= 0; j--){
                //find current product
                int prod = (selfChars[i] - '0') * (otherChars[j] - '0') + carry;

                //find new carry (10s place of product)
                carry = prod / 10;

                //find where the 0s place of the product will be (0s place, 10s place, 100s place, etc)
                int index = selfZeroes + otherZeroes;

                //TODO: add to the correct index in resultList

                otherZeroes++;
            }
            selfZeroes++;
        }
        return new BigNum(new String(resultList));
    }


    public BigNum times(BigNum other){
        if(other.number.length() == 0 || number.length() == 0) return new BigNum("0");

        BigNum result = new BigNum("");

        char[] selfChars = number.toCharArray();
        char[] otherChars = other.number.toCharArray();
        for(int i = number.length()-1; i >= 0; i--){
            for(int j = other.number.length()-1; j >= 0; j--){
                int prod = (selfChars[i] - '0') * (otherChars[j] - '0');

                //get the string representation of the current number
                String tempResult = Integer.toString(prod) +
                        "0".repeat(Math.max(0, other.number.length() - j - 1)) +
                        "0".repeat(Math.max(0, number.length() - i - 1));

                result = result.plus(new BigNum(tempResult));
            }
        }
        return result;
    }

    public BigNum plus(BigNum other){
        int carry = 0;
        int tempSum;
        StringBuilder result = new StringBuilder();

        int i = number.length()-1;
        int j = other.number.length()-1;

        char[] selfChars = number.toCharArray();
        char[] otherChars = other.number.toCharArray();

        while(!(j==-1 && i==-1)){
            if(i < 0 && j>=0){
                tempSum = carry + otherChars[j] - '0';
                j--;
            }
            else if(j < 0 && i>=0){
                tempSum = carry + selfChars[i] - '0';
                i--;
            }
            else {
                tempSum = carry + otherChars[j] + selfChars[i] - ('0'*2);
                i--;
                j--;
            }
            if(tempSum / 10 != 0) carry = 1;
            else carry = 0;

            result.insert(0, ((tempSum) % 10));
        }

        if(carry == 1) result.insert(0, '1');


        return new BigNum(result.toString());
    }
}
