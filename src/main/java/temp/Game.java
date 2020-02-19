package temp;

import core.graphics.Window;
import core.states.State;

import java.awt.*;


public class Game extends State {

    public Game() {
        super("game");
    }

    @Override
    public void init() {
        super.init();
        System.out.println("Initiating game state");
        //objectManager.addResource(new GuiPanel(0, 0, 200, Window.getWindowHeight(), Color.GRAY));
        for (int i = 0; i < 4; i++) objectManager.addResource(new TestObject(i));
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.CYAN);
        graphics.fillRect(0, 0, (int) Window.getWindowWidth(), (int) Window.getWindowHeight());
        super.render(graphics);
    }
}
