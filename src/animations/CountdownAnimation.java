package animations;

import biuoop.Sleeper;
import biuoop.DrawSurface;
import collections.SpriteCollection;
import interfaces.Animation;
import utilities.Constant;

import java.awt.Color;

/**@author rotem ghidale
 * The CountdownAnimation will display the given gameScreen,
for numOfSeconds seconds, and on top of them it will show
a countdown from countFrom back to 1, where each number will
appear on the screen for (numOfSeconds / countFrom) seconds, before
it is replaced with the next one.*/
public class CountdownAnimation implements Animation {
    private SpriteCollection spriteCollection;
    private double numOfSeconds;
    private int countFrom;
    private int text;
    private Sleeper sleeper;

    /**A constructor.
     * @param countFrom from which number we are counting.
     * @param gameScreen the sprites in order to show the game as a "background"
     * @param numOfSeconds for how long the countdown occur
     * */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.spriteCollection = gameScreen;
        this.text = countFrom;
        this.sleeper = new Sleeper();
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        //sleep more except from the first time
        if (this.text != 3) {
            sleeper.sleepFor((long) (1000 / (numOfSeconds / countFrom)) - Constant.FPS * 10);
        }
        if (this.text != 0) {
            spriteCollection.drawAllOn(d);
            d.setColor(Color.BLUE);
            d.drawText((Constant.WIDTH - 2 * Constant.BORDER_REC_SIZE) / 2, (int) (Constant.HEIGHT / 2.0),
                    String.valueOf(this.text), 120
            );
        }
        this.text--;
    }
    @Override
    public boolean shouldStop() {
        return text == -1;
    }
}
