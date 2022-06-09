package collisions;

import geometry.primitive.Point;
import interfaces.Collidable;

/**@author rotem ghidale
 * This class represents information on a collision.*/
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /** constructor.
     * @param collisionPoint - the point at which the collision occurs.
     * @param collisionObject - the collidable object involved in the collision.
     * */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**getter.
     * @return the point at which the collision occurs.
     * */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**getter.
     * @return the collidable object involved in the collision.
     * */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}