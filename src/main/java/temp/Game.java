package temp;

import core.objectManagers.ObjectManager;
import core.states.State;
import core.CinderEngine.RenderType;

import static org.lwjgl.opengl.GL11.glClearColor;

public class Game extends State {

    public Game(ObjectManager objectManager) {
        super(objectManager, "game");
    }

    @Override
    public void init(RenderType renderType) {
        System.out.println("Initiating game state");
        for (int i = 0; i < 10; i++) objectManager.addObject(new TestObject());
    }

    @Override
    public void render(RenderType renderType) {
        glClearColor(1.0f, 0f, 0f, 0.0f);
        super.render(renderType);
    }
}
