package core;

import cache.FileCache;
import core.threads.Renderer;
import core.threads.ThreadManager;
import core.threads.Updater;

public class CinderEngine {

    public static double WIDTH, HEIGHT, ASPECT_RATIO;

    private Updater updater;
    private Renderer renderer;
    private ThreadManager threadManager;

    public CinderEngine(double width, double height) {
        WIDTH = width;
        HEIGHT = height;
        ASPECT_RATIO = width/height;
        threadManager = new ThreadManager();
        updater = new Updater(threadManager, 120, width, height);
        renderer = new Renderer(threadManager, 60);
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
