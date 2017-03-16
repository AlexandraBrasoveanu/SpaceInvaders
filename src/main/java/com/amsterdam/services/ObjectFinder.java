package com.amsterdam.services;

import com.amsterdam.Image;
import com.amsterdam.ImageElement;

import java.util.Map;

/**
 * Created by Alexandra on 3/13/2017.
 */
public interface ObjectFinder {
    Map<ImageElement, ImageElement> find(Image image);
}
