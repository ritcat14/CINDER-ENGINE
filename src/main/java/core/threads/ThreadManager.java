package core.threads;

import core.graphics.Window;
import core.loading.Resource;
import core.objectManagers.StateManager;
import core.objects.Object;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadManager implements Object {

    private final double width, height;

    private CopyOnWriteArrayList<Loop> loops = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Resource> resources = new CopyOnWriteArrayList<>();
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
        window.setThread();
        window.update();
        stateManager.update();
        window.nullThread();
    }

    public synchronized void render() {
        if (window == null) return;
        window.setThread();
        window.render();
        stateManager.render();
        checkClosing();
        window.nullThread();
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
        Iterator<Loop> it = loops.iterator();
        while (it.hasNext()) {
            Loop loop = it.next();
            if (loop.getName().equals(name)) return loop;
        }
        throw new NullPointerException("Cannot find loop " + name);
    }

    public void checkThreads() {
        Iterator<Loop> it = loops.iterator();
        while (it.hasNext()) if (!it.next().isClosed()) return;
        System.out.println("Closing Game.");
        System.exit(0);
    }

    private synchronized void checkClosing() {
        if (window.shouldClose()) {
            Iterator<Loop> it = loops.iterator();
            while (it.hasNext()) it.next().stopRunning();
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
        Iterator<Loop> it = loops.iterator();
        while (it.hasNext()) it.next().start();
    }

    public synchronized void stopLoops() {
        Iterator<Loop> it = loops.iterator();
        while (it.hasNext()) it.next().stop();
    }

    public synchronized void stopLoopsRunning() {
        Iterator<Loop> it = loops.iterator();
        while (it.hasNext()) it.next().stopRunning();
    }

}
