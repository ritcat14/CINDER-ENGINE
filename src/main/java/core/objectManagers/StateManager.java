package core.objectManagers;

import core.objects.Object;
import core.states.State;
import core.threads.ThreadManager;
import core.CinderEngine.RenderType;

import java.util.Iterator;

public class StateManager extends ObjectManager {

    private volatile String currentStateName;
    private volatile State currentState;

    private ThreadManager threadManager;

    public StateManager(ThreadManager threadManager) {
        this.threadManager = threadManager;
    }

    public synchronized void setCurrentState(String stateName) {
        if (currentStateName != null && currentStateName.equals(stateName)) return;
        Iterator<Object> it = sharedObjects.iterator();
        while (it.hasNext()) {
            State state1 = ((State) it.next());
            if (state1.getStateName().equals(stateName)) {
                if (currentState != null) {
                    currentState.cleanUp();
                    if (currentState.hasRequestedChange()) {
                        State preState = currentState;
                        changeState(state1);
                        preState.changed();
                        return;
                    }
                }
                currentStateName = stateName;
                changeState(state1);
            }
        }
    }

    private void changeState(State state) {
        currentState = state;
        threadManager.addResource(currentState);
        threadManager.addResource(currentState.getObjectManager());
    }

    public synchronized void addState(State state) {
        super.addObject(state);
    }

    public synchronized void removeState(State state) {
        super.removeObject(state);
    }

    public void init() {
        if (currentState == null) return;
        currentState.init();
    }

    @Override
    public void update() {
        if (currentState == null) return;
        if (currentState.hasRequestedChange()) setCurrentState(currentState.getRequestedState());
        currentState.update();
    }

    @Override
    public void render(RenderType renderType) {
        if (currentState == null) return;
        if (currentState.hasRequestedChange()) return;
        currentState.render(renderType);
    }

    @Override
    @Deprecated
    public synchronized void addObject(Object object) {
    }

    @Override
    @Deprecated
    public synchronized void removeObject(Object object) {
    }
}
