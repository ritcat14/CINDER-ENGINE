package temp;

import core.graphics.Window;
import core.objects.Entity;

import java.awt.*;
import java.util.Random;

public class TestObject extends Entity {

    private static int initCounter = 0;
    private int id, dir;

    public TestObject(int id) {
        super(100, 100, 50, 50);
        this.id = id;
        dir = new Random().nextInt(4);
    }

    @Override
    public void init() {
        super.init();
        System.out.println("Object " + id + ": " + initCounter + "  initialised.");
        initCounter++;
    }

    @Override
    public void update() {
        switch (dir) {
            case 0:
                y--;
                break;
            case 1:
                x++;
                break;
            case 2:
                y++;
                break;
            case 3:
                x--;
                break;
        }
        if (x < 0 || y < 0 || x > Window.getWindowWidth() - width || y > Window.getWindowHeight() - height) remove();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.BLUE);
        super.render(graphics);
    }
}
