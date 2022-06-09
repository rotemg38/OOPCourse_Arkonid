package interfaces;

import sprites.Ball;
import sprites.Block;

/**@author rotem ghidale
 * This interface implemented by classes who want to be notified of hit events.*/
public interface HitListener {
    /**This method is called whenever the beingHit object is hit.
     * The hitter parameter is the sprites.Ball that's doing the hitting.
     * @param beingHit the block is being hitted
     * @param hitter the ball that hit the block
     * */
    void hitEvent(Block beingHit, Ball hitter);
}