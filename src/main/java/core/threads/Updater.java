package core.threads;

/*
 *  Class responsible for rendering the current game state
 */

import states.State;

public class Updater extends Loop {

    private State state;

    public Updater(ThreadManager threadManager, double MAX_TPS) {
        super(threadManager, MAX_TPS, "UPDATER");
    }

    @Override
    public void init() {
    }

    @Override
    protected void loop() {
        if (state != null) state.update();
        threadManager.intermediateCode();
    }
}
