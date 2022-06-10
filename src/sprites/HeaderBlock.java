package sprites;

import biuoop.DrawSurface;
import game.GameLevel;
import interfaces.Sprite;
import utilities.Constant;
import java.awt.Color;

/**@author rotem ghidale
 * This class represents the header which contains the level name and number of lives.*/
public class HeaderBlock implements Sprite {
    private String levelName;

    /**A Constructor.
     * @param levelName the level name to present on the block header.*/
    public HeaderBlock(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText((int) (Constant.WIDTH / 2.0) - 150, Constant.SCORE_BLOCK_HEIGHT,
                "Lives: 1", Constant.FONT_SIZE);
        d.drawText((int) (Constant.WIDTH / 2.0) + 150, Constant.SCORE_BLOCK_HEIGHT,
                "Level Name: " + this.levelName, Constant.FONT_SIZE);
    }

    @Override
    public void timePassed() {

    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void removeFromGame(GameLevel g) {
        //note- currently there is no use to this function
        g.removeSprite(this);
    }
}
