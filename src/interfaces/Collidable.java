package interfaces;

import geometry.primitive.Velocity;
import geometry.primitive.Point;
import geometry.primitive.Rectangle;
import sprites.Ball;
import biuoop.DrawSurface;

/**@author rotem ghidale
 * This class represents a collidable object.*/
public interface Collidable {
    /**This function return the rectangle of a collidable object.
     * @return the "collision shape" of the object.*/
    Rectangle getCollisionRectangle();

    /**This function notify the object that we collided with it at collisionPoint with a given velocity.
     * @param hitter the hitter ball
     * @param collisionPoint the collision point
     * @param currentVelocity the velocity when we collided
     *@return the new velocity expected after the hit (based on the force the object inflicted on us).
     **/
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**This function draw the object to the given surface.
     * @param surface the draw surface
     **/
    void drawOn(DrawSurface surface);
}
