package game;

import animations.AnimationRunner;
import animations.CountdownAnimation;
import animations.KeyPressStoppableAnimation;
import animations.PauseScreen;
import biuoop.KeyboardSensor;
import collections.GameEnvironment;
import collections.SpriteCollection;
import collisions.BallRemover;
import collisions.BlockRemover;
import collisions.ScoreTrackingListener;
import geometry.primitive.Point;
import geometry.primitive.Rectangle;
import biuoop.DrawSurface;
import geometry.primitive.Velocity;
import interfaces.Animation;
import interfaces.Collidable;
import interfaces.LevelInformation;
import interfaces.Sprite;
import sprites.Ball;
import sprites.Block;
import sprites.HeaderBlock;
import sprites.Paddle;
import sprites.ScoreIndicator;
import utilities.Constant;
import utilities.Counter;
import java.awt.Color;
import java.util.List;

/**@author rotem ghidale
 * This class represents the game.Game*/
public class GameLevel implements Animation {
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private ScoreIndicator scoreIndicator;
    private LevelInformation levelInformation;
    private HeaderBlock header;

    /**Constructor.
     * @param keyboard the keyboard sensor from the gui
     * @param levelInformation the current level information we should run
     * @param runner the animation runner in order to run the level
     * @param scoreIndicator the score of the player in order t update during the running level*/
    public GameLevel(LevelInformation levelInformation, AnimationRunner runner, KeyboardSensor keyboard,
                     ScoreIndicator scoreIndicator) {
        this.levelInformation = levelInformation;
        this.runner = runner;
        this.keyboard = keyboard;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter(levelInformation.numberOfBlocksToRemove());
        this.remainingBalls = new Counter(levelInformation.numberOfBalls());
        this.scoreIndicator = scoreIndicator;
        this.header = new HeaderBlock(levelInformation.levelName());
        header.addToGame(this);
        scoreIndicator.addToGame(this);
    }

    /**This function add a collidable object to the game.
     * @param c a collidable object.*/
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**This function add a sprite to the game.
     * @param s a interfaces.Sprite*/
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**This function remove a collidable object from the game.
     * @param c a collidable object.*/
    public void removeCollidable(Collidable c)  {
        environment.removeCollidable(c);
    }

    /**This function remove a sprite from the game.
     * @param s a interfaces.Sprite*/
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**This function builds the blocks which represents the frame of the window.
     * @return the block of the "dead zone"- down side*/
    private Block buildBlocksFrame() {
        Block side;
        //boundaries rectangle for the frame.
        //up
        Rectangle rec = new Rectangle(new Point(0, Constant.SCORE_BLOCK_HEIGHT + Constant.TWO),
                Constant.WIDTH, Constant.BORDER_REC_SIZE);
        side = new Block(rec, Color.DARK_GRAY, false);
        side.addToGame(this);

        //left
        rec = new Rectangle(new Point(0, Constant.BORDER_REC_SIZE), Constant.BORDER_REC_SIZE,
                Constant.HEIGHT - Constant.BORDER_REC_SIZE);
        side = new Block(rec, Color.DARK_GRAY, false);
        side.addToGame(this);

        //down
        Block down;
        rec = new Rectangle(new Point(Constant.BORDER_REC_SIZE, Constant.HEIGHT - Constant.ONE),
                Constant.WIDTH - 2 * Constant.BORDER_REC_SIZE, Constant.ONE);
        down = new Block(rec, Color.DARK_GRAY, false);
        down.addToGame(this);

        //right
        rec = new Rectangle(new Point(Constant.WIDTH - Constant.BORDER_REC_SIZE, Constant.BORDER_REC_SIZE),
                Constant.BORDER_REC_SIZE, Constant.HEIGHT - Constant.BORDER_REC_SIZE);
        side = new Block(rec, Color.DARK_GRAY, false);
        side.addToGame(this);

        //background
        this.levelInformation.getBackground().addToGame(this);

        return down;
    }

    /**This function create the paddle of the game and the balls.
     * @param downSide the dead zone- the down side of the frame*/
    private void createPaddleAndBalls(Block downSide) {
        //set the paddle center
        Point center = new Point((int) ((Constant.WIDTH - 2 * Constant.BORDER_REC_SIZE) / 2
                - (levelInformation.paddleWidth() / 2)),
                Constant.HEIGHT - Constant.PADDLE_HEIGHT - Constant.FOUR);
        Rectangle paddleRec = new Rectangle(center, levelInformation.paddleWidth(), Constant.PADDLE_HEIGHT);
        Block paddleBlock = new Block(paddleRec, Color.ORANGE, true);
        Paddle paddle = new Paddle(paddleBlock, this.keyboard,
                                    levelInformation.paddleSpeed());
        paddle.addToGame(this);

        //create balls
        //set the ball center
        Velocity move = new Velocity(levelInformation.paddleWidth() / 2, -20);
        center = move.applyToPoint(center);
        //get velocities
        List<Velocity> velocityList = levelInformation.initialBallVelocities();
        for (int i = 0; i < levelInformation.numberOfBalls(); i++) {
            Velocity velocity = velocityList.get(i);
            Ball ball = new Ball(center, Constant.RADIUS, Color.WHITE, environment);
            ball.setVelocity(velocity.getDx(), velocity.getDy());
            ball.addToGame(this);
            paddle.addBall(ball);
        }

        //we do this in order to update the list of balls in the paddle also when balls falls and get out of the game.
        downSide.addHitListener(new BallRemover(this, this.remainingBalls, paddle));
    }

    /**This function build the blocks in the game.*/
    private void buildBlocks() {
        List<Block> blocks = levelInformation.blocks();

        for (int i = 0; i < levelInformation.numberOfBlocksToRemove(); i++) {
            Block block = blocks.get(i);
            block.addHitListener(new BlockRemover(this, this.remainingBlocks));
            block.addHitListener(new ScoreTrackingListener(this.scoreIndicator.getCounter()));
            block.addToGame(this);
        }
    }

    /**This function Initialize a new game: create the Blocks and Ball (and Paddle) and add them to the game.
     * */
    public void initialize() {
        //creates the frame.
        Block downSide = buildBlocksFrame();
        //creates the paddle and the balls.
        createPaddleAndBalls(downSide);
        //create the rest of the blocks
        buildBlocks();
    }

    /**This function Run the game - start the animation loop.*/
    public void run() {
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        // use our runner to run the current animation -- which is one turn of the game.
        this.runner.run(this);
    }

    /**This function checks if the game should be ended.
     * @return true if should be terminated, false else */
    private boolean shouldTerminate() {
        boolean blockStatus = this.remainingBlocks.getValue() != Constant.ZERO;
        if (blockStatus && this.remainingBalls.getValue() != Constant.ZERO) {
            return false;
        }

        if (!blockStatus) {
            this.scoreIndicator.getCounter().increase(Constant.END_BONUS);
        }
        return true;
    }
    /**This is a getter.
     * @return the remaining blocks*/
    public int getRemainingBlocks() {
        return this.remainingBlocks.getValue();
    }
    /**This is a getter.
     * @return the remaining balls*/
    public int getRemainingBalls() {
        return this.remainingBalls.getValue();
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        if (shouldTerminate()) {
            this.running = false;
        }
        this.sprites.notifyAllTimePassed();
        //add a pause option
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                    new PauseScreen()));
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }
}