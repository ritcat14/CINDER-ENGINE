package core;

import cache.FileCache;
import core.objectManagers.StateManager;
import core.threads.Renderer;
import core.threads.ThreadManager;
import core.threads.Updater;
import states.State;

public class CinderEngine {

    public static double WIDTH, HEIGHT, ASPECT_RATIO;

    private Updater updater;
    private Renderer renderer;
    private ThreadManager threadManager;
    private StateManager stateManager;

    public CinderEngine(double width, double height) {
        WIDTH = width;
        HEIGHT = height;
        ASPECT_RATIO = width/height;
        threadManager = new ThreadManager(stateManager = new StateManager());
        updater = new Updater(threadManager, 120, width, height);
        renderer = new Renderer(threadManager, 60);
    }

    public synchronized void addState(State state) {
        stateManager.addState(state);
    }

    public synchronized void setState(StateManager.States state) {
        stateManager.setCurrentState(state);
    }

    public void start() {
        init();
        updater.start();
        renderer.start();
        mainLoop();
    }

    private void mainLoop() {
        while (true) if (threadManager.checkThreads()) break;
        System.out.println("Closing Game.");
        System.exit(0);
    }

    private void init() {
        FileCache.init();
    }

}
