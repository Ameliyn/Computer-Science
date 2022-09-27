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
 * Invariant: r = k!
 *
 * 1. Initialization:
 *   r = 1; k = 1; ==> r = 1 = k = 1!
 *
 * 2. Maintenance:
 *   k' = k+1, r' = r*k'
 *   r = k! (assumed)
 *   r*(k+1) = k!(k+1)
 *   r*(k+1) = (k+1)!
 *   r*k' = (k')!
 *   r' = (k')!
 *
 * 3. Termination:
 *   Exit the loop when k = n
 *   r = k!
 *   r = n!
 *
 * Speed:
 *   Execute the loop n-1 times ==> independant of n ==> speed = Big Theta(n)
 *   (Worst, best, and average case all same)
 */
