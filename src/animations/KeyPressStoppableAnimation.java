package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;
import java.awt.Color;

/**@author rotem ghidale
 * This class is a decorator-class that will wrap an existing animation and add a "waiting-for-key" behavior to it.*/
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboard;
    private Animation animation;
    private String key;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**A constructor.
     * @param animation the animation.
     * @param key the key we are pressing in order to stop.
     * @param sensor the keyboard sensor*/
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboard = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 - 100, d.getHeight() / 2 - 150 + 100,
                "press " + this.key + " to continue", 20);
        if (this.keyboard.isPressed(this.key)) {
            if (!isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop || animation.shouldStop();
    }
}