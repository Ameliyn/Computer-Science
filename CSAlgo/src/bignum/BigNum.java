package bignum;

import edu.princeton.cs.algs4.StdOut;

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

    public BigNum times(BigNum other){
        if(other.number.length() == 0 || number.length() == 0) return new BigNum("0");

        BigNum result = new BigNum("");

        char[] selfChars = number.toCharArray();
        char[] otherChars = other.number.toCharArray();

        for(int i = number.length()-1; i >= 0; i--){
            for(int j = other.number.length()-1; j >= 0; j--){
                Integer sum = (selfChars[i] - 48) * (otherChars[j] - 48);
                String tempResult = sum.toString();

                //add the 0s for the other
                for(int k = 0; k < other.number.length()-j-1; k++){
                    tempResult += "0";
                }

                //add the 0s for the self
                for(int k = 0; k < number.length()-i-1; k++){
                    tempResult += "0";
                }

                result = result.plus(new BigNum(tempResult));
            }
        }
        return result;
    }

    public BigNum plus(BigNum other){
        int carry = 0;
        int tempSum;
        String result = "";

        int i = number.length()-1;
        int j = other.number.length()-1;

        char[] selfChars = number.toCharArray();
        char[] otherChars = other.number.toCharArray();

        while(!(j==-1 && i==-1)){
            if(i < 0 && j>=0){
                tempSum = carry + otherChars[j] - 48;
                j--;
            }
            else if(j < 0 && i>=0){
                tempSum = carry + selfChars[i] - 48;
                i--;
            }
            else {
                tempSum = carry + otherChars[j] + selfChars[i] - 48*2;
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
        String inp = "300000000000";
        String inp2 = "400000000000";

        BigNum one = new BigNum(inp);
        BigNum two = new BigNum(inp2);

        StdOut.println(one.times(two));
    }
}
