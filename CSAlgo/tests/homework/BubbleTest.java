package homework;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BubbleTest {

    @Test
    void sorts() {
        // Generate array of random numbers
        Double[] a = new Double[1000];
        for (int i = 0; i < a.length; i++) {
            a[i] = StdRandom.uniform();
        }
        // Copy into backup array
        Double[] b = new Double[a.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = a[i];
        }
        // Sort both arrays using different methods
        java.util.Arrays.sort(a);
        Bubble.sort(b);
        // Check that the results are the same
        assertArrayEquals(a, b);
    }

}