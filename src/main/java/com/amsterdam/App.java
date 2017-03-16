package com.amsterdam;

import com.amsterdam.spaceInvaders.config.ObjectPatternConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.amsterdam.services.FileProcessor;
import com.amsterdam.services.ObjectFinder;
import com.amsterdam.services.imageprocessing.ImageFileProcessor;
import com.amsterdam.spaceInvaders.SpaceInvaderFinder;
import com.util.MatrixFormatter;
import com.util.Point;

import java.io.File;
import java.text.MessageFormat;
import java.util.Map;

public class App
{
    public static void main( String[] args )
    {
        Injector injector = Guice.createInjector(new ObjectPatternConfiguration());
        ObjectFinder imageObjectFinder = injector.getInstance(SpaceInvaderFinder.class);
        MatrixFormatter formatter = injector.getInstance(MatrixFormatter.class);

        //read and process input file containing the radar image
        File inFile = new File("radarPage.txt");
        FileProcessor processor  = injector.getInstance(ImageFileProcessor.class);
        Image imageMatrix = new Image(processor.fileTo2D(inFile));

        // find a list of the best matching image elements and the corresponding originals
        Map<ImageElement, ImageElement> detectionMapping = imageObjectFinder.find(imageMatrix);

        // pretty the location of the found match in the original image
        // and both the image element and the original pattern for reference
        for(ImageElement croppedImage : detectionMapping.keySet()) {
            Point startingPct = croppedImage.getStartingPoint();
            System.out.println(MessageFormat.format("Found space invader at point ({0},{1})", startingPct.getX(), startingPct.getY()));
            System.out.println("Original pattern: ");
            System.out.println(formatter.prettyFormat(detectionMapping.get(croppedImage).data));
            System.out.println("Matching pattern: ");
            System.out.println(formatter.prettyFormat(croppedImage.data));
        }
    }
}
