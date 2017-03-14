package com.amsterdam.services;

import com.amsterdam.Image;
import com.amsterdam.ImageElement;
import com.util.Point;

/**
 * Created by Alexandra on 3/12/2017.
 */
public class ImageComparerImpl implements ImageComparer {


    @Override
    public float getSimilarityPercentage(ImageElement croppedImg, ImageElement originalImage) {
        Image resultImage;
        int widthDiff = croppedImg.getWidth() - originalImage.getWidth(),
                lengthDiff = croppedImg.getLength() - originalImage.getLength(),
                correctMatrixSum = originalImage.getLength() * originalImage.getWidth();
        float bestMatch = 0f;
        Point bestPositionOff = null;
        if(widthDiff >= 0 && lengthDiff >=0) { // if source matrix is larger we adjust the starting point
            for(int i = 0; i <= widthDiff; i++) {
                for(int j = 0; j <= lengthDiff; j++) {
                    resultImage = croppedImg.computeUnionMatrix(i, j, originalImage); //
                    float currentMatch = getPercentage(resultImage.computeElementsSum(), correctMatrixSum) ;
                    bestMatch = currentMatch > bestMatch ? currentMatch : bestMatch; // remember the best match
                    bestPositionOff = new Point(i,j); // record the offset position
                }
            }
        }

        if(bestPositionOff != null) {
            croppedImg = croppedImg.getElementByOffset(bestPositionOff.getX(), bestPositionOff.getY());
        }

        return bestMatch;
    }

    private float getPercentage(int n, int total) {
        float proportion = ((float) n) / ((float) total);
        return proportion * 100;
    }
}
