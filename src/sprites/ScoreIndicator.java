package sprites;

import geometry.primitive.Point;
import interfaces.Sprite;
import utilities.Counter;
import game.GameLevel;
import biuoop.DrawSurface;
import java.awt.Color;

/**@author rotem ghidale
 * This class in charge of displaying the current score of the game.*/
public class ScoreIndicator implements Sprite {
    private Counter currentScore;
    private Point position;
    private final int fontSize = 16;

    /**Constructor.
     * @param position the position start of the score text*/
    public ScoreIndicator(Point position) {
        currentScore = new Counter();
        this.position = position;
    }

    /**This is getter.
     * @return the score counter.*/
    public Counter getCounter() {
        return this.currentScore;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText((int) this.position.getX(), (int) this.position.getY(),
                "Score: " + currentScore.getValue(), fontSize);
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
