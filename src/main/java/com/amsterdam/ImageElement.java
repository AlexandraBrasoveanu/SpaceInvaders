package com.amsterdam;

import com.util.Point;

/**
 * Created by Alexandra on 3/11/2017.
 */
public class ImageElement extends Image {
    private int x, y, columns, rows;
    private Edge edge;
    public enum Edge {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public ImageElement(int[][] matrix) {
        super(matrix);
        this.x = 0;
        this.y = 0;
        this.columns = matrix.length;
        this.rows = matrix.length;
        edge = null;
    }

    public ImageElement(int[][] matrix, int x, int y, int columns, int rows) {
        super(matrix);
        this.x = x;
        this.y = y;
        this.columns = columns;
        this.rows = rows;

    }

    /**
     * Crops the original matrix into a submatrix from the specified point
     * keeping in mind the width and the length of the new matrix.
     * The original cropping point is saved
     * @param x x-axis coordinate for the starting point
     * @param y y-axis coordinaate for the starting point
     * @param rows length of the submatrix that will be created
     * @param columns width of the submatrix that will be created
     * @return newly cropped matrix
     */
    public ImageElement getElement(int x, int y, int rows, int columns) {
        int[][] subMatrix = new int[rows][columns];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                subMatrix[i][j] = data[x + i][y + j];
            }
        }
        return new ImageElement(subMatrix, this.x + x , this.y + y, columns, rows);
    }

    /**
     * Crops the original matrix into a submatrix from the specified point
     * including the entire block till another specified point.
     * The original cropping point is saved
     * @param x1 x-axis coordinate for the upper cropping point
     * @param y1 y-axis coordinate for the upper cropping point
     * @param x2 x-axis coordinate for the lower cropping point
     * @param y2 x-axis coordinate for the lower cropping point
     * @return return cropped submatrix
     */
    public ImageElement getElementByPoints(int x1, int y1, int x2, int y2) {
        int rows = x2 - x1,
            columns = y2 - y1;
        return this.getElement(x1, y1, rows, columns);
    }

    public ImageElement getElementByOffset(int xOff, int yOff) {
        return getElement(this.x + xOff, this.y + yOff, this.rows - xOff, this.columns - yOff);
    }

    public Point getStartingPoint() {
        return new Point(x,y);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sb.append(data[i][j]);
                if (data[i][j] < 10) { // TODO remove this nonsense
                    sb.append("  ");
                } else if(data[i][j] < 100){
                    sb.append(" ");
                }
                sb.append(" ");

            }
            sb.append("\n");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

}
