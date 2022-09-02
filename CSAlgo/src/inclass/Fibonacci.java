package inclass;
import edu.princeton.cs.algs4.*;

public class Fibonacci {

    public long fib(long n){
        if(n == 1) return 1;
        return n*fib(n-1);
    }
}
