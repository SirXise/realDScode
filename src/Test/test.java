package Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {
    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        int i = 1;
        while(in.hasNextLine()){
            String data = in.nextLine();
            String[] data1 = data.split("\\s");
            System.out.println("No."+ i + "length : " + data1.length);
            System.out.println(data1[0]+" "+data1[1]);
            i++;
        }
    }
}
