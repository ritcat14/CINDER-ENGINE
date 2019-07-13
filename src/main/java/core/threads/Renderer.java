package core.threads;

/*
 *  Class responsible for rendering the current game state
 */

import states.State;

public class Renderer extends Loop {

    private State state;

    public Renderer(ThreadManager threadManager, int MAX_TPS) {
        super(threadManager, MAX_TPS, "RENDERER");
        threadManager.setRenderer(this);
    }

    @Override
    public void init() {
    }

    @Override
    protected void loop() {
        if (state != null) state.render();
        threadManager.intermediateCode();
        threadManager.render();
    }

}
