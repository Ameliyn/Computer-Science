package inclass;

public class Maximum {

    public static Comparable Max(Comparable[] A){

        Comparable currentMax = A[0];
        for(int i = 1; i < A.length; i++){
            if(currentMax.compareTo(A[i]) < 0) currentMax = A[i];
        }
        return currentMax;
    }
}


/**
 * Invariant Analysis
 *
 * Invariant:  MAX is the largest value on A[0 : k]
 * Assumption: MAX = max(A[0 : k-1])
 * Define:     max() = maximum value between two or more values (either an array or values)
 *                     ex. max(a,b) is a if a>b or b if b>a
 *                         max(A[]) is maximum value on that array
 *
 * Initialization:
 *   k = 1
 *   MAX = max(A[0 : 1-1]) = max(A[0])
 *   the Maximum of a length 1 array is always the element ==> MAX = A[0]
 *
 * Maintenance:
 *   MAX  = max(A[0 : k-1])           --Assumption
 *
 *   MAX  = max(MAX, A[k+1])          --Check max versus the next value
 *   MAX  = max(A[0 : (k+1)-1])       --we now know MAX is the maximum between 0 and k+1
 *   k++
 *   MAX  = max(A[0 : k-1])
 *
 * Termination:
 *   when k = A.Length, leave the loop
 *   MAX = max(A[0 : k-1])
 *   MAX = max(A[0 : Length-1])
 *   MAX = max(A[])
 *   Therefore, MAX is the maximum value on the whole array
 */
