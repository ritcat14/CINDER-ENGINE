package core.objectManagers;

import core.events.Event;
import core.events.EventListener;
import core.graphics.Renderer;
import core.sout.LogType;
import core.sout.Logger;
import core.states.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class StateManager implements EventListener {

    private volatile String currentStateName;
    private volatile State currentState;

    private final List<State> states = Collections.synchronizedList(new ArrayList<>());

    public synchronized void setCurrentState(String stateName) {
        if (currentStateName != null && currentStateName.equals(stateName)) return;
        for (State state : states) {
            if (state.getStateName().equals(stateName)) {
                if (currentState != null) {
                    currentState.cleanUp();
                    if (currentState.hasRequestedChange()) {
                        State preState = currentState;
                        changeState(state);
                        preState.changed();
                        return;
                    }
                }
                currentStateName = stateName;
                changeState(state);
            }
        }
    }

    private void changeState(State state) {
        Logger.PRINT(LogType.INFO, "Setting state to: " + state.getStateName());
        currentState = state;
        currentState.init();
    }

    public synchronized void addState(State state) {
        state.setStateManager(this);
        states.add(state);
    }

    public synchronized State getState(String stateName) {
        synchronized (states) {
            Iterator<State> iterator = states.iterator();
            while (iterator.hasNext()) {
                State state = iterator.next();
                if (state.getStateName().equals(stateName)) return state;
            }
        }
        Logger.PRINT_ERROR(new NullPointerException(), "Failed to find state: " + stateName, true);
        return null;
    }

    public void update() {
        if (currentState == null) return;
        /*
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

    public void render(Renderer renderer) {
        if (currentState == null) return;
        if (currentState.hasRequestedChange()) return;
        currentState.render(renderer);
    }

    public void renderGUI(Renderer renderer) {
        if (currentState == null) return;
        if (currentState.hasRequestedChange()) return;
        currentState.renderGui(renderer);
    }

    @Override
    public void onEvent(Event event) {
        if (currentState != null) currentState.onEvent(event);
    }

    public void cleanUp() {
        currentState.cleanUp();
        //TODO: Clear lists
    }
}
