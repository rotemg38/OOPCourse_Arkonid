package interfaces;

import game.GameLevel;
import biuoop.DrawSurface;

/**@author rotem ghidale
 * This Interface represents a sprite in the game*/
public interface Sprite {

    /**This function draw the sprite to the screen.
     * @param d the draw surface
     * */
    void drawOn(DrawSurface d);

    /**This function notify the sprite that time has passed.*/
    void timePassed();

    /**This function add the sprite to the game.
     * @param g the game*/
    void addToGame(GameLevel g);

    /**This function remove a sprite from the game.
     * @param g the game*/
    void removeFromGame(GameLevel g);
}