package geometry.primitive;

import utilities.Constant;

/**
 * @author rotem ghidale
 * This class represents velocity- specifies the change in position on the `x` and the `y` axes.
 * */
public class Velocity {
    private double dx;
    private double dy;

    /** constructor.
     * @param dx specifies the change in position on the `x` axes.
     * @param dy specifies the change in position on the `y` axes.
     * */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**getter.
     * @return the change in position on the `x` axes.
     * */
    public double getDx() {
        return this.dx;
    }

    /**getter.
     * @return specifies the change in position on the `y` axes.
     * */
    public double getDy() {
        return this.dy;
    }

    /**setter.
     * @param dxVal the change in position on the `x` axes.
     * */
    public void setDx(double dxVal) {
        this.dx = dxVal;
    }

    /**setter.
     * @param dyVal the change in position on the `y` axes.
     * */
    public void setDy(double dyVal) {
        this.dy = dyVal;
    }

    /** applyToPoint- this function Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     * @param p the given point
     * @return the new point with the new velocity
     * */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**This is a static function which create the velocity according to the given angle and speed.
     * @param angle the given angle
     * @param speed the given speed
     * @return return a new velocity instance.
     * */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.round(Math.sin(angle * Constant.TO_RADIAN) * speed);
        double dy = -1 * Math.round(Math.cos(angle * Constant.TO_RADIAN) * speed);
        return new Velocity(dx, dy);

    }
}