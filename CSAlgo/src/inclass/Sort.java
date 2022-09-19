package inclass;

import edu.princeton.cs.algs4.StdOut;

public class Sort {


    public static void insertionSort(Comparable[] list){
        for(int i = 1; i < list.length; i++){
            Comparable temp = list[i];
            int j = i-1;
            while(j >= 0 && temp.compareTo(list[j]) < 0){
                list[j+1] = list[j];
                j--;
            }
            list[j+1] = temp;
        }
    }

    public static void main(String[] unused){
        Integer a[] = {5,4,3,2,1};
        //print the old list
        String result = printArray(a);
        insertionSort(a);
        result += " --> " + printArray(a);
        StdOut.println(result);

        Character b[] = {'p','i','r','a','t','e',' ','d','a','y'};
        result = printArray(b);
        insertionSort(b);
        result += " --> " + printArray(b);
        StdOut.println(result);


    }

    private static String printArray(Object[] a){
        String result = "{";
        for(int i = 0; i < a.length; i++){
            if(i+1 == a.length)
                result += a[i] + "}";
            else
                result += a[i] + ", ";
        }
        return result;
    }

}
