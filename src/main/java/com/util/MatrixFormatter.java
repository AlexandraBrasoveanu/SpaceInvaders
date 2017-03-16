package com.util;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Created by Arwen on 3/16/2017.
 */
public class MatrixFormatter {
    private String background;
    private String foreground;

    @Inject
    public MatrixFormatter(@Named("Background") String background, @Named("Foreground") String foreground) {
        this.background = background;
        this.foreground = foreground;
    }

    public String prettyFormat(int[][] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j] > 0) {
                    sb.append(foreground);
                } else {
                    sb.append(background);
                }
            }
            sb.append("\n");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
