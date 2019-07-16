import core.CinderEngine;
import core.objectManagers.StateManager;
import temp.Game;

import java.awt.*;

public class Main {

    private static double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static void main(String[] args) {
        CinderEngine cinderEngine = new CinderEngine(WIDTH, HEIGHT);
        cinderEngine.addState(new Game());
        cinderEngine.setState(StateManager.States.GAME);
        cinderEngine.start();
    }

}
