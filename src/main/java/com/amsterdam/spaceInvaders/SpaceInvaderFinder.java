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
    private final static int MINIMUM_MATCH_REQ = 50;
    private List<int[][]> spaceInvaders;

    @Inject
    public SpaceInvaderFinder(@Named("SpaceInvaderPattern") List<int[][]> spaceInvaders) {
        this.spaceInvaders = spaceInvaders;
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
                if(percentage > MINIMUM_MATCH_REQ) {
                    spaceInvaderMapLocation.put(imageObject, spaceInvader);
                }
            }
        }
        return spaceInvaderMapLocation;
    }
}
