package collisions;

import interfaces.HitListener;
import sprites.Ball;
import sprites.Block;
import utilities.Constant;
import utilities.Counter;

/**@author rotem ghidale
 * This class responsible on the score- update this counter when blocks are being hit and removed.*/
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**Constructor.
     * @param scoreCounter the score counter*/
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
       this.currentScore.increase(Constant.HIT_SCORE);
    }
}