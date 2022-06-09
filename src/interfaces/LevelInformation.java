package interfaces;

import geometry.primitive.Velocity;
import sprites.Block;
import java.util.List;

/**@author rotem ghidale
 * This interface represents the information of the level.*/
public interface LevelInformation {
    /**The number of balls needed in the level.
     * @return the number of balls*/
    int numberOfBalls();
    /**The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     * @return list of the velocities*/
    List<Velocity> initialBallVelocities();
    /**The paddle speed in the level.
     * @return the paddle speed.*/
    int paddleSpeed();
    /**The paddle width in the level.
     * @return the paddle width.*/
    int paddleWidth();
    /**The level name will be displayed at the top of the screen.
     * @return the level name.*/
    String levelName();
    /**The background of the level.
     * @return a sprite with the background of the level*/
    Sprite getBackground();
    /**The blocks of the level, each block contains its size, color and location.
     * @return the blocks */
    List<Block> blocks();
    /**This function gets the number of blocks that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     * @return number of blocks*/
    int numberOfBlocksToRemove();
}
