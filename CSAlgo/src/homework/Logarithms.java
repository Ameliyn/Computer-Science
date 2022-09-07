package homework;
//@author Skye Russ
//CS383: Algorithms Lab 1: Logarithms
public class Logarithms {

    public static int log(int a, int b){
        int i = 0;
        while(b/a >= 1){
            i++;
            b /= a;
        }
        return i;
    }
}
