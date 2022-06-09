package collections;

import interfaces.Sprite;
import biuoop.DrawSurface;
import java.util.LinkedList;
import java.util.List;

/**@author rotem ghidale
 * This class hold list of sprites and handle them in the code.*/
public class SpriteCollection {
    private List<Sprite> spritesList;

    /**Constructor.*/
    public SpriteCollection() {
        this.spritesList = new LinkedList<>();
    }

    /**This function add a sprite to the list.
     * @param s a sprite
     * */
    public void addSprite(Sprite s) {
        this.spritesList.add(s);
    }

    /**This function remove a sprite from the list.
     * @param s a sprite*/
    public void removeSprite(Sprite s) {
        this.spritesList.remove(s);
    }

    /**This function call timePassed() on all sprites.*/
    public void notifyAllTimePassed() {
        //make a copy to prevent a multi access to the list, iterate while deleting.
        List<Sprite> sprites = new LinkedList<Sprite>(this.spritesList);

        for (Sprite sprite: sprites) {
            sprite.timePassed();
        }
    }

    /**This function call drawOn(d) on all sprites.
     * @param d the draw surface.
     * */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite: spritesList) {
            sprite.drawOn(d);
        }
    }
}
