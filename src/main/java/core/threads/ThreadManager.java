package core.threads;


import core.graphics.Window;

public class ThreadManager {

    private Renderer renderer;
    private Updater updater;
    private Window window;

    private boolean rendering = false;

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
        window.setThread();
        window.render();
        window.nullThread();
        rendering = false;
        notify();
    }

    public synchronized void intermediateCode() {
        System.err.println("FPS:" + renderer.getFinalTPS() + " | UPS:" + updater.getFinalTPS());
    }

    public void setUpdater(Updater updater) {
        this.updater = updater;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public synchronized Window getWindow() {
        return window;
    }

    public void cleanUp() {
        updater = null;
        renderer = null;
        window = null;
        rendering = false;
    }

    public synchronized boolean shouldClose() {
        if (window != null) return window.shouldClose();
        return false;
    }
}
