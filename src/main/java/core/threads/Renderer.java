package core.threads;

/*
 *  Class responsible for rendering the current game state
 */

public class Renderer extends Loop {

    private static double MAX_TPS;

    public Renderer(ThreadManager threadManager, int MAX_TPS) {
        super(threadManager, MAX_TPS, "RENDERER");
        Renderer.MAX_TPS = MAX_TPS;
    }

    @Override
    public void init() {
    }

    @Override
    public void onLoop() {
        threadManager.render();
    }

    public static double getMaxTps() {
        return MAX_TPS;
    }
}
