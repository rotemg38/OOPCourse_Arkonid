package collections;

import geometry.primitive.Line;
import geometry.primitive.Point;
import geometry.primitive.Rectangle;
import interfaces.Collidable;
import collisions.CollisionInfo;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**@author rotem ghidale
 * This class represents the game environmient.
 * */
public class GameEnvironment {
    private List<Collidable> collidablesObjects;

    /**A Constructor.
     * creates an game environment object.
     * */
    public GameEnvironment() {
        this.collidablesObjects = new LinkedList<Collidable>();
    }

    /**This function add the given colloidable object to the list in the game environment.
     * @param c the collidable object
     * */
    public void addCollidable(Collidable c) {
        collidablesObjects.add(c);
    }

    /**This function remove the given colloidable object from the list in the game environment.
     * @param c a collidable object.*/
    public void removeCollidable(Collidable c) {
        this.collidablesObjects.remove(c);
    }

    /**This function Assume an object moving from line.start() to line.end().
     * and check if this object will not collide with any of the collidables in this collection.
     * @param trajectory the object path line.
     * @return If this this object will not collide with any of the collidables in this collection, return null.
     * Else, return the information about the closest collision that is going to occur.
     * */
    public CollisionInfo getClosestCollision(Line trajectory) {
        java.util.Map<Collidable, Point> allIntersectionsClosestPoints = new HashMap<>();
        //make a copy to prevent a multi access to the list, iterate while deleting.
        List<Collidable> collidables = new LinkedList<Collidable>(this.collidablesObjects);

        for (Collidable collidableObj : collidables) {
            Rectangle rec = collidableObj.getCollisionRectangle();
            Point point = trajectory.closestIntersectionToStartOfLine(rec);
            if (point != null) {
                allIntersectionsClosestPoints.put(collidableObj, point);
            }
        }

        Map.Entry<Collidable, Point> theClosestPoint = closestIntersectionToStartOfLine(trajectory,
                allIntersectionsClosestPoints);
        if (theClosestPoint == null) {
            return null;
        }

        return new CollisionInfo(theClosestPoint.getValue(), theClosestPoint.getKey());
    }

    /**This function return the closest point to the start of the line.
     * @param points the points.
     * @param line the line.
     * @return return the closest intersection point to the start of the line and his matching collidable object.
     * */
    private Map.Entry<Collidable, Point> closestIntersectionToStartOfLine(Line line, java.util.Map<Collidable,
                                                                            Point> points) {
        if (points == null) {
            return null;
        }

        Map.Entry<Collidable, Point> minEntry = null;
        //holds the distance of the closest point from the start.
        double minDis = -1;

        for (Map.Entry<Collidable, Point> entry : points.entrySet()) {
            Point point = entry.getValue();
            double newDis = line.start().distance(point);
            if (newDis < minDis || minDis == -1) {
                minDis = newDis;
                minEntry = entry;
            }
        }

        return minEntry;
    }

}