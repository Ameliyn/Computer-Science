package inclass;

import edu.princeton.cs.algs4.StdOut;

public class Poly {

    private double[] coeffs;

    public Poly(double[] coeffs){
        this.coeffs = coeffs;
    }

    double eval_recursive(double x){
        double sum = 0;
        for(int i = coeffs.length-1; i >= 0; i--){
            sum += coeffs[i]*(power(x,i));
        }
        return sum;
    }

    private double power(double value, int power){
        if(power == 0) return 1;
        if(power == 1) return value;
        return value * power(value,power-1);
    }

    double eval(double x){
        double sum = 0;
        double currentx = 1;
        for(int i = coeffs.length-1; i >= 0; i--){
            sum += coeffs[i]*currentx;
            currentx *= x;
        }
        return sum;
    }

    public static void main(String[] args){
        Poly pol = new Poly(new double[]{1,2,1});
        StdOut.println("Poly coefs 1,2,1");
        StdOut.println("x=0 | " + pol.eval(0));
        StdOut.println("x=1 | " + pol.eval(1));
        StdOut.println("x=2 | " + pol.eval(2));
        StdOut.println("x=3 | " + pol.eval(3));


    }
}
