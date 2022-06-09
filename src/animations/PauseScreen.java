package animations;

import biuoop.DrawSurface;
import interfaces.Animation;
import java.awt.Color;

/**@author rotem ghidale
 * A simple animation, that will display a screen with the message paused--
 * press space to continue until a key is pressed.*/
public class PauseScreen implements Animation {
    private boolean stop;

    /**A Constructor.*/
    public PauseScreen() {
        this.stop = false;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        Color[] colors = {Color.BLACK, Color.BLUE};
        for (int i = 0; i < 2; i++) {
            d.setColor(colors[i]);
            d.drawText(d.getWidth() / 2 - 100 + (i * 2), d.getHeight() / 2 - 150, "PAUSED", 40);
        }
    }
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}