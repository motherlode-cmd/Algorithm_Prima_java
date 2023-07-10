package com.example.demo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleReader extends Reader{
    private Scanner scan;

    public ConsoleReader() {
        scan = new Scanner(System.in);
    }

    @Override
    public void read() {
        String [] input = new String[3];
        int i = 0;
        try {
            if(scan.hasNext())
                setV1(scan.next());
            if(scan.hasNext())
                setV2(scan.next());
            if(scan.hasNextDouble())
                setW(scan.nextDouble());
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
    }

}
