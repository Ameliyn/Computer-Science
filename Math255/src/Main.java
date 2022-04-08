import java.io.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        String filename = "javadataset.R";
        try{
            FileOutputStream output = new FileOutputStream(filename, true);

            create_dataset(128, 0, 1, false, output);
            create_dataset(128, 1, 128, true, output);
            create_dataset(128, 1, 512, true, output);
            create_dataset(128, 1, 1024, true, output);
            create_dataset(128, 1, 2048, true, output);

            create_dataset(512, 0, 1, false, output);
            create_dataset(512, 1, 128, true, output);
            create_dataset(512, 1, 512, true, output);
            create_dataset(512, 1, 1024, true, output);
            create_dataset(512, 1, 2048, true, output);

            create_dataset(1024, 0, 1, false, output);
            create_dataset(1024, 1, 128, true, output);
            create_dataset(1024, 1, 512, true, output);
            create_dataset(1024, 1, 1024, true, output);
            create_dataset(1024, 1, 2048, true, output);

            create_dataset(2048, 0, 1, false, output);
            create_dataset(2048, 1, 128, true, output);
            create_dataset(2048, 1, 512, true, output);
            create_dataset(2048, 1, 1024, true, output);
            create_dataset(2048, 1, 2048, true, output);

            output.close();
        }catch(IOException n){
            System.out.println("WE BROKE IT!\n");
        }
    }

    /**
     * Creates and appends to a file the dataset
     * @param num_points integer number of points in set
     * @param limit_a double lower limit of dataset
     * @param limit_b double upper limit of dataset
     * @param integer whether to create doubles or integers
     */
    private static int create_dataset(int num_points, double limit_a, double limit_b, boolean integer,
                                      FileOutputStream output){
        Random random = new Random();
        try {
            byte[] byt;
            String pref = "java_" + num_points + "_" + limit_a + "_" + limit_b + " <- c(";
            output.write(pref.getBytes());
            for(int i = 0; i < num_points; i++){
                if(integer){
                    if(i+1 != num_points)
                        byt = (Math.abs(random.nextInt()%limit_b)+1 + ", ").getBytes();
                    else
                        byt = (Math.abs(random.nextInt()%limit_b)+1 + ")\n").getBytes();
                }
                else{
                    if(i+1 != num_points)
                        byt = (Math.abs(random.nextDouble()) + ", ").getBytes();
                    else
                        byt = (Math.abs(random.nextDouble()) + ")\n").getBytes();
                }
                output.write(byt);
            }

        } catch(IOException n){
            System.out.println("WE BROKE IT INSIDE \n");
            return -1;
        }
        return 1;
    }
}