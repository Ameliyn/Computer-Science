package inclass;
import edu.princeton.cs.algs4.*;

public class Fibonacci {

    public static void main(String args[]){
        for(int n = 0; n < 10; n++){
            StdOut.println("Fib(" + n + ") = " + fast_fib(n));
        }
        StdOut.println("Fib(" + 500 + ") = " + fast_fib(500));
    }

    //the speed is O(2^n)
    public static long fib(long n){
        if(n == 0 || n == 1) return n;
        return fib(n-1) + fib(n-2);
    }

    //By caching the value, the speed becomes O(n)
    public static long[] cached = new long[1000];

    public static long fast_fib(long n){
        if(cached[(int)n] != 0) return cached[(int)n];
        else if(n == 0 || n == 1) cached[(int)n] = n;
        else cached[(int)n] = fast_fib(n-1) + fast_fib(n-2);
        return cached[(int)n];
    }
}
