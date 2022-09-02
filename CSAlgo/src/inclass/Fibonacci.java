package inclass;
import edu.princeton.cs.algs4.*;

public class Fibonacci {

    public static void main(String args[]){
        for(int n = 0; n < 10; n++){
            StdOut.println("Fib(" + n + ") = " + fib(n));
        }
    }
    
    public static long fib(long n){
        if(n == 0 || n == 1) return n;
        return fib(n-1) + fib(n-2);
    }
}
