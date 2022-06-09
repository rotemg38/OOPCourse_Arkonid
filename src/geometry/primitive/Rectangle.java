package geometry.primitive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**@author rotem ghidale
 * This class represents a rectangle object.*/
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**This function creates a new rectangle with location and width/height.
     * @param width the rectangle width.
     * @param height the rectangle height.
     * @param upperLeft the rectangle upper left point.
     * */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**This function creates the sides of the rectangle as Map of lines and their names.
     * @return the sides of the rectangle.
     * */
    public java.util.Map<String, Line> getRectangleSides() {
        Point upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        Point downLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        Point downRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);

        Line upperSide = new Line(upperLeft, upperRight);
        Line downSide = new Line(downLeft, downRight);
        Line leftSide = new Line(upperLeft, downLeft);
        Line rightSide = new Line(upperRight, downRight);

        Map<String, Line> rectangleSides = new HashMap<String, Line>();
        rectangleSides.put("up", upperSide);
        rectangleSides.put("down", downSide);
        rectangleSides.put("left", leftSide);
        rectangleSides.put("right", rightSide);

        return rectangleSides;
    }

    /**This function calculates the intersection points of the line and the rectangle.
     * @param line the line
     * @return return a (possibly empty) List of intersection points with the specified line.
     * */
    public java.util.List<Point> intersectionPoints(Line line) {
        Map<String, Line> rectangleSides = getRectangleSides();

        Point intersectionOne = line.intersectionWith(rectangleSides.get("up"));
        Point intersectionTwo = line.intersectionWith(rectangleSides.get("down"));
        Point intersectionThree = line.intersectionWith(rectangleSides.get("left"));
        Point intersectionFour = line.intersectionWith(rectangleSides.get("right"));

        java.util.List<Point> points = new ArrayList<>();
        if (intersectionOne != null) {
            points.add(intersectionOne);
        }
        if (intersectionTwo != null) {
            points.add(intersectionTwo);
        }
        if (intersectionThree != null) {
            points.add(intersectionThree);
        }
        if (intersectionFour != null) {
            points.add(intersectionFour);
        }

        //remove duplications.
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if (points.get(i).equals(points.get(j))) {
                    points.remove(j);
                }
            }
        }
        return points;
    }

    /**This function check if the received point is in the rectangle.
     * @param point the point
     * @return true if the point in the rectangle area, otherwise false.
     * */
    public boolean isInRectangle(Point point) {
        return point.getX() >= this.upperLeft.getX() && point.getX() <= this.upperLeft.getX() + this.getWidth()
                && point.getY() >= this.upperLeft.getY() && point.getY() <= this.upperLeft.getY() + this.getHeight();
    }

    /**width method.
     * @return the width of the rectangle.
     * */
    public double getWidth() {
        return this.width;
    }

    /**height method.
     * @return the height of the rectangle.
     * */
    public double getHeight() {
        return this.height;
    }

    /**getter method.
     * @return the upper-left point of the rectangle.
     * */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**setter method.
     * @param point the upper-left point of the rectangle.
     * */
    public void setUpperLeft(Point point) {
        this.upperLeft = point;
    }
}