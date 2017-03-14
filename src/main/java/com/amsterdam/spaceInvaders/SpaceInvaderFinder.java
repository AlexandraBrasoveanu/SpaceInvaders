package com.amsterdam.spaceInvaders;

import com.amsterdam.Image;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.amsterdam.services.*;
import com.amsterdam.ImageElement;
import com.amsterdam.services.imageprocessing.ImageSeparator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexandra on 3/13/2017.
 */
public class SpaceInvaderFinder implements ObjectFinder {
    private List<int[][]> spaceInvaders;
    private FileProcessor processor;

    @Inject
    public SpaceInvaderFinder(@Named("SpaceInvaderPattern") List<int[][]> spaceInvaders, FileProcessor fileProcessor) {
        this.spaceInvaders = spaceInvaders;
        this.processor = fileProcessor;
    }

    @Override
    public Map<ImageElement, ImageElement> find(Image radarImage) {
        ImageSeparator imageSeparator = new ImageSeparator();
        List<ImageElement> blocks = imageSeparator.extractBlocks(radarImage); // traverse matrix and split into continuous blocks
        ImageComparer imageComparer = new ImageComparerImpl();
        Map<ImageElement, ImageElement> spaceInvaderMapLocation = new HashMap<>();
        for(int[][] originalObject : spaceInvaders) {
            ImageElement spaceInvader = new ImageElement(originalObject);
            for(ImageElement imageObject : blocks) {
                float percentage = imageComparer.getSimilarityPercentage(imageObject, spaceInvader);
                if(percentage > 50) {
                spaceInvaderMapLocation.put(spaceInvader, imageObject);
                }
            }
        }
        return spaceInvaderMapLocation;
    }
}
