package inclass;

public class Factorial {


    public static int Factorial(int n){
        int sum = 1;
        for(int i = 2; i <= n; i++){
            sum *= i;
        }
        return sum;
    }
}


/**
 * Invariant: Factorial(n) = 1*2*3*...*n-1*n
 *
 * 1. Initialization: Factorial(1) = 1*n-1
 * Speed = n
 */
