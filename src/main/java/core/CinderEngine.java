package core;

import cache.FileCache;
import core.graphics.Renderer;
import core.logic.Updater;
import states.State;

import java.util.ArrayList;
import java.util.List;

public class CinderEngine {

    public static double WIDTH, HEIGHT, ASPECT_RATIO;

    private List<State> states = new ArrayList<>();
    private Updater updater;
    private Renderer renderer;

    public CinderEngine(double width, double height) {
        WIDTH = width;
        HEIGHT = height;
        ASPECT_RATIO = width/height;
        updater = new Updater();
        renderer = new Renderer(width, height);
    }

    public void init() {
        FileCache.init();
    }

    public synchronized void start() {
        updater.start();
        renderer.start();
    }

    public void addState(State state) {
        states.add(state);
    }

}
