package collisions;

import interfaces.HitListener;
import utilities.Constant;
import utilities.Counter;
import sprites.Ball;
import sprites.Block;
import sprites.Paddle;
import game.GameLevel;

/**@author rotem ghidale
 * This class is in charge of removing balls from the game,
 * as well as keeping count of the number of balls that remain.*/
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;
    private Paddle paddle;

    /**A constructor.
     * @param gameLevel object represents the game
     * @param removedBalls a counter of the remained balls
     * @param paddle the paddle object
     * */
    public BallRemover(GameLevel gameLevel, Counter removedBalls, Paddle paddle) {
        this.gameLevel = gameLevel;
        this.remainingBalls = removedBalls;
        this.paddle = paddle;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        remainingBalls.decrease(Constant.ONE);
        //remove the ball also from the paddle balls.
        paddle.removeBall(hitter);
    }
}