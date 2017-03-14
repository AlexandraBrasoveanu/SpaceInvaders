package com.amsterdam.services.imageprocessing;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.amsterdam.services.FileProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Alexandra on 3/10/2017.
 */
public class ImageFileProcessor implements FileProcessor {
    private String foregroundChar;

    @Inject
    public ImageFileProcessor(@Named("Foreground") String foregroundChar) {
        this.foregroundChar = foregroundChar;
    }

    public int[][] fileTo2D(File inFile) {
        int[][] matrix;
        try {
            Scanner in = new Scanner(inFile);
            char[] length = in.nextLine().trim().toCharArray();
            int intLength = length.length;
            int lineCount = 1;
            while(in.hasNextLine()) {
                lineCount++;
                in.nextLine();
            }
            in.close();
            in = new Scanner(inFile);
            matrix = new int[lineCount][intLength];
            lineCount = 0;
            while (in.hasNextLine()) {
                char[] currentLine = in.nextLine().trim().toCharArray();
                matrix[lineCount++] = simplifyArray(currentLine);
            }
            in.close();
            return matrix;
        } catch (FileNotFoundException e) {
            System.out.println("Problem reading the original image");
        }
        return new int[0][];
    }

    private int[] simplifyArray(char[] line) {
        int[] binaryArray = new int[line.length];
        for(int i = 0; i < line.length; i++) {
            binaryArray[i] = line[i] == foregroundChar.charAt(0) ? 1 : 0;
        }
        return binaryArray;
    }
}
