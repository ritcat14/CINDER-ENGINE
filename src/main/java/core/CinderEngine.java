package core;

import cache.FileCache;
import core.objectManagers.StateManager;
import core.states.State;
import core.threads.Loader;
import core.threads.Renderer;
import core.threads.ThreadManager;
import core.threads.Updater;

public class CinderEngine {

    public static enum RenderType {
        T2D, T3D;
    }

    public static double WIDTH, HEIGHT, ASPECT_RATIO;

    private ThreadManager threadManager;
    private StateManager stateManager;

    public CinderEngine(double width, double height, RenderType renderType) {
        WIDTH = width;
        HEIGHT = height;
        ASPECT_RATIO = width/height;
        threadManager = new ThreadManager(width, height);
        stateManager = threadManager.getStateManager();
        threadManager.addLoop(new Loader(threadManager, renderType));
        threadManager.addLoop(new Updater(threadManager, 120, renderType));
        threadManager.addLoop(new Renderer(threadManager, 60, renderType));
    }

    public synchronized void addState(State state) {
        stateManager.addState(state);
    }

    public synchronized void setState(String stateName) {
        stateManager.setCurrentState(stateName);
    }

    public void start() {
        init();
        threadManager.startLoops();
        mainLoop();
    }

    private void mainLoop() {
        while (true) threadManager.checkThreads();
    }

    private void init() {
        FileCache.init();
    }

    public ThreadManager getThreadManager() {
        return threadManager;
    }
}
