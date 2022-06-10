package geometry.primitive;

import utilities.Constant;

/**@author rotem ghidale
 * This class represents line equation as: ax + by = c.*/
public class Equation {
    /**represents the types of equation parameters.*/
    private enum Param { A, B, C };
    private double a;
    private double b;
    private double c;

    /**This is a constructor which finds the line equation.
     *  line equation represents as: a1x + b1y = c1
     *  known line equation: y − y1 = m(x − x1) when m=(y2-y1)/(x2-x1) ,(x1,y1) = point on the line
     *  y-y1 = (y2-y1)/(x2-x1) * (x-x1) => (y1-y2) * x + (x2-x1) * y + (x1-x2)*y1 + (y2-y1)*x1 = 0
     *  thus, a1= (y1-y2), b1= (x2-x1), c1= (x1-x2)*y1 + (y2-y1)*x1.
     * @param start point of segment
     * @param end point of segment
    **/
    public Equation(Point start, Point end) {
        this.a = end.getY() - start.getY();
        this.b = start.getX() - end.getX();
        this.c = this.a * (start.getX()) + this.b * (start.getY());
    }

    /**This function receive the name of parameter and return the relevant parameter.
     * @param paramType the parameter type of the equation: a\b\c.
     * @return return the matching parameter.
     * */
    public double getByParam(Param paramType) {
        switch (paramType) {
            case A:
                return this.a;
            case B:
                return this.b;
            case C:
                return this.c;
            default:
                return Double.NaN;
        }
    }

    /**This is getter function.
    @return the x parameter = a */
    public double getA() {
        return a;
    }

    /**This is getter function.
     @return the y parameter = b */
    public double getB() {
        return b;
    }

    /**This is getter function.
     @return the c */
    public double getC() {
        return c;
    }

    /**This function helps the equals function to determine whether the two equations are equals.
     * @param other - represent the other equation.
     * @param paramType - the parameter type of the equation: a\b.
     * @return 1 if the equations passed the test 0 if not and -1 if yet to be determine.
     * */
    private int equalsHelper(Equation other, Param paramType) {
        double toCheck = this.getByParam(paramType);
        double otherToCheck = other.getByParam(paramType);
        Param secondToCheck = paramType == Param.B ? Param.A : Param.B;
        if (toCheck == otherToCheck) {
            //if both like ax = c \ by = c
            if (toCheck == 0) {
                if (this.getByParam(secondToCheck) != 0 && this.c != 0) {
                    return other.getByParam(secondToCheck) / this.getByParam(secondToCheck) == other.getC() / this.c
                            && other.getC() / this.c != 0 ? Constant.ONE : Constant.ZERO;
                }
                /*means now or c==0 or a==0 or both \ b == 0
                if both equation like (1)ax = 0 \ by = 0 or (2)0x = 0 \ 0y = 0, return true.*/
                if (this.c == 0 && other.getC() == 0 && this.getByParam(secondToCheck) != 0
                        && other.getByParam(secondToCheck) != 0) {
                    return Constant.ONE;
                }
                return this.c == other.getC() && this.getByParam(secondToCheck) == other.getByParam(secondToCheck)
                        ? Constant.ONE : Constant.ZERO;
            }
        }
        return Constant.MINUS_ONE;
    }

    /**equals method.
     * @param other - represent the other equation.
     * @return true is the equations are equal, false otherwise.
     * */
    public boolean equals(Equation other) {
        if (other == null) {
            return false;
        }
        if (this.a != 0 && this.b != 0 && this.c != 0) {
            return other.getA() / this.a == other.getB() / this.b && other.getA() / this.a == other.getC() / this.c
                    && other.getC() / this.c == other.getB() / this.b && other.getB() / this.b != 0;
        }
        if (this.c == other.getC()) {
            //if both like ax + by = 0
            if (this.c == 0) {
                if (this.a != 0 && this.b != 0) {
                    return other.getA() / this.a == other.getB() / this.b && other.getB() / this.b != 0;
                }
                /*means now or b==0 or a==0 or both
                if both equation like (1)ax = 0 or (2)by = 0, return true.*/
                if (this.a != 0 && other.getA() != 0 && this.b == 0 && other.getB() == 0) {
                    return true;
                }
                //check for case (2)
                if (this.a == 0 && other.getA() == 0 && this.b != 0 && other.getB() != 0) {
                    return true;
                }
            }
            //if c!=0 so a==0 or b==0 and any way in order that the equation will e equals, this must happen:
            return this.b == other.getB() && this.a == other.getA();
        }

        int result = equalsHelper(other, Param.B);
        if (result != Constant.MINUS_ONE) {
            return result == Constant.ONE;
        }
        result = equalsHelper(other, Param.A);
        if (result != Constant.MINUS_ONE) {
            return result == Constant.ONE;
        }
        return false;
    }

    /**This function tells if this is equation or just a point.
     * equation must be with a not 0 or b not 0.
     * @return true for point, false for equation
     * */
    public boolean isPoint() {
        if (a == 0 && b == 0) {
            return true;
        }
        return false;
    }

    /**This function check if the given point is on the line equation.
     * @param point the point we are checking.
     * @return true - the point is on the equation, otherwise false.
     * */
    public boolean isOnEquation(Point point) {
        return a * point.getX() + b * point.getY() == c;
    }
}
