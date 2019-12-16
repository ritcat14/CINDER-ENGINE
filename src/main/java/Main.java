import core.CinderEngine;
import core.CinderEngine.RenderType;
import core.objectManagers.EntityManager;
import core.objectManagers.NullManager;
import temp.Game;
import temp.Start;

import java.awt.*;

public class Main {

    private static double WIDTH = 1920;
    private static double HEIGHT = 1080;

    public static void main(String[] args) {
        CinderEngine cinderEngine = new CinderEngine(WIDTH, HEIGHT, RenderType.T2D);
        cinderEngine.addState(new Start(new NullManager()));
        cinderEngine.addState(new Game(new EntityManager()));
        cinderEngine.setState("start");
        cinderEngine.start();
    }
}
