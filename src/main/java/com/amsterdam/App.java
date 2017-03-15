package com.amsterdam;

import com.amsterdam.spaceInvaders.config.ObjectPatternConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.amsterdam.services.FileProcessor;
import com.amsterdam.services.ObjectFinder;
import com.amsterdam.services.imageprocessing.ImageFileProcessor;
import com.amsterdam.spaceInvaders.SpaceInvaderFinder;
import com.util.Point;

import java.io.File;
import java.text.MessageFormat;

/**
 * Hello world!
 *
 */
public class App
{
     private static void displayMatrix(int[][] newMatrix) {
        for(int i=0; i<newMatrix.length; i++) {
            for(int j=0; j < newMatrix[0].length; j++) {
                    System.out.print(newMatrix[i][j]) ;
            }
                System.out.println();
        }
    }

    private static void displayMatrix(char[][] newMatrix) {
        for(int i=0; i<newMatrix.length; i++) {
            for(int j=0; j<newMatrix[0].length; j++) {
                    System.out.print(newMatrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void main( String[] args )
    {
        Injector injector = Guice.createInjector(new ObjectPatternConfiguration());
        ObjectFinder imageObjectFinder = injector.getInstance(SpaceInvaderFinder.class);
        File inFile = new File("C:\\radarPage.txt");
        FileProcessor processor  = injector.getInstance(ImageFileProcessor.class);
        int[][] oldMatrix = processor.fileTo2D(inFile);
        for(ImageElement croppedImage : imageObjectFinder.find(new Image(oldMatrix)).keySet()) {
            Point startingPct = croppedImage.getStartingPoint();
            System.out.println(MessageFormat.format("Found space invader at point ({0},{1})", startingPct.getX(), startingPct.getY()));
        }
    }
}
