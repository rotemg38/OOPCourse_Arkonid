package interfaces;

import biuoop.DrawSurface;

/**@author rotem ghidale
 * This interface represents an animation.*/
public interface Animation {
    /**This function responsible on what we do in each frame.
     * @param d the draw surface*/
    void doOneFrame(DrawSurface d);
    /**This function tell us if we should stop the animation.
     * @return if true if we should stop the animation , otherwise false*/
    boolean shouldStop();
}