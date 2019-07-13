package core.threads;

/*
 *  Class responsible for rendering the current game state
 */

public class Renderer extends Loop {

    public Renderer(ThreadManager threadManager, int MAX_TPS) {
        super(threadManager, MAX_TPS, "RENDERER");
        threadManager.setRenderer(this);
    }

    @Override
    public void init() {
    }

    @Override
    protected void loop() {
        threadManager.intermediateCode();
        threadManager.render();
    }

}
