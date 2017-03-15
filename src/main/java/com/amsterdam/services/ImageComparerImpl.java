package com.amsterdam.services;

import com.amsterdam.Image;
import com.amsterdam.ImageElement;
import com.util.Point;

/**
 * Created by Alexandra on 3/12/2017.
 */
public class ImageComparerImpl implements ImageComparer {


    /**
     * Compares the two image elements' matrices point by point. A new matrix is created,
     * with the sizes of the smaller matrix of the two. At comparison time, the result matrix will only
     * contain values for the values that match in both matrices. In order to match, they either both have to
     * be zero, or positive. The percentage of similarity will be the proportion of positive values from the
     * result matrix as opposed to the original pattern.
     * @param croppedImg matrix containg part of the image
     * @param originalImage matrix containing the original pattern that needs to be found
     * @return percentage of similarity between two matrices
     */
    @Override
    public float getSimilarityPercentage(ImageElement croppedImg, ImageElement originalImage) {
        Image unionMatrix;
        int widthDiff = croppedImg.getWidth() - originalImage.getWidth(),
                lengthDiff = croppedImg.getLength() - originalImage.getLength(),
                correctMatrixSum = originalImage.getLength() * originalImage.getWidth();
        float bestMatch = 0f;
        Point bestPositionOff = new Point(0,0);
        if(widthDiff >= 0 && lengthDiff >=0) { // if source matrix is larger we adjust the starting point
            for(int i = 0; i <= widthDiff; i++) {
                for(int j = 0; j <= lengthDiff; j++) {
                    unionMatrix = croppedImg.computeUnionMatrix(i, j, originalImage); //
                    float currentMatch = getPercentage(unionMatrix.computeElementsSum(), correctMatrixSum) ;
                    bestMatch = currentMatch > bestMatch ? currentMatch : bestMatch; // remember the best match
                    bestPositionOff.setX(i);
                    bestPositionOff.setY(j); // record the offset position
                }
            }
        }
//        croppedImg = croppedImg.getElementByOffset(bestPositionOff.getX(), bestPositionOff.getY());
        return bestMatch;
    }

    /**
     * Utility method to compute a percentage from total
     * @param n propotion to be computed
     * @param total the total number
     * @return percentage out of 100
     */
    private float getPercentage(int n, int total) {
        float proportion = ((float) n) / ((float) total);
        return proportion * 100;
    }
}
