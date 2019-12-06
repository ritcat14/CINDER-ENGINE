package core.threads;

import core.CinderEngine.RenderType;

/*
 *  Class responsible for rendering the current game state
 */

public class Renderer extends Loop {

    public Renderer(ThreadManager threadManager, int MAX_TPS, RenderType renderType) {
        super(threadManager, MAX_TPS, "RENDERER", renderType);
    }

    @Override
    public void init() {
    }

    @Override
    public void onLoop() {
        threadManager.render(renderType);
    }

}
