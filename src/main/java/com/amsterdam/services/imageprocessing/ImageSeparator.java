package com.amsterdam.services.imageprocessing;

import com.amsterdam.Image;
import com.amsterdam.ImageElement;
import com.util.DisjointUnionSets;
import com.util.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alexandra on 3/11/2017.
 */

/*
* This class is used to hold a matrix composed of labels that are used to crop it
* into blocks of continuous areas.
* */
public class ImageSeparator implements MatrixBlockExtractor {
    private final static int MAX_SIZE = 1000*1000;
    private final static int MIN_OBJECT_SIZE = 5;

    private int[][] labels;

    @Override
    public List<ImageElement> extractBlocks(Image rawImage) {
        identifyBlocks(rawImage);
        return computeObjects();
    }

    /**
     * Receives an Image object as an input and identifies the continuous blocks of data
     * in the matrix. Blocks are identified starting with the top left node by visiting neighbors
     * and marking them accordingly. The algorithm passes the matrix two times. First one checks the neighbors
     * of the start point and assigns them a label. The label is saved in a disjoint union set eg.
     * each node is saved in a set and they are slowly joined according to the neighboring labels using
     * the minimum label as a representative
     * The second pass updates the resulting image matrix with the joined labels.
     * @param snapshot
     */
    private void identifyBlocks(Image snapshot) {
        DisjointUnionSets linked = new DisjointUnionSets();
        labels = new int[snapshot.getLength()][snapshot.getWidth()];
        int nextLabel = 1;

        for(int row = 0; row < snapshot.getLength(); row++) {
            for(int col = 0; col < snapshot.getWidth(); col++) {
                if(!snapshot.isFilledSpace(row,col)) {
                    continue;
                }
                List<Integer> neighbors = getNeighborsWithLabel(snapshot, row, col);
                if(neighbors.isEmpty()) {
                    linked.addNewElement(nextLabel);
                    labels[row][col] = nextLabel;
                    nextLabel++;
                } else {
                    int min = MAX_SIZE; // minimum label among all neighbors
                    for(Integer n : neighbors) {
                        if(min > n) {
                            min = n;
                        }
                    }
                    labels[row][col] = min;
                    for(int neighbor : neighbors) {
                        linked.union(neighbor, min);
                    }
                }
            }
        }
        for(int row = 0; row < snapshot.getLength(); row++) {
            for(int col = 0; col < snapshot.getWidth(); col++) {
                if(!snapshot.isFilledSpace(row, col)) {
                    continue;
                }
                labels[row][col] = linked.find(labels[row][col]);
            }
        }

    }

    /**
     * Checks all 8 neighbors of a point in the matrix and returns a list with
     * all their corresponding labels. Only neighbors that have already been visited
     * and have a label assigned are taken into consideration
     * @param snapshot original input image
     * @param row x-axis coordinate for the starting point
     * @param col y-axis coordinate for the starting point
     * @return a list of neighboring labels
     */
    private List<Integer> getNeighborsWithLabel(Image snapshot, int row, int col) {
        List<Integer> neighbors = new ArrayList<>();

        int rowNum[] = {-1, 0, 0, 1};
        int colNum[] = {0, -1, 1, 0};

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                int x = row + rowNum[i];
                int y = col + colNum[j];

                if (!snapshot.isFilledSpace(x, y)) {
                    continue;
                }

                if (labels[x][y] > 0) {
                    neighbors.add(labels[x][y]);
                }
            }
        }
        return neighbors;
    }

    /**
     * @param matrix
     * @return maximum value found in the input matrix
     */
    private int getMaxValInMatrix(int[][] matrix) {
        int max = 0;
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                if(max < matrix[i][j]) {
                    max = matrix[i][j];
                }
            }
        }
        return max;
    }

    /**
     * Returns a list of image element, cropped from the labeled matrix.
     * Cropping is done after checking all the points in the matrix that belong to
     * a certain area. The upper and lower boundary points are computed as being the
     * extreme points that will be a part of the block, the left most x and y and rightmost
     * x and y
     * @return list of image elements
     */
    private List<ImageElement> computeObjects() {
        ImageElement wholePicture = new ImageElement(labels);

        // list that will contain different lists containing
        // all the points included in an area
        List<List<Point>> areas = new ArrayList<>();

        // array that will contain the extracted image blocks
        List<ImageElement> elements = new ArrayList<>();

        // the maximum value in the matrix shows how many possible
        // blocks we have in the matrix
        int nbOfBlocks = getMaxValInMatrix(labels);

        for(int i = 0; i <= nbOfBlocks; i++) {
            areas.add(i, new ArrayList<Point>());
        }

        //add to the list of points all points int he matrix
        //with the indicated value
        for(int row = 0; row < labels.length; row++) {
            for(int col = 0; col < labels[row].length; col++) {
                    areas.get(labels[row][col]).add(new Point(row, col));
            }
        }

        for(int i = 0; i <= nbOfBlocks; i++) {
            int sizeOfElement = areas.get(i).size();
            if(sizeOfElement < MIN_OBJECT_SIZE) {
                continue;
            }
            // sort the list of point by the x axis to determine x
            // coordinates for the boundary points
            Collections.sort(areas.get(i), Point.X_ORDER);
            int lowestX = areas.get(i).get(0).getX();
            int highestX = areas.get(i).get(sizeOfElement - 1).getX();

            // sort the list of point by the y axis to determine y
            // coordinates for the boundary points
            Collections.sort(areas.get(i), Point.Y_ORDER);
            int lowestY = areas.get(i).get(0).getY();
            int highestY = areas.get(i).get(sizeOfElement - 1).getY();

            //the area that is cropped is delimited by the two points
            //with the coordinates previously computed
            elements.add(wholePicture.getElementByPoints(lowestX, lowestY, highestX, highestY));
        }

        return elements;
    }
}
