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
public class ImageSeparator implements MatrixBlockExtractor, MatrixProcessor {
    private final int MAX_SIZE = 1000*1000;
    private final int MIN_OBJECT_SIZE = 5;

    private int[][] labels;

    @Override
    public List<ImageElement> extractBlocks(Image rawImage) {
        List<ImageElement> elements = computeObjects(identifyBlocks(rawImage).getData());
        return elements;
    }

    @Override
    public Image identifyBlocks(Image snapshot) {
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

        return new Image(labels);
    }

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

    private List<ImageElement> computeObjects(int[][] imageMatrix) {
        ImageElement wholePicture = new ImageElement(imageMatrix);
        List<List<Point>> areas = new ArrayList<>();
        List<ImageElement> elements = new ArrayList<>();
        areas.add(0, new ArrayList<Point>());
        int nbOfBlocks = getMaxValInMatrix(imageMatrix);
        for(int i = 1; i <= nbOfBlocks; i++) {
            int row = 0;
            areas.add(i, new ArrayList<Point>());
            while(row < imageMatrix.length) {
                int  col = 0;
                while(col < imageMatrix[row].length) {
                    if(imageMatrix[row][col] == i) {
                        areas.get(i).add(new Point(row, col));
                    }
                    col++;
                }
                row++;
            }
            int sizeOfElement = areas.get(i).size();
            if(sizeOfElement < MIN_OBJECT_SIZE) {
                continue;
            }
            Collections.sort(areas.get(i), Point.X_ORDER);
            int lowestX = areas.get(i).get(0).getX();
            int highestX = areas.get(i).get(sizeOfElement - 1).getX();
            Collections.sort(areas.get(i), Point.Y_ORDER);
            int lowestY = areas.get(i).get(0).getY();
            int highestY = areas.get(i).get(sizeOfElement - 1).getY();
            elements.add(wholePicture.getElementByPoints(lowestX, lowestY, highestX, highestY));
        }

        return elements;
    }
}
