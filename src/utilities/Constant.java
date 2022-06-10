package utilities;

import java.awt.Color;

/**
 * @author rotem ghidale
 * This class holds all of the constants we need to our project.*/
public class Constant {
    public static final double EPSILON = Math.pow(10, -15);
    public static final double TO_RADIAN = Math.PI / 180;
    public static final int MINUS_ONE = -1;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int TEN = 10;
    public static final int FONT_SIZE = 15;
    public static final int THOUSAND = 1000;
    public static final Color[] BLOCKS_COLORS = {Color.RED, Color.PINK, Color.GRAY, Color.CYAN,
            Color.ORANGE, Color.YELLOW, Color.BLUE};
    public static final Color SPECIAL_BLUE = new Color(64, 134, 202);
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int FPS = 60;
    public static final int BORDER_REC_SIZE = 25;
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 20;
    public static final int SCORE_BLOCK_HEIGHT = 18;
    public static final int BLOCK_WIDTH = 50;
    public static final int REGIONS = 5;
    public static final int RADIUS = 7;
    //score policy
    public static final int END_BONUS = 100;
    public static final int HIT_SCORE = 5;
}
