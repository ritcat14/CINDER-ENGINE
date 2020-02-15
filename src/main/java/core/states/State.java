package core.states;

/*
 *  Class that represents a game state
 */

import core.events.EventListener;
import core.objectManagers.ObjectManager;
import core.objects.Object;

import java.awt.*;

public abstract class State extends Object implements EventListener {

    private volatile boolean requestedChange = false;
    private volatile String requestedState = "";

    protected ObjectManager objectManager; // For allowing the state to handle it's own objects
    protected String stateName;

    public State(String stateName) {
        this.stateName = stateName;
    }

    public void setObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    @Override
    public void update() {
        if (objectManager != null) objectManager.update();
    }

    @Override
    public void render(Graphics graphics) {
        if (objectManager != null) objectManager.render(graphics);
    }

    public void requestChange(String stateName) {
        requestedChange = true;
        requestedState = stateName;
    }

    public synchronized void changed() {
        requestedChange = false;
        requestedState = "";
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
        if (objectManager != null) objectManager.cleanUp();
    }

}
