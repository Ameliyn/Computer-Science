package pattern;

public class BruteForceMatcher implements Matcher {

    public BruteForceMatcher(){

    }

    @Override
    public int[] match(int[][] pattern, int[][] text) {
        for(int i = 0; i <= (text.length - pattern.length); i++){
            for(int j = 0; j <= (text[0].length - pattern[0].length); j++){
                if(CheckSection(pattern, text, i, j)) return new int[]{i,j};
            }
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
