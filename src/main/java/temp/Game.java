package temp;

import core.events.Event;
import core.graphics.PixelRenderer;
import core.graphics.Window;
import core.states.State;

import java.awt.*;


public class Game extends State {

    public Game() {
        super("game");
    }

    @Override
    public void init() {
        System.out.println("Initiating game state");
        //objectManager.addResource(new GuiPanel(0, 0, 200, Window.getWindowHeight(), Color.GRAY));
        for (int i = 0; i < 4; i++) objectManager.addResource(new TestObject(i));
        super.init();
    }

    @Override
    protected void eventFired(Event event) {

    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(PixelRenderer pixelRenderer) {
        pixelRenderer.fillRectangle(0, 0, Window.getWindowWidth(), Window.getWindowHeight(), Color.CYAN);
        super.render(pixelRenderer);
    }
}
