import java.io.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        create_freqs();
//        String filename = "javadataset.R";
//        try {
//            FileOutputStream output = new FileOutputStream(filename, true);
//
//            create_dataset(128, 0, 1, false, output);
//            create_dataset(128, 1, 128, true, output);
//            create_dataset(128, 1, 512, true, output);
//            create_dataset(128, 1, 1024, true, output);
//            create_dataset(128, 1, 2048, true, output);
//
//            create_dataset(512, 0, 1, false, output);
//            create_dataset(512, 1, 128, true, output);
//            create_dataset(512, 1, 512, true, output);
//            create_dataset(512, 1, 1024, true, output);
//            create_dataset(512, 1, 2048, true, output);
//
//            create_dataset(1024, 0, 1, false, output);
//            create_dataset(1024, 1, 128, true, output);
//            create_dataset(1024, 1, 512, true, output);
//            create_dataset(1024, 1, 1024, true, output);
//            create_dataset(1024, 1, 2048, true, output);
//
//            create_dataset(2048, 0, 1, false, output);
//            create_dataset(2048, 1, 128, true, output);
//            create_dataset(2048, 1, 512, true, output);
//            create_dataset(2048, 1, 1024, true, output);
//            create_dataset(2048, 1, 2048, true, output);
//
//            output.close();
//        } catch (IOException n) {
//            System.out.println("WE BROKE IT!\n");
//        }
    }

    /**
     * Creates and appends to a file the dataset
     *
     * @param num_points integer number of points in set
     * @param limit_a    double lower limit of dataset
     * @param limit_b    double upper limit of dataset
     * @param integer    whether to create doubles or integers
     */
    private static int create_dataset(int num_points, double limit_a, double limit_b, boolean integer,
                                      FileOutputStream output) {
        Random random = new Random();
        try {
            byte[] byt;
            String pref = "java_" + num_points + "_" + limit_a + "_" + limit_b + " <- c(";
            output.write(pref.getBytes());
            for (int i = 0; i < num_points; i++) {
                if (integer) {
                    if (i + 1 != num_points)
                        byt = (Math.abs(random.nextInt() % limit_b) + 1 + ", ").getBytes();
                    else
                        byt = (Math.abs(random.nextInt() % limit_b) + 1 + ")\n").getBytes();
                } else {
                    if (i + 1 != num_points)
                        byt = (Math.abs(random.nextDouble()) + ", ").getBytes();
                    else
                        byt = (Math.abs(random.nextDouble()) + ")\n").getBytes();
                }
                output.write(byt);
                if (i % 20 == 0) {
                    output.write("\n".getBytes());
                }
            }

        } catch (IOException n) {
            System.out.println("WE BROKE IT INSIDE \n");
            return -1;
        }
        return 1;
    }

    private static int create_freqs() {

        String filename = "javaset.R";
        int num_points = 1000;
        int lower_limit = 1;
        int upper_limit = 100;
        Random random = new Random();
        byte[] byt;
        int[] freq;

        try {
            FileOutputStream output = new FileOutputStream(filename, true);

            for(int iter = 1; iter < 11; iter++) {

                freq = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

                for (int i = 0; i < num_points; i++) {
                    freq[(Math.abs(random.nextInt() % upper_limit)) / 10]++;
                }
                String pref = "java" + iter + "_" + num_points + "_" + lower_limit + "_" + upper_limit + " <- c(";
                output.write(pref.getBytes());
                for(int i = 0; i < 10; i++){
                    if (i + 1 != 10)
                        output.write((freq[i] + ", ").getBytes());
                    else
                        output.write((freq[i] + ")\n").getBytes());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }
}
