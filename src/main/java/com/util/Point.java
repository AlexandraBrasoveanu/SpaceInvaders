package com.util;

import java.util.Comparator;

/**
 * Created by Alexandra on 3/11/2017.
 */
public class Point implements Comparable{
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Compares two points by x-coordinate.
     */
    public static final Comparator<Point> X_ORDER = new XOrder();

    /**
     * Compares two points by y-coordinate.
     */
    public static final Comparator<Point> Y_ORDER = new YOrder();



    @Override
    public int compareTo(Object o) {
        Point other = (Point) o;
        if (this.y < other.y) {
            return -1;
        }
        if (this.y > other.y) {
            return +1;
        }
        if (this.x < other.x) {
            return -1;
        }
        if (this.x > other.x) {
            return +1;
        }
        return 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // compare points according to their x-coordinate
    private static class XOrder implements Comparator<Point> {
        public int compare(Point p, Point q) {
            if (p.x < q.x) return -1;
            if (p.x > q.x) return +1;
            return 0;
        }
    }

    // compare points according to their y-coordinate
    private static class YOrder implements Comparator<Point> {
        public int compare(Point p, Point q) {
            if (p.y < q.y) return -1;
            if (p.y > q.y) return +1;
            return 0;
        }
    }


}

