package collisions;

import interfaces.HitListener;
import sprites.Ball;
import sprites.Block;
import utilities.Constant;
import utilities.Counter;
import game.GameLevel;

/**@author rotem ghidale
 * This class is in charge of removing blocks from the game,
 * as well as keeping count of the number of blocks that remain.*/
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**A constructor.
     * @param gameLevel object represents the game
     * @param removedBlocks a counter of the removed blocks
     * */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.gameLevel);
        remainingBlocks.decrease(Constant.ONE);
    }
}