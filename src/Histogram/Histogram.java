/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Histogram;

import java.util.*;
import java.lang.*;
/**
 *
 */
public class Histogram {
    public static void main(String[] args) {

        Scanner input_data = new Scanner(System.in);
        int nocases = input_data.nextInt();

        int c = 0;
        while (c < nocases) {
            int nodata = input_data.nextInt();
            int nobins = input_data.nextInt();

            Integer[] datapoints = new Integer[nodata];
            for(int b = 0;b<nodata;b++) {
                datapoints[b] = input_data.nextInt();
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
            System.out.println(line);

            String line1="";
            for (int nocount: count) {

                line1 += nocount + " ";
            }
            System.out.println(line1);

            c++;
        }
    }

}