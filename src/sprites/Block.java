package sprites;

import geometry.primitive.Velocity;
import geometry.primitive.Line;
import geometry.primitive.Point;
import geometry.primitive.Rectangle;
import interfaces.Collidable;
import interfaces.HitListener;
import interfaces.HitNotifier;
import interfaces.Sprite;
import game.GameLevel;
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**@author rotem ghidale
 * This class represents a block in the game.*/
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle block;
    private Color color;
    private List<HitListener> hitListeners;
    private boolean withBorder;

    /**Constructor.
     * @param color the color of the block
     * @param block the block information as a rectangle object
     * @param withBorder true if want to draw the block with black border false if without any border.*/
    public Block(Rectangle block, Color color, Boolean withBorder) {
        this.block = block;
        this.color = color;
        this.hitListeners = new ArrayList<>();
        this.withBorder = withBorder;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**This function notify all listeners on the acuured hit.
     * @param hitter the hitted ball*/
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Map<String, Line> rectangleSides = block.getRectangleSides();
        Velocity newVelocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());

        if (rectangleSides.get("up").getEquation().isOnEquation(collisionPoint)
            || rectangleSides.get("down").getEquation().isOnEquation(collisionPoint)) {
           newVelocity.setDy(currentVelocity.getDy() * (-1));
        }

        if (rectangleSides.get("right").getEquation().isOnEquation(collisionPoint)
            || rectangleSides.get("left").getEquation().isOnEquation(collisionPoint)) {
            newVelocity.setDx(currentVelocity.getDx() * (-1));
        }
        notifyHit(hitter);
        return newVelocity;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillRectangle((int) block.getUpperLeft().getX(), (int) block.getUpperLeft().getY(),
                (int) block.getWidth(), (int) block.getHeight());
        //set a black border.
        if (withBorder) {
            surface.setColor(Color.BLACK);
            surface.drawRectangle((int) block.getUpperLeft().getX(), (int) block.getUpperLeft().getY(),
                    (int) block.getWidth(), (int) block.getHeight());
        }
    }

    @Override
    public void timePassed() {
        //note: in the meantime this function do nothing.
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    @Override
    public void removeFromGame(GameLevel g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}
