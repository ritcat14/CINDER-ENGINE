package core.threads;

import core.CinderEngine;
import core.graphics.Window;
import core.loading.Resource;
import core.objectManagers.StateManager;
import core.objects.Object;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadManager extends Object {

    private final double width, height;

    private ConcurrentLinkedQueue<Loop> loops = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Resource> resources = new ConcurrentLinkedQueue<>();
    private Window window;
    private StateManager stateManager;

    public ThreadManager(double width, double height) {
        this.width = width;
        this.height = height;
        stateManager = new StateManager(this);
    }

    @Override
    public void init() {
        window = new Window(width, height);
    }

    public synchronized void update() {
        if (window == null) return;
        window.update();
        stateManager.update();
    }

    @Override
    public void render(Graphics graphics) {
    }

    public synchronized void render() {
        if (window == null) return;
        window.render(stateManager);
        checkClosing();
    }

    public synchronized void addResource(Resource resource) {
        resources.add(resource);
    }

    public synchronized void updateResources() {
        ((Loader) getLoop("loader")).addResources(resources);
        resources.clear();
    }

    public synchronized StateManager getStateManager() {
        return stateManager;
    }

    /*
     * Loop handling code
     */

    public synchronized Loop getLoop(String name) {
        name = name.toUpperCase();
        for (Loop loop : loops) {
            if (loop.getName().equals(name)) return loop;
        }
        throw new NullPointerException("Cannot find loop " + name);
    }

    public boolean checkThreads() {
        for (Loop loop : loops) if (!loop.isClosed()) return true;
        System.out.println("Closing Game.");
        System.exit(0);
        return false;
    }

    private synchronized void checkClosing() {
        if (CinderEngine.CLOSED || window.shouldClose()) {
            CinderEngine.CLOSE();
            for (Loop loop : loops) loop.stopRunning();
        }
    }

    public synchronized void addLoop(Loop loop) {
        loops.add(loop);
    }

    public synchronized void stopLoopRunning(String name) {
        getLoop(name).stopRunning();
    }

    public synchronized void stopLoop(String name) {
        getLoop(name).stop();
    }

    public synchronized void startLoop(String name) {
        getLoop(name).start();
    }

    public synchronized void startLoops() {
        for (Loop loop : loops) loop.start();
    }

    public synchronized void stopLoops() {
        for (Loop loop : loops) loop.stop();
    }

    public synchronized void stopLoopsRunning() {
        for (Loop loop : loops) loop.stopRunning();
    }

}
