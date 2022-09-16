package bignum;

/**
 * CS Algorithm Design and Analysis Group Project 1: BigNum
 * @Author Skye Russ, Anders Stall, Katie Shimaura, Nuzhat Hoque
 */
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
    public BigNum times(BigNum other){
        if(other.number.length() == 0 || number.length() == 0) return new BigNum("0");

        char[] selfChars = number.toCharArray();
        char[] otherChars = other.number.toCharArray();
        int carry = 0;
        int[] resultList = new int[selfChars.length+ otherChars.length];
        int selfZeroes = 0; int otherZeroes = 0;

        for(int i = number.length()-1; i >= 0; i--){

            otherZeroes = 0;
            for(int j = other.number.length()-1; j >= 0; j--){
                //find current product
                int prod = (selfChars[i] - '0') * (otherChars[j] - '0') + carry;

                //find where the 0s place of the product will be (0s place, 10s place, 100s place, etc)
                int index = selfZeroes + otherZeroes;


                //Handle adding to our array
                int tempSum = resultList[resultList.length-index-1] + prod;
                do{
                    resultList[resultList.length-1-index] = (tempSum % 10);
                    carry = tempSum / 10;
                    index++;
                    tempSum = resultList[resultList.length-index-1] + carry;
                }while(carry > 0);

                otherZeroes++;
            }
            selfZeroes++;
        }

        //turn our int array into a char array (and remove the leading 0s)
        char[] answer = new char[1];
        int offset = -1;
        for(int i = 0; i < resultList.length; i++){
            if(resultList[i] != 0 && offset == -1) {
                answer = new char[resultList.length-i];
                offset = i;
                answer[i-offset] = (char)(resultList[i] + '0');
            }
            else if (offset != -1){
                answer[i-offset] = (char)(resultList[i] + '0');
            }
        }
        return new BigNum(new String(answer));
    }


    public BigNum times2(BigNum other){
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
