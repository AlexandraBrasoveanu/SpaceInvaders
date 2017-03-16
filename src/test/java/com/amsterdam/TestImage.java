package com.amsterdam;


import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Alexandra on 3/16/2017.
 */
public class TestImage {
    private Image testimage;

    @Before
    public void setup() {
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
        testimage = new Image(testMatrix);
    }

    @Test
    public void testComputeElementsSum() {
        int result = testimage.computeElementsSum();
        assertEquals(18, result);
    }

    @Test
    public void testIsValidPointOutOfBoundary() {
        assertEquals(false, testimage.isFilledSpace(5,0));
    }

    @Test
    public void testIsValidPointFalse() {
        assertEquals(false, testimage.isFilledSpace(0,0));
    }

    @Test
    public void testIsValidPointTrue() {
        assertEquals(true, testimage.isFilledSpace(1,2));
    }
}
