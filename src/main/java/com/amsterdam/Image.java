package com.amsterdam;

/**
 * Created by Alexandra on 3/12/2017.
 */
public class Image {
    protected int[][] data;

    public Image(int[][] data) {
        this.data = data;
    }

    public int computeElementsSum() {
        int sum = 0;
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[i].length; j++) {
                sum += data[i][j];
            }
        }
        return sum;
    }
    public int getWidth() {
        return data[0].length;
    }

    public int getHeight() {
        return data.length;
    }

    /**
     * Computes a result matrix after comparing each value and checking if they correspond.
     * Two values are considered a match if they are both 0 or both bigger than 0. The resulting
     * matrix will hold 1s for the cells that match, and 0 for the ones that do not.
     * The comparison is done from the specified row and column of the current object with the 0,0 position
     * of the other matrix.
     * @param row int that specifies at which row the comparison of the current object starts
     * @param col int that specified at which column the comparisor of the current object starts
     * @param otherImg the image object holding the matrix to compare against
     * @return wrapper around an int matrix containing 1s and 0s only
     */
    public Image computeUnionMatrix(int row, int col, Image otherImg) {
        int[][] dest = new int[Math.max(getHeight(), otherImg.getHeight())][Math.max(getWidth(), otherImg.getWidth())];
        for(int i = row, k = 0; i < Math.min(getHeight(), otherImg.getHeight()); i++, k++) {
            for(int j = col, l = 0; j < Math.min(getWidth(), otherImg.getWidth()); j++, l++) {
                if(otherImg.get(k,l) >= 1 && this.get(i,j) >= 1 || otherImg.get(k,l) == 0 && this.get(i,j) == 0) {
                    dest[k][l] = 1;
                }
            }
        }
        return new Image(dest);
    }

    public int get(int i, int j){
        return data[i][j];
    }

    public boolean isFilledSpace(int x, int y) {
        return isValidPoint(x, y) && data[x][y] == 1;
    }

    private boolean isValidPoint(int x, int y) {
        return x >=0 && x < getHeight() && y >=0 && y < getWidth();

    }

    public int[][] getData() {
        return data;
    }


}
