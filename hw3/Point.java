/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        double result;

        if (this.y == that.y) {
            if (this.x == that.x)
                result = Double.NEGATIVE_INFINITY;
            else
                result = 0.0;
        } else if (this.x == that.x) {
            result = Double.POSITIVE_INFINITY;
        } else {
            result = (that.y - this.y) * 1.0 / (that.x - this.x);
        }

        return result;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        int result;

        if (this.y == that.y) {
            if (this.x < that.x)
                result = -1;
            else if (this.x == that.x)
                result = 0;
            else
                result = 1;
        } else if (this.y < that.y) {
            result = -1;
        } else {
            result = 1;
        }

        return result;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            int result;
            double slopeDifference = slopeTo(a) - slopeTo(b);

            if (slopeDifference < 0)
                result = -1;
            else if (slopeDifference > 0)
                result = 1;
            else
                result = 0;

            return result;
        }
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
