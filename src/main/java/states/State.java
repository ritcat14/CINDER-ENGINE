package states;

/*
 *  Class that represents a game state
 */

import core.objectManagers.ObjectManager;
import core.objectManagers.StateManager;
import core.objects.SharedObject;

public abstract class State extends SharedObject {

    protected ObjectManager objectManager;
    protected StateManager.States stateName;

    public State(ObjectManager objectManager, StateManager.States stateName) {
        this.objectManager = objectManager;
        this.stateName = stateName;
    }

    public abstract void init();

    @Override
    public void update() {
        objectManager.update();
    }

    @Override
    public void render() {
        objectManager.render();
    }

    @Override
    public synchronized void intermediateCode() {
        objectManager.intermediateCode();
    }

    public StateManager.States getStateName() {
        return stateName;
    }

    public void cleanUp() {
        objectManager.cleanUp();
    }

}
