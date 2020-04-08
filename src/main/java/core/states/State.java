package core.states;

/*
 *  Class that represents a game state
 */

import core.events.Event;
import core.events.EventListener;
import core.graphics.Renderer;
import core.objectManagers.StateManager;
import core.objectManagers.TaskLoader;

public abstract class State implements EventListener {

    protected volatile StateManager stateManager;

    private volatile boolean requestedChange = false;
    private volatile String requestedState = "";

    protected volatile TaskLoader taskLoader;

    protected String stateName;

    public State(String stateName) {
        this.stateName = stateName;
        this.taskLoader = new TaskLoader();
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public abstract void init();

    protected abstract void eventFired(Event event);

    public abstract void update();

    public abstract void render(Renderer renderer);

    public abstract void renderGui(Renderer renderer);

    public void requestChange(String stateName) {
        requestedChange = true;
        requestedState = stateName;
    }

    public synchronized void changed() {
        requestedChange = false;
        requestedState = "";
    }

    public TaskLoader getTaskLoader() {
        return taskLoader;
    }

    public synchronized boolean hasRequestedChange() {
        return requestedChange;
    }

    public synchronized String getRequestedState() {
        return requestedState;
    }

    public String getStateName() {
        return stateName;
    }

    public void cleanUp() {
        if (taskLoader != null) taskLoader.cleanUp();
    }

    @Override
    public void onEvent(Event event) {
        eventFired(event);
    }
}
