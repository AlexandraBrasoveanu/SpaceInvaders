package com.amsterdam;


import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Arwen on 3/16/2017.
 */
public class TestImage {

    @Test
    public void testComputeElementsSum() {
        // Create matrix with values
        // [ 0, 1, 2
        //   1, 2, 3
        //   2, 3, 4]
        int[][] testMatrix = new int[3][3];
        for (int x = 0; x < testMatrix.length; x++)
        {
            for (int y = 0; y < testMatrix[0].length; y++)
            {
                testMatrix[x][y] = x + y;
            }
        }
        Image testimage = new Image(testMatrix);
        int result = testimage.computeElementsSum();
        assertEquals(18, result);
    }


}
