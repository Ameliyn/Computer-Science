package homework;

//@author Skye Russ
public class Bubble {

    public static void sort(Comparable[] a){
        Comparable temporaryHolder;
        for(int i = 0; i < a.length-1; i++){
            for(int j = 0; j < a.length-i-1; j++){
                if(a[j].compareTo(a[j+1]) > 0){
                    //switch!
                    temporaryHolder = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temporaryHolder;
                }
            }
        }
    }
}


/*
 * //n = number of elements in the array, and array bounds are respected (I'm just not writing n-1 all the time)
 * Invariant: a[0, ..., n] is original a[0, ..., n] but sorted
 *
 * 1. Initialization:
 * i=0 -> a[n-i, ..., n] = a[n, ..., n] = a[n] is one element and therefore always sorted
 *
 * 2. Maintenance:
 * a[n - i, ..., n] sorted
 * a[i + 1]
 * a[n - (i + 1), ..., n] is sorted
 * i++
 * a[n - i, ..., n] sorted
 *
 *
 * 3. Termination
 * i >= n
 * i = n
 * a[n-i ,... , n] = a[0, ..., n] is sorted
 */
