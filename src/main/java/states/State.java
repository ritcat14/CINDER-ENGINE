package states;

/*
 *  Class that represents a game state
 */

public abstract class State {

    public abstract void init();

    public abstract void update();

    public abstract void preRender();

    public abstract void render();

    public abstract void postRender();

}
