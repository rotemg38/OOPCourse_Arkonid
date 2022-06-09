package geometry.primitive;

import utilities.Constant;

/**
 * @author rotem ghidale
 *This class represents a geometryPrimitive.GeometryPrimitive.Point with x and y values.
 * */
public class Point {
    private double x;
    private double y;
    /** constructor.
     * @param x - x value of the creating point.
     * @param y - y value of the creating point.
     * */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**distance method.
     * @param other - represents other point.
     * @return the distance of this point to the other point, if the given point is null return -1.
     * */
    public double distance(Point other) {
        if (other != null) {
            double powX = Math.pow(this.x - other.getX(), Constant.TWO);
            double powY = Math.pow(this.y - other.getY(), Constant.TWO);
            return Math.sqrt(powX + powY);
        }
        return Constant.MINUS_ONE;
    }

    /**equals method.
     * @param other - represents other point.
     * @return true is the points are equal, false otherwise.
     * */
    public boolean equals(Point other) {
        // this checks if a-b is closer to 0 than epsilon, if true, then a probably equals b.
        if (other != null && Math.abs(this.x - other.getX()) < Constant.EPSILON
                && Math.abs(this.y - other.getY()) < Constant.EPSILON) {
            return true;
        }
        return false;
    }

    /**getter.
     * @return the x value of this point.
     * */
    public double getX() {
        return this.x;
    }

    /**getter.
     * @return the y value of this point.
     * */
    public double getY() {
        return this.y;
    }
}