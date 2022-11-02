package pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FingerprintMatcherTest {

    Matcher matcher;

    Random random;

    @BeforeEach
    public void setUp() {
        matcher = new FingerprintMatcher();
        random = new Random();
    }

    @Test
    void findsSmallMatchesInDenseArrays() {
        for (int i = 0; i < 10; i++) {
            findsMatch(3, 10, true);
        }
    }


    @Test
    void findsLargeMatchesInDenseArrays() {
        for (int i = 0; i < 10; i++) {
            findsMatch(100, 1000, true);
        }
    }

    @Test
    void findsSmallMatchesInSparseArrays() {
        for (int i = 0; i < 10; i++) {
            findsMatch(3, 10, false);
        }
    }

    @Test
    void findsLargeMatchesInSparseArrays() {
        for (int i = 0; i < 10; i++) {
            findsMatch(100, 1000, false);
        }
    }

    @Test
    void findsNonmatches() {
        int m = 3;
        int n = 10;
        for (int i = 0; i < 10; i++) {
            int[][] pattern = randomDenseArray(m);
            int[][] text = randomDenseArray(n);
            assertNull(matcher.match(pattern, text));
        }
    }

    /**
     * Creates a random mxm pattern and a random nxn text in which the pattern appears.
     * Returns true if matcher can find the match.
     *
     * @param dense If true, the pattern and text have only one non-zero element.
     */
    private void findsMatch(int m, int n, boolean dense) {
        int[][] pattern;
        int[][] text;
        if (dense) {
            pattern = randomDenseArray(m);
            text = randomDenseArray(n);
        } else {
            pattern = randomSparseArray(m);
            text = new int[n][n];
        }
        int i = random.nextInt(n + 1 - m);
        int j = random.nextInt(n + 1 - m);
        overwrite(pattern, text, i, j);
        int[] result = matcher.match(pattern, text);
        assertEquals(i, result[0]);
        assertEquals(j, result[1]);
    }

    private int[][] randomDenseArray(int width) {
        int[][] result = new int[width][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = random.nextInt();
            }
        }
        return result;
    }

    private int[][] randomSparseArray(int width) {
        int[][] result = new int[width][width];
        result[random.nextInt(width)][random.nextInt(width)] = random.nextInt();
        return result;
    }

    private void overwrite(int[][] pattern, int[][] text, int i, int j) {
        int m = pattern.length;
        for (int a = 0; a < m; a++) {
            for (int b = 0; b < m; b++) {
                text[a+i][b+j] = pattern[a][b];
            }
        }
    }

}