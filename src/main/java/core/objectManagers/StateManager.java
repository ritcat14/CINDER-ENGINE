package core.objectManagers;

import core.objects.SharedObject;
import states.State;

import java.util.Iterator;

public class StateManager extends ObjectManager {

    public enum States {
        START, GAME, PAUSE
    }

    private volatile States currentStateEnum;
    private volatile State currentState;

    public synchronized void setCurrentState(States state) {
        if (currentStateEnum != null && currentStateEnum.equals(state)) return;
        Iterator<SharedObject> it = sharedObjects.iterator();
        while (it.hasNext()) {
            State state1 = ((State) it.next());
            if (state1.getStateName().equals(state)) {
                if (currentState != null) currentState.cleanUp();
                currentStateEnum = state;
                currentState = state1;
                currentState.init();
            }
        }
    }

    public synchronized void addState(State state) {
        super.addObject(state);
    }

    public synchronized void removeState(State state) {
        super.removeObject(state);
    }

    @Override
    public synchronized void intermediateCode() {
        if (currentState == null) return;
        currentState.intermediateCode();
    }

    @Override
    public void update() {
        if (currentState == null) return;
        currentState.update();
    }

    @Override
    public void render() {
        if (currentState == null) return;
        currentState.render();
    }

    @Override
    protected void onUpdate() {
    }

    @Override
    protected void onRender() {
    }

    @Override
    @Deprecated
    public synchronized void addObject(SharedObject object) {
    }

    @Override
    @Deprecated
    public synchronized void removeObject(SharedObject object) {
    }
}
