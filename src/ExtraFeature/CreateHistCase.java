package ExtraFeature;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class CreateHistCase {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter the file name : ");
        String filename = in.nextLine();
        filename = filename.trim();

        Random random = new Random();
        int numberdata = random.nextInt(1000);
        int numberbin = random.nextInt(20);
        PrintWriter writer=null;
        try {
            File Fileright = new File("C:\\Users\\User\\Downloads\\New Down\\HistBench\\" + filename + ".txt");
            boolean test = Fileright.createNewFile();
            if (test) {
                System.out.println("File created" + Fileright.getCanonicalPath());
                writer = new PrintWriter(Fileright);
                writer.println(numberdata + " " + numberbin);
                String dataset ="";
                for (int i = 0; i <numberdata; i++) {
                    int data = random.nextInt(1000);
                    dataset += data + " ";
                }
                writer.println(dataset);
                writer.println("ANS");
                String ans = String.valueOf(Ans(numberdata,numberbin,dataset));
                writer.println(ans);
            } else {
                System.out.println("File already exist" + Fileright.getCanonicalPath());
                System.out.println("File already exist" + Fileright.getCanonicalPath());
            }
        }
        catch(Exception e){

    }finally{
        writer.close();
        in.close();
    }
    }

    public static String Ans(int numberdata, int numberbin, String dataset){

        int no_of_data = numberdata;
        int no_of_bins = numberbin;

        int[] datapoints = new int[no_of_data];

        int b = 0;
        String[] split = dataset.split(" ");
        while (b < no_of_data) {
            datapoints[b] = Integer.parseInt(split[b]);
            b++;
        }

        int maximum = datapoints[0];
        int minimum = datapoints[0];

        for (int i = 0; i < datapoints.length; i++) {

            if (datapoints[i] > maximum) {
                maximum = datapoints[i];
            } else if (datapoints[i] < minimum) {
                minimum = datapoints[i];
            }
        }

        int cutoffs_value = (maximum - minimum) / no_of_bins;
        int sum = 0;
        int[] interval = new int[no_of_bins + 1];
        int[] count = new int[no_of_bins];
        String line = "";

        for (int j = 0; j < interval.length; j++) {
            if (j == 0) {
                sum += minimum;
                interval[j] = minimum;
                line += minimum + " ";
            } else {
                interval[j] = sum;
                line += sum + " ";
            }
            sum += cutoffs_value;
        }

        line += "\n";

        for (int k = 0; k < datapoints.length; k++) {
            for (int l = 0; l < interval.length - 1; l++) {
                if (datapoints[k] >= interval[l] && datapoints[k] < interval[l + 1]) {
                    count[l]++;
                }
            }
            if (datapoints[k] == interval[interval.length - 1]) {
                count[interval.length - 2]++;
            }
        }

        for (int m = 0; m < count.length; m++) {
            line += count[m] + " ";
        }
        return line;
    }
}
