package core.threads;

import core.CinderEngine.RenderType;

/*
 *  Class responsible for rendering the current game state
 */

public class Updater extends Loop {

    private final RenderType renderType;

    public Updater(ThreadManager threadManager, double MAX_TPS, RenderType renderType) {
        super(threadManager, MAX_TPS, "UPDATER", renderType);
        this.renderType = renderType;
    }

    @Override
    public synchronized void init() {
        threadManager.init(renderType);
    }

    @Override
    public void onLoop() {
        threadManager.update(renderType);
    }
}
