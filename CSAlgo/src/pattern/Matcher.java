package pattern;

public interface Matcher {

    /** Returns the position {i, j} where pattern appears in text, or null if it does not. */
    public int[] match(int[][] pattern, int[][] text);

}