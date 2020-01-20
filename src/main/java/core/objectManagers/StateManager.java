package core.objectManagers;

import core.objects.Object;
import core.states.State;
import core.threads.ThreadManager;

public class StateManager extends ObjectManager {

    private volatile String currentStateName;
    private volatile State currentState;

    private ThreadManager threadManager;

    private boolean addedResources = false;
    private int time = 0, resourceCount = 0;

    public StateManager(ThreadManager threadManager) {
        this.threadManager = threadManager;
    }

    public synchronized void setCurrentState(String stateName) {
        if (currentStateName != null && currentStateName.equals(stateName)) return;
        for (Object sharedObject : sharedObjects) {
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
        currentState.init();
    }

    public synchronized void addState(State state) {
        super.addObject(state);
    }

    public synchronized void removeState(State state) {
        System.out.println("Removed state " + currentStateName);
        super.removeObject(state);
    }

    @Override
    public synchronized void init() {
        super.init();
        if (currentState == null) return;
        currentState.init();
    }

    @Override
    public void update() {
        if (currentState == null) return;
        System.out.println(currentState.getObjectManager().sharedObjects.size());
        if (!addedResources && resourceCount < currentState.getObjectManager().sharedResources.size() - 1) {
            time++;
            if (time % (120 * 10) == 0) {
                resourceCount++;
                threadManager.addResource(currentState.getObjectManager().sharedResources.get(resourceCount));
            }
        } else if (resourceCount + 1 == currentState.getObjectManager().sharedResources.size()) addedResources = true;
        /*if (currentState.isRemoved()) {
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
    public void render() {
        if (currentState == null) return;
        if (currentState.hasRequestedChange()) return;
        currentState.render();
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
