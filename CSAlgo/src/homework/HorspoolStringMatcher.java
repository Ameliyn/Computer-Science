package homework;

public class HorspoolStringMatcher {

    private char[] needle;
    private int[] shiftTable;

    public HorspoolStringMatcher(String needle){
        this.needle = needle.toCharArray();
        GenerateShiftTable();
    }

    private void GenerateShiftTable(){
        shiftTable = new int[256];
        for(int i = 0; i < 257; i++){
            shiftTable[i] = getShift((char)i);
        }
    }

    /**
     * returns how many you can jump before the next occurrence of this letter
     * @param c
     * @return
     */
    public int getShift(char c){
        int firstIndex = needle.length;
        for(int i = 0; i < needle.length; i++){
            if(needle[i] == c){
                firstIndex = needle.length-1-i;
                break;
            }
        }
        return firstIndex;
    }

    /**
     * match returns the first index of the needle in the haystack
     * @param haystack string to be searched
     * @return first index of needle in haystack
     */
    public int match(String haystack){
        return 0;
    }
}
