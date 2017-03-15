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

    /**
     * Reads a file containing an image and saved the image as a matrix of ints.
     * The elements that are designated as being part of the foreground are marked
     * with a value of 1 in the matrix, for ease of calculation
     * @param inFile File object containing the input image
     * @return result matrix containing 1s and 0s only
     */
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

    /**
     *
     * Replaces the foreground characters will 1s. The rest will remain zero
     * @param line character array containing one line from the input image
     * @return int array containing the processed line
     */
    private int[] simplifyArray(char[] line) {
        int[] binaryArray = new int[line.length];
        for(int i = 0; i < line.length; i++) {
            binaryArray[i] = line[i] == foregroundChar.charAt(0) ? 1 : 0;
        }
        return binaryArray;
    }
}
