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
 * Represents the Wide Easy level.*/
public class WideEasyLevel implements LevelInformation {
    private final int totalBlocks = 15;
    @Override
    public int numberOfBalls() {
        return Constant.TEN;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<>();

        double dx1 = Constant.MINUS_ONE * Constant.TWO - 0.5;
        double dx2 = Constant.TWO + 0.5;
        double dy1 = Constant.MINUS_ONE * Constant.THREE;
        for (int i = 1; i <= numberOfBalls() / 2; i++) {
            list.add(new Velocity(dx2, dy1));
            list.add(new Velocity(dx1, dy1));
            dx1 += 0.5;
            dx2 -= 0.5;
            dy1 -= 0.3;
        }

        return list;
    }

    @Override
    public int paddleSpeed() {
        return Constant.TEN;
    }

    @Override
    public int paddleWidth() {
        return Constant.PADDLE_WIDTH * 4;
    }

    @Override
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public Sprite getBackground() {
        Rectangle rec = new Rectangle(
                new Point(Constant.BORDER_REC_SIZE, Constant.BORDER_REC_SIZE + Constant.SCORE_BLOCK_HEIGHT),
                Constant.WIDTH - 2 * Constant.BORDER_REC_SIZE,
                Constant.HEIGHT - Constant.BORDER_REC_SIZE - Constant.ONE);
        return new Block(rec, Color.WHITE, false);
    }

    @Override
    public List<Block> blocks() {
        List<Block> list = new ArrayList<>();
        Point start = new Point(Constant.WIDTH - Constant.BORDER_REC_SIZE - Constant.BLOCK_WIDTH,
                (int) (Constant.HEIGHT / Constant.FOUR));
        Velocity stepDx = new Velocity(-1 * Constant.BLOCK_WIDTH, 0);
        Point point = start;
        //counters in order to set the colors as required in the assigment
        int counter = 1;
        int colorCount = 0;
        for (int i = 0; i < this.totalBlocks; i++) {
            //set the color of the block
            if (counter >= 3 && i != 8) {
                counter = 1;
                colorCount++;
            }
            Color color = Constant.BLOCKS_COLORS[colorCount];
            counter++;
            //create the block
            Rectangle rec = new Rectangle(point, Constant.BLOCK_WIDTH, Constant.PADDLE_HEIGHT);
            Block block = new Block(rec, color, true);
            list.add(block);
            point = stepDx.applyToPoint(point);
        }
        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.totalBlocks;
    }
}
