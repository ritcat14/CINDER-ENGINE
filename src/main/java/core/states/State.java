package core.states;

/*
 *  Class that represents a game state
 */

import core.events.Event;
import core.events.EventListener;
import core.graphics.PixelRenderer;
import core.objectManagers.ObjectManager;
import core.objects.Object;

public abstract class State extends Object implements EventListener {

    private volatile boolean requestedChange = false;
    private volatile String requestedState = "";

    protected ObjectManager objectManager; // For allowing the state to handle it's own objects
    protected String stateName;

    public State(String stateName) {
        this.stateName = stateName;
    }

    protected abstract void eventFired(Event event);

    public void setObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    @Override
    public void update() {
        if (objectManager != null) objectManager.update();
    }

    @Override
    public void render(PixelRenderer pixelRenderer) {
        if (objectManager != null) objectManager.render(pixelRenderer);
    }

    public void requestChange(String stateName) {
        requestedChange = true;
        requestedState = stateName;
    }

    public synchronized void changed() {
        requestedChange = false;
        requestedState = "";
    }

    public ObjectManager getObjectManager() {
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
        if (objectManager != null) objectManager.cleanUp();
    }

    @Override
    public void onEvent(Event event) {
        eventFired(event);
        objectManager.onEvent(event);
    }
}
