package animations;

import biuoop.DrawSurface;
import interfaces.Animation;
import java.awt.Color;

/**@author rotem ghidale
 * A simple animation, that will display a screen with the message of game ended-press space to continue.*/
public class EndScreen implements Animation {
    private boolean stop;
    private String msg;
    private int score;
    /**A Constructor.
     * @param msg the message that will displayed on the screen
     * @param score the final score of the player*/
    public EndScreen(String msg, int score) {
        this.stop = false;
        this.msg = msg;
        this.score = score;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        Color[] colors = {Color.BLACK, Color.RED};
        for (int i = 0; i < 2; i++) {
            d.setColor(colors[i]);
            d.drawText(d.getWidth() / 2 - 100 + (i * 2), d.getHeight() / 2 - 150, msg, 32);
            d.drawText(d.getWidth() / 2 - 100 + (i * 2), d.getHeight() / 2 - 150 + 50, "Your Score is: " + score, 28);

        }
    }
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}