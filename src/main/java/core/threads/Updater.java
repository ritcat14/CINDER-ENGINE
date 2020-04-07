package core.threads;

/*
 *  Class responsible for rendering the current game state
 */

public class Updater extends Loop {

    private static double MAX_TPS;

    public Updater(ThreadManager threadManager, double MAX_TPS) {
        super(threadManager, MAX_TPS, "UPDATER");
        Updater.MAX_TPS = MAX_TPS;
    }

    @Override
    public synchronized void init() {
        threadManager.init();
    }

    @Override
    public void onLoop() {
        threadManager.update();
    }

    public static double getMaxTps() {
        return MAX_TPS;
    }
}
