package core.threads;

import core.graphics.Window;
import core.objectManagers.StateManager;

public class ThreadManager {

    private StateManager stateManager;
    private Renderer renderer;
    private Updater updater;
    private Window window;

    private boolean rendering = false;

    public ThreadManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void init(double width, double height) {
        window = new Window(width, height);
    }

    public boolean checkThreads() {
        return (!updater.isRunning() && !renderer.isRunning());
    }

    public synchronized void update() {
        if (window == null) return;
        if (rendering) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught.");
            }
        }
        stateManager.update();
        window.setThread();
        window.update();
        window.nullThread();
        rendering = true;
        notify();
    }

    public synchronized void render() {
        if (window == null) return;
        if (!rendering) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught.");
            }
        }
        stateManager.render();
        window.setThread();
        window.render();
        window.nullThread();
        rendering = false;
        notify();
    }

    public synchronized void intermediateCode() {
        stateManager.intermediateCode();
        //System.err.println("FPS:" + renderer.getFinalTPS() + " | UPS:" + updater.getFinalTPS());
    }

    public void setUpdater(Updater updater) {
        this.updater = updater;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public synchronized boolean shouldClose() {
        if (window != null) return window.shouldClose();
        return false;
    }
}
