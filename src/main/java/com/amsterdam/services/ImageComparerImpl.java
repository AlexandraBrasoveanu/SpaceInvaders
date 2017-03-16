package com.amsterdam.services;

import com.amsterdam.Image;
import com.amsterdam.ImageElement;

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
                heightDiff = croppedImg.getHeight() - originalImage.getHeight();
        float bestMatch = 0f;
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        for(int i = 0; i <= Math.abs(widthDiff); i++) {
            for(int j = 0; j <= Math.abs(heightDiff); j++) {
                ImageElement croppedOriginalImage = originalImage;
                // if cropped image is smaller than the original image and it has at least an edge it is a valid candidate
                if(croppedImg.isEdge() && (widthDiff < 0 && heightDiff < 0)) {
                    croppedOriginalImage = croppedOriginalImage.getElement(i, j, originalHeight - i, originalWidth - j);
                }
                int correctMatrixSum = croppedOriginalImage.getHeight() * croppedOriginalImage.getWidth();
                unionMatrix = croppedImg.computeUnionMatrix(i, j, croppedOriginalImage); //
                float currentMatch = getPercentage(unionMatrix.computeElementsSum(), correctMatrixSum) ;
                bestMatch = currentMatch > bestMatch ? currentMatch : bestMatch; // remember the best match
            }
        }
//        croppedImg = croppedImg.getElementByOffset(bestPositionOff.getX(), bestPositionOff.getY());
        return bestMatch;
    }

    /**
     * Utility method to compute a percentage from total
     * @param n proportion to be computed
     * @param total the total number
     * @return percentage out of 100
     */
    private float getPercentage(int n, int total) {
        float proportion = ((float) n) / ((float) total);
        return proportion * 100;
    }

}
