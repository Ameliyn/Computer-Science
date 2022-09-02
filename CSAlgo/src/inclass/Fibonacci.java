package inclass;
import edu.princeton.cs.algs4.*;

public class Fibonacci {

    public long fib(long n){
        if(n == 0 || n == 1) return n;
        return fib(n-1) + fib(n-2);
    }
}
