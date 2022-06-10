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
 * Represents the Final Four level.*/
public class FinalFourLevel implements LevelInformation {
    private final int maxBlocksInRow = 15;
    private final int blocksRows = 7;

    @Override
    public int numberOfBalls() {
        return Constant.THREE;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<>();
        list.add(new Velocity(Constant.ZERO, Constant.MINUS_ONE * Constant.FOUR));
        list.add(new Velocity(Constant.MINUS_ONE * Constant.FOUR, Constant.MINUS_ONE * Constant.FOUR));
        list.add(new Velocity(Constant.FOUR, Constant.MINUS_ONE * Constant.FOUR));

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
        return "Final Four";
    }

    @Override
    public Sprite getBackground() {
        Rectangle rec = new Rectangle(
                new Point(Constant.BORDER_REC_SIZE, Constant.BORDER_REC_SIZE + Constant.SCORE_BLOCK_HEIGHT),
                Constant.WIDTH - 2 * Constant.BORDER_REC_SIZE,
                Constant.HEIGHT - Constant.BORDER_REC_SIZE - Constant.ONE);
        return new Block(rec, Constant.SPECIAL_BLUE, false);
    }

    @Override
    public List<Block> blocks() {
        List<Block> list = new ArrayList<>();
        Point start = new Point(Constant.WIDTH - Constant.BORDER_REC_SIZE - Constant.BLOCK_WIDTH,
                (int) (Constant.HEIGHT / Constant.FOUR));
        Velocity stepDx = new Velocity(-1 * Constant.BLOCK_WIDTH, 0);
        Velocity stepDy = new Velocity(0, 0);
        Point point = start;
        for (int i = 0; i < blocksRows; i++) {
            Color color = Constant.BLOCKS_COLORS[i];
            for (int j = 0; j < maxBlocksInRow; j++) {
                Rectangle rec = new Rectangle(point, Constant.BLOCK_WIDTH, Constant.PADDLE_HEIGHT);
                Block block = new Block(rec, color, true);
                list.add(block);
                point = stepDx.applyToPoint(point);
            }
            point = start;
            stepDy.setDy(stepDy.getDy() + Constant.PADDLE_HEIGHT);
            point = stepDy.applyToPoint(point);
        }
        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksRows * this.maxBlocksInRow;
    }
}
