package geometry.primitive;

import java.util.List;
import utilities.Constant;

/**
 * @author rotem ghidale
 *This class represents a geometryPrimitive.GeometryPrimitive.Line via two points- start point and end point.
 * */
public class Line {
    private Point start;
    private Point end;
    private Equation equation;


    /** constructor 1.
     * @param start - represents the start point of the line.
     * @param end - represents the end point of the line.
     * */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.equation = new Equation(start, end);
    }

    /** constructor 2.
     * @param x1 - x value of start point.
     * @param y1 - y value of start point.
     * @param x2 - x value of end point.
     * @param y2 - y value of end point.
     * */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        this.equation = new Equation(start, end);

    }

    /**length method.
     * @return the length of the line.
     * */
    public double length() {
        return this.end.distance(this.start);
    }

    /**middle method.
     * @return the middle point of the line.
     * */
    public Point middle() {
        double middleX = (this.end.getX() + this.start.getX()) / 2;
        double middleY = (this.end.getY() + this.start.getY()) / 2;
        return new Point(middleX, middleY);
    }

    /**getter.
     * @return the start point of the line.
     * */
    public Point start() {
        return this.start;
    }

    /**getter.
     * @return the end point of the line.
     * */
    public Point end() {
        return this.end;
    }

    /**getter.
     * @return the equation of the line.
     * */
    public Equation getEquation() {
        return this.equation;
    }

    /**isIntersecting method.
     * @param other - represent the other line.
     * @return true if the lines intersect, false otherwise.
     * */
    public boolean isIntersecting(Line other) {
        if (intersectionWith(other) != null) {
            return true;
        }
        return false;
    }

    /**This function handles the case which two segments are on the same line equation.
     * The function checks if the two suspected intersection points are the same.if so, the function checks if this
     * is the only suspected point, meaning, check if one of the other points is on the one of the other segments.
     * for example: s1(=s2)--->p1---->p2 the suspected points are s1 and s2
     * and p1 is on the segment (s2, p2) in that case null will be returned.
     * @param oneSuspectedIntersection the suspected intersection point
     * @param twoSuspectedIntersection the other suspected intersection point on the other segment
     * @param p1 other edge point in one segment
     * @param p2 other edge point in other segment
     * @param other the other line
     * @return return (if there is) the intersection point, else return null.
     * */
    private Point intersectionSameEquation(Point oneSuspectedIntersection, Point twoSuspectedIntersection,
                                           Point p1, Point p2, Line other) {
        if (oneSuspectedIntersection.equals(twoSuspectedIntersection) && !inRangeSegments(other, p1.getX(), p1.getY())
                && !inRangeSegments(other, p2.getX(), p2.getY())) {
            return new Point(oneSuspectedIntersection.getX(), oneSuspectedIntersection.getY());
        }
        return null;
    }
    /**This functions handles the case which one of the lines is a point.
     * @param suspectedLine - the suspected line to be line.
     * @param suspectedPoint - the suspected line to be point.
     * @param other - the other line, need in order to check if the point is in the boundaries of this&other segments.
     * @return 1 - the suspected point is really point and the other is line and the point is on the line.
     *         0 - the suspected point is really point and the other is line but the point is not on the line.
     *        -1 - the first "if" failed- means the result of intersection is meanwhile unknown.
     * */
    private int intersectionOnePoint(Line suspectedPoint, Line suspectedLine, Line other) {
        //if one of the lines is a point
        if (!suspectedLine.equation.isPoint() && suspectedPoint.equation.isPoint()) {
            //we check if the point is on the line equation.
            if (suspectedLine.equation.isOnEquation(suspectedPoint.start)) {
                //check if intersection point in segments.
                if (inRangeSegments(other, suspectedPoint.start.getX(), suspectedPoint.start.getY())) {
                    return Constant.ONE;
                }
            }
            return Constant.ZERO;
        }
        return Constant.MINUS_ONE;
    }

    /**intersectionWith method.
     * explain of the calculation:
     * find intersection point:
     *    this line: a1x + b1y = c1 => multiply by b2: a1b2x + b1b2y = c1b2
     *    other line: a2x + b2y = c2 => multiply by b1: a2b1x + b2b1y = c2b1
     *    => (a1b2 – a2b1) x = c1b2 – c2b1 => found the intersection x value
     *
     *    this line: a1x + b1y = c1 => multiply by a2: a1a2x + b1a2y = c1a2
     *    other line: a2x + b2y = c2 => multiply by a1: a2a1x + b2a1y = c2a1
     *    => (b1a2 – b2a1) y = c1a2 – c2a1 => found the intersection y value
     *
     *    notice! the divider of x (which is: a1b2 – a2b1) equals -1*(divider of y = b1a2 – b2a1)
     * @param other - represent the other line.
     * @return the intersection point if the lines intersect, and null otherwise.
     * */
    public Point intersectionWith(Line other) {
        if (other == null) {
            return null;
        }

        //if the lines are points.
        if (this.equals(other) && this.equation.isPoint()) {
            return new Point(this.start.getX(), this.start.getY());
        }

        //if the lines equations are the same.
        if (this.equation.equals(other.equation)) {
            Point intersection = intersectionSameEquation(this.start, other.end, other.start, this.end, other);
            if (intersection != null) {
               return intersection;
            }
            intersection = intersectionSameEquation(this.start, other.start, other.end, this.end, other);
            if (intersection != null) {
                return intersection;
            }
            intersection = intersectionSameEquation(this.end, other.start, other.end, this.start, other);
            if (intersection != null) {
                return intersection;
            }
            intersection = intersectionSameEquation(this.end, other.end, other.start, this.start, other);
            if (intersection != null) {
                return intersection;
            }
            return null;
        }

        //if one of the lines is a point
        int result = intersectionOnePoint(other, this, other);
        if (result != Constant.MINUS_ONE) {
            return result == 0 ? null : new Point(other.start.getX(), other.start.getY());
        }
        result = intersectionOnePoint(this, other, other);
        if (result != Constant.MINUS_ONE) {
            return result == 0 ? null : new Point(this.start.getX(), this.start.getY());
        }

        //"this" line equation represents as a1x + b1y = c1.
        double a1 = this.equation.getA();
        double b1 = this.equation.getB();
        double c1 = this.equation.getC();

        //"other" line equation represents as a2x + b2y = c2.
        double a2 = other.equation.getA();
        double b2 = other.equation.getB();
        double c2 = other.equation.getC();

        double divider = a1 * b2 - a2 * b1;

        if (divider != 0) {
            double intersectedX = (c1 * b2 - c2 * b1) / divider;
            double intersectedY = (-1) * ((c1 * a2 - c2 * a1) / divider);

            //check if intersection point in segments.
            if (inRangeSegments(other, intersectedX, intersectedY)) {
                return new Point(intersectedX, intersectedY);
            }
            return null;
        }
        return null;
    }

    /** inRangeSegments method.
     * @param other - represent the other line.
     * @param intersectedX - x value of intersection point.
     * @param intersectedY - y value of intersection point.
     * @return true if the given intersection point is in the range of the two lines, otherwise false.
     * */
    private boolean inRangeSegments(Line other, double intersectedX, double intersectedY) {
        double minXLineThis = Math.min(this.start.getX(), this.end.getX());
        double minXLineOther = Math.min(other.start.getX(), other.end.getX());

        double manXLineThis = Math.max(this.start.getX(), this.end.getX());
        double manXLineOther = Math.max(other.start.getX(), other.end.getX());

        double minYLineThis = Math.min(this.start.getY(), this.end.getY());
        double minYLineOther = Math.min(other.start.getY(), other.end.getY());

        double manYLineThis = Math.max(this.start.getY(), this.end.getY());
        double manYLineOther = Math.max(other.start.getY(), other.end.getY());
        //check if intersection point in segments.
        if (!(intersectedX >= minXLineThis
                && intersectedX >= minXLineOther
                && intersectedX <= manXLineThis
                && intersectedX <= manXLineOther
                && intersectedY >= minYLineThis
                && intersectedY >= minYLineOther
                && intersectedY <= manYLineThis
                && intersectedY <= manYLineOther
        )) {
            return false;
        }
        return true;
    }

    /**equals method.
     * @param other - represent the other line.
     * @return true is the lines are equal, false otherwise.
     * */
    public boolean equals(Line other) {
        if (other != null && (this.start.equals(other.start) && this.end.equals(other.end))
            || (this.start.equals(other.end) && this.end.equals(other.start))) {
            return true;
        }
        return false;
    }

    /**This function return the closest point to the start of the line.
     * @param rect the rectangle.
     * @return If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     * */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> points = rect.intersectionPoints(this);
        if (points.size() == 0) {
            return null;
        }
        //holds the index of the closest point.
        int minIndex = -1;
        //holds the distance of the closest point from the start.
        double minDis = -1;
        for (int i = 0; i < points.size(); i++) {

            double newDis = this.start.distance(points.get(i));
            if (newDis < minDis || minDis == -1) {
                minDis = newDis;
                minIndex = i;
            }
        }

        return points.get(minIndex);
    }
}