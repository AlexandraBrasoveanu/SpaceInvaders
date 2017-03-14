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

    public int getLength() {
        return data.length;
    }

    public Image computeUnionMatrix(int row, int col, Image toCompare) {
        int[][] dest = new int[Math.max(getLength(), toCompare.getLength())][Math.max(getWidth(), toCompare.getWidth())];
        for(int i = row, k = 0; i < Math.min(getLength(), toCompare.getLength()); i++, k++) {
            for(int j = col, l = 0; j < Math.min(getWidth(), toCompare.getWidth()); j++, l++) {
                if(toCompare.get(k,l) >= 1 && this.get(i,j) >= 1 || toCompare.get(k,l) == 0 && this.get(i,j) == 0) {
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
        return x >=0 && x < getLength() && y >=0 && y < getWidth();

    }

    public int[][] getData() {
        return data;
    }

}
