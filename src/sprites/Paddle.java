package sprites;

import geometry.primitive.Velocity;
import geometry.primitive.Line;
import geometry.primitive.Point;
import geometry.primitive.Rectangle;
import interfaces.Collidable;
import interfaces.Sprite;
import utilities.Constant;
import game.GameLevel;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**@author rotem ghidale
 * This class represents the paddle in our game.*/
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Block paddle;
    private int speed;
    private List<Ball> balls;

    /**Constructor.
     * @param paddle the paddle block
     * @param keyboard the keyboard sensor from the game
     * @param speed the paddle speed*/
    public Paddle(Block paddle, KeyboardSensor keyboard, int speed) {
        this.speed = speed;
        this.paddle = paddle;
        this.keyboard = keyboard;
        this.balls = new LinkedList<>();
    }

    /**This function add ball to a list of balls.
     * @param b a ball to add.*/
    public void addBall(Ball b) {
        this.balls.add(b);
    }

    /**This function remove ball from the list of balls.
     * @param b a ball to remove.*/
    public void removeBall(Ball b) {
        this.balls.remove(b);
    }

    /**This function tells as if the paddle hit a ball when he wants to move left or right.
     * @param side "left" \ "right"- which side the paddle want to move
     * @return true if the paddle hits a ball, otherwise false.*/
    private boolean isHitBall(String side) {
        Line sideRec = paddle.getCollisionRectangle().getRectangleSides().get(side);
        for (Ball ball : balls) {
            Point center = new Point(ball.getX(), ball.getY());
            Point end;
            if (side.equals("right")) {
                end = new Point(center.getX() - ball.getSize(), center.getY());
            } else {
                end = new Point(center.getX() + ball.getSize(), center.getY());
            }
            Line line = new Line(center, end);

            if (sideRec.isIntersecting(line)) {
                return true;
            }
            if (paddle.getCollisionRectangle().isInRectangle(center)) {
                return true;
            }
        }
        return false;
    }

    /**This function move the paddle to the left considering the objects and the window limits.*/
    public void moveLeft() {
        final Velocity velocityLeft = new Velocity(-1 * this.speed, Constant.ZERO);;
        //check if we are at the end of the screen- then do not move.
        double distanceFromEdge = Constant.BORDER_REC_SIZE
                - (paddle.getCollisionRectangle().getUpperLeft().getX() + velocityLeft.getDx());

        if (distanceFromEdge > 0) {
            velocityLeft.setDx(velocityLeft.getDx() + distanceFromEdge);
        }

        paddle.getCollisionRectangle().setUpperLeft(
                velocityLeft.applyToPoint(paddle.getCollisionRectangle().getUpperLeft()));
        //if we hit a ball- move back
        if (isHitBall("left")) {
            velocityLeft.setDx(Constant.TEN);
            paddle.getCollisionRectangle().setUpperLeft(
                    velocityLeft.applyToPoint(paddle.getCollisionRectangle().getUpperLeft()));
        }
    }

    /**This function move the paddle to the right considering the objects and the window limits.*/
    public void moveRight() {
        final Velocity velocityRight = new Velocity(this.speed, Constant.ZERO);;
        //check if we are at the end of the screen- then do not move.
        double distanceFromEdge = (Constant.WIDTH - Constant.BORDER_REC_SIZE)
                - (paddle.getCollisionRectangle().getUpperLeft().getX() + velocityRight.getDx()
                + paddle.getCollisionRectangle().getWidth());

        if (distanceFromEdge < 0) {
            velocityRight.setDx(velocityRight.getDx() + distanceFromEdge);
        }

        paddle.getCollisionRectangle().setUpperLeft(
                velocityRight.applyToPoint(paddle.getCollisionRectangle().getUpperLeft()));
        //if we hit a ball- move back
        if (isHitBall("right")) {
            velocityRight.setDx(-1 * Constant.TEN);
            paddle.getCollisionRectangle().setUpperLeft(
                    velocityRight.applyToPoint(paddle.getCollisionRectangle().getUpperLeft()));
        }
    }
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }
    @Override
    public void drawOn(DrawSurface d) {
        paddle.drawOn(d);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddle.getCollisionRectangle();
    }

    /**This function return the region we hit on the paddle.
     * @param collisionPoint the point we hit on the paddle.
     * @return the region we hit*/
    private int getRegion(Point collisionPoint) {
        double pointX = paddle.getCollisionRectangle().getUpperLeft().getX();
        /*plus 1 because (int) is round down.
        might return 6 if its divide with 5 but the calling function consider it as 5*/
        return (int) (collisionPoint.getX() - pointX) / (Constant.PADDLE_WIDTH / Constant.REGIONS) + 1;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Map<String, Line> rectangleSides = paddle.getCollisionRectangle().getRectangleSides();

        //only if the ball hit on the upper side of the paddle- set the ball velocity according to regions.
        if (rectangleSides.get("up").getEquation().isOnEquation(collisionPoint)) {
            int region = getRegion(collisionPoint);
            //check if in region 3- then the handle is similar to block's, else- handle according to region
            if (region != Constant.THREE) {
                double angle = 0;
                double currentSpeed =  (Math.sqrt(Math.pow(currentVelocity.getDy(), Constant.TWO)
                        + Math.pow(currentVelocity.getDx(), Constant.TWO)));
                switch (region) {
                    case Constant.ONE:
                        angle = 300;
                        break;
                    case Constant.TWO:
                        angle = 330;
                        break;
                    case Constant.FOUR:
                        angle = 30;
                        break;
                    default:
                        angle = 60;
                        break;
                }
                return Velocity.fromAngleAndSpeed(angle, currentSpeed);
            }
        }
        return this.paddle.hit(hitter, collisionPoint, currentVelocity);
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    @Override
    public void removeFromGame(GameLevel g) {
        //note current this function has no use
        g.addCollidable(this);
        g.addSprite(this);
    }
}