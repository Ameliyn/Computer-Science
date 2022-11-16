package inclass;
import edu.princeton.cs.algs4.*;

public class Fibonacci {

    public static void main(String args[]){
        StdOut.println(fast_small_fib(25));
    }

    //the speed is O(2^n)
    public static long fib(long n){
        if(n == 0 || n == 1) return n;
        return fib(n-1) + fib(n-2);
    }

    //By caching the value, the speed becomes O(n)
    public static long[] cached = new long[1000];

    //O(n) speed with O(n) space
    public static long fast_fib(long n){
        if(cached[(int)n] != 0) return cached[(int)n];
        else if(n == 0 || n == 1) cached[(int)n] = n;
        else cached[(int)n] = fast_fib(n-1) + fast_fib(n-2);
        return cached[(int)n];
    }

    //O(n) speed with less than O(n) space
    public static long fast_small_fib(long n){
        return fast_small_fib_helper(n,0,1);
    }

    private static long fast_small_fib_helper(long n, long a, long b){
        if(n == 0) return a;
        else if (n==1) return b;
        return fast_small_fib_helper(n-1, b, a+b);
    }
}
