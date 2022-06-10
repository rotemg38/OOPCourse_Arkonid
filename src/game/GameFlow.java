package game;

import animations.AnimationRunner;
import animations.EndScreen;
import animations.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;
import geometry.primitive.Point;
import interfaces.LevelInformation;
import sprites.ScoreIndicator;
import utilities.Constant;
import java.util.List;

/**@author rotem ghidale
 * This class will be in charge of creating the different levels, and moving from one level to the next.*/
public class GameFlow {
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private ScoreIndicator scoreIndicator;

    /**A constructor.
     * @param ar the animation runner
     * @param ks the keyboard sensor from the gui*/
    public GameFlow(AnimationRunner ar, KeyboardSensor ks) {
        this.keyboardSensor = ks;
        this.animationRunner = ar;
        this.scoreIndicator = new ScoreIndicator(new Point(Constant.WIDTH / 2.0, Constant.SCORE_BLOCK_HEIGHT));
    }

    /**This function run the given level by their order in the given list.
     *@param levels the levels list */
    public void runLevels(List<LevelInformation> levels) {
        String msg = "You Won!";
        for (LevelInformation levelInfo : levels) {

            GameLevel level = new GameLevel(levelInfo, this.animationRunner, this.keyboardSensor, this.scoreIndicator);

            level.initialize();

            //the level run as long as the balls didnt fall and the blocks didnt ended
            level.run();

            //if all the balls fall we need to end the game
            if (level.getRemainingBalls() == 0) {
                msg = "Game Over!";
                break;
            }

        }

        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                new EndScreen(msg, this.scoreIndicator.getCounter().getValue())));
        this.animationRunner.getGui().close();
    }

}
