package temp;

import core.graphics.Window;
import core.objects.Entity;
import files.ImageTools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TestObject extends Entity {

    private static int initCounter = 0;
    private int id;

    private BufferedImage image;

    public TestObject(int id) {
        super(100, 100, 50, 50);
        this.id = id;
        dir = new Random().nextInt(4);
    }

    @Override
    public void init() {
        System.out.println("Object " + id + ": " + initCounter + "  initialised.");
        initCounter++;
        image = ImageTools.getImage("images/d0.png");
        super.init();
    }

    @Override
    public void update() {
        switch (dir) {
            case 0:
                setY(getY() - 1);
                break;
            case 1:
                setX(getX() + 1);
                break;
            case 2:
                setY(getY() + 1);
                break;
            case 3:
                setX(getX() - 1);
                break;
        }
        if (getX() < 0 || getY() < 0 ||
                getX() > Window.getWindowWidth() - getWidth() || getY() > Window.getWindowHeight() - getHeight())
            remove();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.BLUE);
        super.render(graphics);
        graphics.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
    }
}
