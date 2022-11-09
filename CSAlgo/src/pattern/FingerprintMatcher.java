package pattern;

public class FingerprintMatcher implements Matcher{

    public FingerprintMatcher(){

    }

    @Override
    public int[] match(int[][] pattern, int[][] text) {

        //get initial value of pattern
        int patternValue = 0;
        for(int i = 0; i < pattern.length; i++){
            for(int j = 0; j < pattern[0].length; j++){
                patternValue += pattern[i][j];
            }
        }

        //Get initial value of text section
        int runningValue = 0;
        for(int i = 0; i < pattern.length; i++){
            for(int j = 0; j < pattern[0].length; j++){
                runningValue += text[i][j];
            }
        }

        int i = 0;
        while(i < text.length - pattern.length) {

            // go right
            for(int j = 0; j <= text[0].length - pattern[0].length; j++){
                if(runningValue == patternValue) {
                    if(CheckSection(pattern, text, i, j))
                        return new int[] {i,j};
                }

                if(j == text[0].length-pattern[0].length) break;
                for(int k = i; k < i + pattern.length; k++){
                    runningValue -= text[k][j];
                    runningValue += text[k][j+pattern.length];
                }
                //shift right
            }

            if(i == text.length - pattern.length) break;
            //shift down
            for(int k = text[0].length-1; k >= text[0].length - pattern[0].length; k--){
                runningValue -= text[i][k];
                runningValue += text[i+pattern.length][k];
            }
            i++;

            // go left
            for(int j = text[0].length-pattern.length; j >= 0; j--){
                if(runningValue == patternValue) {
                    if(CheckSection(pattern, text, i, j))
                        return new int[] {i,j};
                }

                //shift left
                if(j == 0) break;
                for(int k = i; k < i + pattern.length; k++){
                    runningValue -= text[k][j+pattern.length-1];
                    runningValue += text[k][j-1];
                }
            }

            //shift down

            if(i == text.length - pattern.length) break;
            for(int k = 0; k < pattern.length; k++){
                runningValue -= text[i][k];
                runningValue += text[i+pattern.length][k];
            }
            i++;
        }
        return null;
    }

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
}
