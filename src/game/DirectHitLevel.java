package game;

import geometry.primitive.Point;
import geometry.primitive.Rectangle;
import geometry.primitive.Velocity;
import interfaces.LevelInformation;
import interfaces.Sprite;
import sprites.Block;
import utilities.Constant;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**@author rotem ghidale
 * Represents the Direct hit level, which is one block with one ball go directly to destroy the block.*/
public class DirectHitLevel implements LevelInformation {
    @Override
    public int numberOfBalls() {
        return Constant.ONE;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        //the ball need to go directly to the block.
        List<Velocity> list = new ArrayList<>();
        list.add(new Velocity(Constant.ZERO, Constant.MINUS_ONE * Constant.THREE));
        return list;
    }

    @Override
    public int paddleSpeed() {
        return Constant.TEN;
    }

    @Override
    public int paddleWidth() {
        return Constant.PADDLE_WIDTH;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        Rectangle rec = new Rectangle(
                new Point(Constant.BORDER_REC_SIZE, Constant.BORDER_REC_SIZE + Constant.SCORE_BLOCK_HEIGHT),
                Constant.WIDTH - 2 * Constant.BORDER_REC_SIZE,
                Constant.HEIGHT - Constant.BORDER_REC_SIZE - Constant.ONE);
        return new Block(rec, Color.black, false);
    }

    @Override
    public List<Block> blocks() {
        List<Block> list = new ArrayList<>();
        Point upperLeft = new Point((int) ((Constant.WIDTH - 2 * Constant.BORDER_REC_SIZE) / 2)
                - (paddleWidth() / 4),
                Constant.BORDER_REC_SIZE + Constant.BLOCK_WIDTH);
        Rectangle rec = new Rectangle(upperLeft, Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH);
        list.add(new Block(rec, Color.RED, false));
        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return Constant.ONE;
    }
}
