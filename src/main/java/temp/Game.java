package temp;

import core.objectManagers.EntityManager;
import core.objectManagers.StateManager;
import states.State;

public class Game extends State {

    public Game() {
        super(new EntityManager(), StateManager.States.GAME);
    }

    @Override
    public void init() {
        for (int i = 0; i < 10; i++) objectManager.addObject(new TestObject());
    }

}
