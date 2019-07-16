package core.states;

/*
 *  Class that represents a game state
 */

import core.objectManagers.ObjectManager;
import core.objects.Object;

public abstract class State implements Object {

    private volatile boolean requestedChange = false;
    private volatile String requestedState = "";

    protected ObjectManager objectManager;
    protected String stateName;

    public State(ObjectManager objectManager, String stateName) {
        this.objectManager = objectManager;
        this.stateName = stateName;
    }

    @Override
    public void update() {
        objectManager.update();
    }

    @Override
    public void render() {
        objectManager.render();
    }

    public void requestChange(String stateName) {
        requestedChange = true;
        requestedState = stateName;
    }

    public synchronized void changed() {
        requestedChange = false;
        requestedState = "";
    }

    public synchronized ObjectManager getObjectManager() {
        return objectManager;
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
        objectManager.cleanUp();
    }

}
