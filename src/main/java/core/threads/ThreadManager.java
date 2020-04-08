package core.threads;

import core.CinderEngine;
import core.graphics.Window;
import core.objectManagers.StateManager;
import core.sout.LogType;
import core.sout.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadManager {

    private final double width, height;

    private final List<Loop> loops = Collections.synchronizedList(new ArrayList<>());
    private Window window;
    private StateManager stateManager;

    public ThreadManager(double width, double height) {
        this.width = width;
        this.height = height;
        stateManager = new StateManager();
    }

    public void init() {
        window = new Window(width, height, stateManager);
    }

    public synchronized void update() {
        if (window == null) return;
        window.update();
        stateManager.update();
        checkClosing();
    }

    public synchronized void render() {
        if (window == null) return;
        window.render(stateManager);
    }

    public synchronized StateManager getStateManager() {
        return stateManager;
    }

    /*
     * Loop handling code
     */

    public synchronized Loop getLoop(String name) {
        name = name.toUpperCase();

        synchronized (loops) {
            for (Loop loop : loops) {
                if (name.equals(loop.getName())) return loop;
            }
        }
        throw new NullPointerException("Cannot find loop " + name);
    }

    public boolean checkThreads() {
        synchronized (loops) {
            for (Loop loop : loops) if (!loop.isClosed()) return true;
        }
        Logger.PRINT(LogType.THREAD, "Threads closed.");
        Window.CLOSE();
        return false;
    }

    private synchronized void checkClosing() {
        if (CinderEngine.CLOSED || window.shouldClose()) {
            CinderEngine.CLOSE();
            stateManager.cleanUp();

            synchronized (loops) {
                for (Loop loop : loops) loop.stopRunning();
            }
        }
    }

    public synchronized void addLoop(Loop loop) {
        synchronized (loops) {
            loops.add(loop);
        }
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
