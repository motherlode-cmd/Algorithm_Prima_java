package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Vector;
import java.io.BufferedReader;
public class FileReader extends Reader{
    private Vector <String> txt = new Vector<String >();
    private int counter = 0;

    private Vector <String> strings = new Vector<String >();

    @Override
    public void read() {
        try {
            setV1(txt.elementAt(counter * 3));
            setV2(txt.elementAt(counter * 3 + 1));
            setW(Double.parseDouble(txt.elementAt(counter * 3 + 2)));
            counter++;
        } catch (NumberFormatException e) {
            setW(0);
            setV2("");
            setV1("");
            //e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            setW(0);
            setV2("");
            setV1("");
        }
    }

    public FileReader(File file) {
        int i = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                txt.add(word);
                if(i % 3 != 2 && !strings.contains(word))
                    strings.add(word);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getN() {
        return txt.size()/3;
    }
}
