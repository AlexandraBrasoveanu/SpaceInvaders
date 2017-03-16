package com.amsterdam.services;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Alexandra on 3/10/2017.
 */
public interface FileProcessor {
    int[][] fileTo2D(File inFile) throws FileNotFoundException;
}
