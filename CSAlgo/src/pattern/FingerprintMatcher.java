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
            for(int j = 0; j < text[0].length - pattern[0].length; j++){
                if(runningValue == patternValue) return new int[] {i,j};

                if(j+1 == text[0].length-pattern[0].length) break;
                for(int k = i; k < i + pattern.length; k++){
                    runningValue -= text[k][j];
                    runningValue += text[k][j+pattern.length];
                }
                //shift right
            }

            if(i+1 == text.length - pattern.length) break;
            //shift down
            for(int k = text[0].length-1; k > text[0].length - pattern[0].length; k--){
                runningValue -= text[i][k];
                runningValue += text[i+pattern.length][k];
            }
            i++;

            // go left
            for(int j = text[0].length-1; j > pattern[0].length; j--){
                if(runningValue == patternValue) return new int[] {i,j};

                //shift left
                if(j-1 == pattern[0].length) break;
                for(int k = i; k < i + pattern.length; k++){
                    runningValue -= text[k][j];
                    runningValue += text[k][j-pattern.length];
                }
            }

            //shift down

            if(i+1 == text.length - pattern.length) break;
            for(int k = 0; k < pattern.length; k++){
                runningValue -= text[i][k];
                runningValue += text[i+pattern.length][k];
            }
            i++;
        }



        return null;
    }
}
