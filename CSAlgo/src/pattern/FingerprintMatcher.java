package pattern;

public class FingerprintMatcher implements Matcher{

    @Override
    public int[] match(int[][] pattern, int[][] text) {

        //get initial value of pattern
        int patternValue = GetValue(pattern, 0,0, pattern.length);

        //Get initial value of text section
        int runningValue = GetValue(text, 0, 0, pattern.length);

        int i = 0;
        while(i < text.length - pattern.length) {
            // go right
            for(int j = 0; j <= text[0].length - pattern[0].length; j++){
                if(runningValue == patternValue && CheckSection(pattern, text, i, j)) {
                    return new int[] {i,j};
                }
                runningValue = ShiftSection(text, i, j, 'R', pattern.length, runningValue);
            }
            runningValue = ShiftSection(text, i, text[0].length-pattern.length, 'D', pattern.length, runningValue);
            i++;

            // go left
            for(int j = text[0].length-pattern.length; j >= 0; j--){
                if(runningValue == patternValue && CheckSection(pattern, text, i, j)) {
                    return new int[] {i,j};
                }
                runningValue = ShiftSection(text, i, j, 'L', pattern.length, runningValue);
            }
            runningValue = ShiftSection(text, i, 0, 'D', pattern.length, runningValue);
            i++;
        }
        return null;
    }

    /**
     * CheckSection checks if the pattern is equal to the portion of text starting at i,j
     * @return boolean True if match
     */
    private boolean CheckSection(int[][] pattern, int[][] text, int i, int j){
        int initialJ = j;
        for(int k = 0; k < pattern.length; k++){
            for(int l = 0; l < pattern[0].length; l++){
                if(text[i][j] != pattern[k][l]) return false;
                j++;
            }
            i++;
            j = initialJ;
        }
        return true;
    }

    /**
     * ShiftSection shifts the given section starting at i,j in the direction and returns the new value
     * @return integer value
     */
    private int ShiftSection(int[][] text, int i, int j, char direction, int patternLength, int currentValue){
        switch (direction) {
            case 'L' -> {
                if (j == 0) break;
                for (int k = i; k < i + patternLength; k++) {
                    currentValue -= text[k][j + patternLength - 1];
                    currentValue += text[k][j - 1];
                }
            }
            case 'R' -> {
                if (j + patternLength == text.length) break;
                for (int k = i; k < i + patternLength; k++) {
                    currentValue -= text[k][j];
                    currentValue += text[k][j + patternLength];
                }
            }
            case 'D' -> {
                if (i + patternLength == text.length) break;
                for (int k = j; k < j + patternLength; k++) {
                    currentValue -= text[i][k];
                    currentValue += text[i + patternLength][k];
                }
            }
        }
        return currentValue;
    }

    /**
     * GetValue returns the sum of all elements in the given length
     * @return integer value
     */
    private int GetValue(int[][] array, int i, int j, int length){
        int value = 0;
        for(int y = i; y < i+length; y++){
            for(int x = j; x < j+length; x++){
                value += array[y][x];
            }
        }
        return value;
    }
}
