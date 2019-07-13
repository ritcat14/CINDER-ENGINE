package core.threads;

/*
 *  Class responsible for rendering the current game state
 */

import core.graphics.Window;
import states.State;

public class Renderer extends Loop {

    private final double width, height;
    private State state;
    private Window window;

    public Renderer(ThreadManager threadManager, int MAX_TPS, double width, double height) {
        super(threadManager, MAX_TPS, "RENDERER");
        this.width = width;
        this.height = height;
    }

    @Override
    public void init() {
        window = new Window(width, height);
        System.out.println("init renderer");
    }

    @Override
    protected void loop() {
        window.updateThread();
        threadManager.intermediateCode();
        window.setTitle(threadManager.getTPS());
        if (state != null) state.render();
        window.render();
        window.nullThread();
    }

}
