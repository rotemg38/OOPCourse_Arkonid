import animations.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.DirectHitLevel;
import game.FinalFourLevel;
import game.GameFlow;
import game.Green3Level;
import game.WideEasyLevel;
import interfaces.LevelInformation;
import utilities.Constant;

import java.util.ArrayList;
import java.util.List;

/**@author rotem ghidale
 * Thia class activates the game.*/
public class Ass6Game {

    private static final LevelInformation[] LEVELS = {
            new DirectHitLevel(),
            new WideEasyLevel(),
            new Green3Level(),
            new FinalFourLevel()
    };

    /**This is the main method who creates a game and run it.
     * @param args the command line arguments*/
    public static void main(String[] args) {

        AnimationRunner runner = new AnimationRunner(new GUI("Arkanoid", Constant.WIDTH, Constant.HEIGHT));
        KeyboardSensor keyboard = runner.getGui().getKeyboardSensor();


        List<LevelInformation> levels = new ArrayList<>();
        for (String arg : args) {
            try {
                int num = Integer.parseInt(arg);
                LevelInformation level = getLevelInfo(num);
                if (level != null) {
                    levels.add(level);
                }
            } catch (NumberFormatException e) {
                //do nothing- ignore
            }
        }

        //means we didnt get from the args any levels
        if (levels.size() == 0) {
            for (int i = 1; i <= Constant.FOUR; i++) {
                levels.add(getLevelInfo(i));
            }
        }

        GameFlow gameFlow = new GameFlow(runner, keyboard);
        gameFlow.runLevels(levels);
    }

    /**This function return the level information according to the given level number.
     * @param num level number
     * @return the level information*/
    private static LevelInformation getLevelInfo(int num) {
        switch (num) {
            case Constant.ONE:
                return LEVELS[Constant.ZERO];
            case Constant.TWO:
                return LEVELS[Constant.ONE];
            case Constant.THREE:
                return LEVELS[Constant.TWO];
            case Constant.FOUR:
                return LEVELS[Constant.THREE];
            default:
                return null;
        }
    }
}
