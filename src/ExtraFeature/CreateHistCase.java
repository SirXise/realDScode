package ExtraFeature;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class CreateHistCase {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter the file name : ");
        String filename = in.nextLine();
        filename = filename.trim();

        Random random = new Random();
        int numberdata = random.nextInt(100000);
        int numberbin = random.nextInt(40);
        PrintWriter writer=null;
        try {
            File Fileright = new File("HistCase\\" + filename + ".txt");
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

        int nodata = numberdata;
        int nobins = numberbin;

        Integer[] datapoints = new Integer[nodata];
        String[] split = dataset.split(" ");
        for(int b = 0;b<nodata;b++) {
            datapoints[b] = Integer.parseInt(split[b]);
        }

        int max = Collections.max(Arrays.asList(datapoints));
        int min = Collections.min(Arrays.asList(datapoints));

        int cutoffs = (max - min) / nobins;
        int[] interval = new int[nobins + 1];
        int[] count = new int[nobins];

        for (int i = 0; i < interval.length; i++) {
            interval[i] = min;
            min += cutoffs;
        }

        Loop:
        for (int data : datapoints) {
            for (int k = 0; k < interval.length - 2; k++) {
                if (data < interval[k + 1]) {
                    count[k]++;
                    continue Loop;
                }
            }
            count[nobins-1]++;
        }

        String line="";
        for (int cutoff: interval) {
            line += cutoff + " ";
        }

        line+="\n";
        for (int nocount: count) {
            line += nocount + " ";
        }
        return line;
    }
}
