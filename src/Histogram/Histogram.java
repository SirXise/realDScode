/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Histogram;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Histogram {

    public static void main(String[] args){
        Scanner input_data = new Scanner(System.in); //scanner class

        System.out.print("Number of cases: ");//prompt user to input number of cases
        int nocases = input_data.nextInt();//initialise the variable for number of cases

        int c = 0;
        while (c < nocases) {
            int nodata = input_data.nextInt();//initialise the variable for number of data
            int nobins = input_data.nextInt();//initialise the variable for number of bins

            Integer[] datapoints = new Integer[nodata];//initialise array for datapoints with value of number of data as index

            //loop to input datapoints
            for(int b = 0;b<nodata;b++){
                datapoints[b] = input_data.nextInt();
            }

            int max = Collections.max(Arrays.asList(datapoints));
            //max() method will return the maximum element in the array and takes array.asList(dataset) as parameter
            //asList() method will return the fixed-sized list of the array and takes datapaoints(array) as parameter

            int min = Collections.min(Arrays.asList(datapoints));
            //min() method will return the minimum element in the array and takes array.asList(dataset) as parameter
            //asList() method will return the fixed-sized list of the array and takes datapaoints(array) as parameter

            int cutoffs = (max - min) / nobins;//formula for the cutofs for the histogram
            int[] interval = new int[nobins + 1];//initialise an array for interval between the datasets on the number of passengers being collected
            int[] count = new int[nobins];//initialise an array for count

            for (int i = 0; i < interval.length; i++){
                interval[i] = min;
                min += cutoffs;

            }

            Loop:
            //for-each loop to transverse datapoints array
            for (int data : datapoints){
                //for loop to insert interval
                for (int k = 0; k < interval.length - 2; k++){
                    if (data < interval[k + 1]) {
                        count[k]++;//increment count
                        continue Loop;//end the current iteration of Loop and continue next iteration
                    }
                }
                count[nobins-1]++;
            }

            String line="";
            //for-each loop to transverse the interval array
            for (int cutoff: interval) {
                line += cutoff + " ";//increment line
            }
            System.out.println(line);

            String line1="";
            //for-each loop to transverse the interval array
            for (int nocount: count) {

                line1 += nocount + " ";//increment line1
            }
            System.out.println(line1);

            c++;//increment counter for while loop

        }
    }

}