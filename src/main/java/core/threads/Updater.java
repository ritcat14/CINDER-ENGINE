package core.threads;

/*
 *  Class responsible for rendering the current game state
 */

public class Updater extends Loop {

    public Updater(ThreadManager threadManager, double MAX_TPS, RenderType renderType) {
        super(threadManager, MAX_TPS, "UPDATER", renderType);
    }

    @Override
    public synchronized void init() {
        threadManager.init();
    }

    @Override
    public void onLoop() {
        threadManager.update();
    }
}
