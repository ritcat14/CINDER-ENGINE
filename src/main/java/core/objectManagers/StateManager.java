package core.objectManagers;

import core.objects.Object;
import core.states.State;
import core.threads.ThreadManager;

import java.awt.*;

public class StateManager extends ObjectManager {

    private ObjectManager objectManager;

    private volatile String currentStateName;
    private volatile State currentState;

    private ThreadManager threadManager;

    public StateManager(ThreadManager threadManager) {
        this.threadManager = threadManager;
        objectManager = new ObjectManager();
    }

    public synchronized void setCurrentState(String stateName) {
        if (currentStateName != null && currentStateName.equals(stateName)) return;
        for (Object sharedObject : getSharedObjects()) {
            State state1 = ((State) sharedObject);
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
        System.out.println("Setting state to: " + state.getStateName());
        currentState = state;
        currentState.setObjectManager(objectManager);
        currentState.init();
    }

    public synchronized void addState(State state) {
        super.addObject(state);
    }

    @Override
    public void update() {
        if (currentState == null) return;
        if (objectManager.getSharedResources().size() > 0) {
            threadManager.addResource(objectManager.getSharedResources().iterator().next());
        }/*
        if (currentState.isRemoved()) {
            removeState(currentState);

            Iterator<Object> it = sharedObjects.iterator();
            State state1;
            if (it.hasNext()) {
                state1 = ((State) it.next());
                setCurrentState(state1.getStateName());
            } else {
                System.out.println("No states left, closing application");
                CinderEngine.CLOSE();
            }
        }*/
        if (currentState.hasRequestedChange()) setCurrentState(currentState.getRequestedState());
        currentState.update();
    }

    @Override
    public void render(Graphics graphics) {
        if (currentState == null) return;
        if (currentState.hasRequestedChange()) return;
        currentState.render(graphics);
    }

    @Override
    @Deprecated
    public synchronized void addObject(Object object) {
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }
}
