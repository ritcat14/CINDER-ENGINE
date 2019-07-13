package core.threads;

/*
 *  Class responsible for rendering the current game state
 */

import states.State;

public class Updater extends Loop {

    private final double width, height;
    private State state;

    public Updater(ThreadManager threadManager, double MAX_TPS, double width, double height) {
        super(threadManager, MAX_TPS, "UPDATER");
        this.width = width;
        this.height = height;
        threadManager.setUpdater(this);
    }

    @Override
    public void init() {
        threadManager.init(width, height);
    }

    @Override
    protected void loop() {
        if (state != null) state.update();
        threadManager.intermediateCode();
        threadManager.update();
    }
}
