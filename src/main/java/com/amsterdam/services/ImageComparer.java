package com.amsterdam.services;

import com.amsterdam.ImageElement;

/**
 * Created by Alexandra on 3/12/2017.
 */
public interface ImageComparer {
    float getSimilarityPercentage(ImageElement sourceImage, ImageElement imageToCheck);
}
