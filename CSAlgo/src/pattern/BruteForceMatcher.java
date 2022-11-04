package pattern;

public class BruteForceMatcher implements Matcher {

    public BruteForceMatcher(){

    }

    @Override
    public int[] match(int[][] pattern, int[][] text) {
        boolean match = true;
        int[] result = new int[2];

        for(int i = 0; i <= (text.length - pattern.length); i++){
            for(int j = 0; j <= (text[0].length - pattern[0].length); j++){
                for(int patI = 0; patI < pattern.length; patI++){
                    for(int patJ = 0; patJ < pattern[patI].length; patJ++){
                        if(text[i + patI][j + patJ] != pattern[patI][patJ]) {
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }
}
