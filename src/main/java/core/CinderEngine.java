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
        updater = new Updater(threadManager, 120);
        renderer = new Renderer(threadManager, 60, width, height);
    }

    public void start() {
        init();
        updater.start();
        renderer.start();
    }

    private void init() {
        FileCache.init();
    }

}
