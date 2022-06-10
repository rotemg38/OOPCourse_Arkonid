package animations;

import biuoop.Sleeper;
import interfaces.Animation;
import biuoop.DrawSurface;
import biuoop.GUI;
import utilities.Constant;

/**@author rotem ghidale
 * The AnimationRunner takes an Animation object and runs it.*/
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**A constructor.
     * @param gui the gui of the animation.*/
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.framesPerSecond = Constant.FPS;
        this.sleeper = new Sleeper();
    }
    /**Getter.
     *@return the gui object.*/
    public GUI getGui() {
        return gui;
    }

    /**The run loop of the given animation.
     * @param animation the animation.*/
    public void run(Animation animation) {
        int millisecondsPerFrame = Constant.THOUSAND / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();

            animation.doOneFrame(d);

            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}