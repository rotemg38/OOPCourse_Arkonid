package sprites;

import interfaces.Sprite;
import geometry.primitive.Velocity;
import geometry.primitive.Line;
import geometry.primitive.Point;
import collisions.CollisionInfo;
import collections.GameEnvironment;
import utilities.Constant;
import game.GameLevel;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * @author rotem ghidale
 * This class represents a "ball" which is a circle on the screen.
 * */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;

    /**This is a constructor1.
     * @param color the color of the ball.
     * @param center the center of the ball.
     * @param r the radius of the ball.
     * @param gameEnvironment hold all of the game related objects.
     * */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(Constant.ZERO, Constant.ZERO);
        this.gameEnvironment = gameEnvironment;
    }

    /**This is a constructor2.
     * @param color the color of the ball.
     * @param y the y value of the center of the ball.
     * @param x the x value of the center of the ball.
     * @param r the radius of the ball.
     * @param gameEnvironment hold all of the game related objects.
     * */
    public Ball(double x, double y, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this(new Point(x, y), r, color, gameEnvironment);
    }

    /**getter.
     * @return the x value of the center of the ball
     * */
    public int getX() {
        return (int) this.center.getX();
    }

    /**getter.
     * @return the y value of the center of the ball
     * */
    public int getY() {
        return (int) this.center.getY();
    }

    /**getter.
     * @return the radius of the ball
     * */
    public int getSize() {
        return this.radius;
    }

    /**getter.
     * @return the color of the ball
     * */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**getter.
     * @return the velocity of the ball
     * */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**setter- set the velocity of the ball.
     * @param v  the velocity of the ball
     * */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**setter- set the velocity of the ball.
     * @param dx  the velocity of the ball x axis.
     * @param dy  the velocity of the ball y axis.
     * */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**This function change the center of the ball according to the velocity of the ball,
     * while considering a possible collision according to this algorithm:
     * 1) compute the ball trajectory (the trajectory is "how the ball will move
     * without any obstacles" -- its a line starting at current location, and
     * ending where the velocity will take the ball if no collisions will occur).
     * 2) Check (using the game environment) if moving on this trajectory will hit anything.
     *     2.1) If no, then move the ball to the end of the trajectory.
     *     2.2) Otherwise (there is a hit):
     *     2.2.3) notify the hit object (using its hit() method) that a collision occurred.
     *     2.2.4) update the velocity to the new velocity returned by the hit() method.
     * */
    public void moveOneStep() {
        Line trajectory = new Line(center, this.velocity.applyToPoint(center));

        CollisionInfo collisionInfo = gameEnvironment.getClosestCollision(trajectory);

        if (collisionInfo == null) {
            this.center = this.getVelocity().applyToPoint(this.center);
        } else {
            //get the correct new velocity.
            this.velocity = collisionInfo.collisionObject().hit(this, collisionInfo.collisionPoint(), this.velocity);
            /*notice: we are not changing the ball center because:
            1) at the next move step the ball will move with the right velocity
            2) the ball might hit another collidable object, therefore we dont move the ball right now
             and on the next move one step the ball will check if there are more collisions point in his way.*/
        }
    }

    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
        //set a black border
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
}