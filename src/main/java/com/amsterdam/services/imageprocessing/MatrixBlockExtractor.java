package com.amsterdam.services.imageprocessing;

import com.amsterdam.Image;
import com.amsterdam.ImageElement;

import java.util.List;

/**
 * Created by Alexandra on 3/11/2017.
 */
public interface MatrixBlockExtractor {
    List<ImageElement> extractBlocks(Image imageMatrix);

}
