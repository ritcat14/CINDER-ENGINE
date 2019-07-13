package states;

/*
 *  Class that represents a game state
 */

import core.graphics.gl.Shader;

public abstract class State {

    protected Shader shader;

    public State(Shader shader) {
        this.shader = shader;
    }

    public abstract void init();

    public abstract void update();

    public abstract void preRender();

    public abstract void render();

    public abstract void postRender();

}
