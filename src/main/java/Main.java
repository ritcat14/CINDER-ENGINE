import core.CinderEngine;
import core.objectManagers.EntityManager;
import core.objectManagers.NullManager;
import temp.Game;
import temp.Start;

import java.awt.*;

public class Main {

    private static double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static void main(String[] args) {
        CinderEngine cinderEngine = new CinderEngine(WIDTH, HEIGHT);
        cinderEngine.addState(new Start(new NullManager()));
        cinderEngine.addState(new Game(new EntityManager()));
        cinderEngine.setState("start");
        cinderEngine.start();
    }
}
