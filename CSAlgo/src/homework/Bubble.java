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
