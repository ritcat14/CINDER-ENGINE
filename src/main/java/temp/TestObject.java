package temp;

import core.graphics.PixelRenderer;
import core.graphics.Window;
import core.objects.Entity;
import core.objects.Rectangle;
import files.ImageTools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TestObject extends Entity {

    private static int initCounter = 0;
    private int id;

    private BufferedImage image;

    public TestObject(int id) {
        super(new Rectangle(100, 100, 50, 50));
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
                bounds.setY(bounds.getY() - 1);
                break;
            case 1:
                bounds.setX(bounds.getX() + 1);
                break;
            case 2:
                bounds.setY(bounds.getY() + 1);
                break;
            case 3:
                bounds.setX(bounds.getX() - 1);
                break;
        }
        if (bounds.getX() < 0 || bounds.getY() < 0 ||
                bounds.getX() > Window.getWindowWidth() - bounds.getWidth() || bounds.getY() > Window.getWindowHeight() - bounds.getHeight())
            remove();
    }

    @Override
    public void render(PixelRenderer pixelRenderer) {
        pixelRenderer.fillRectangle(bounds, Color.BLUE);
        pixelRenderer.renderImage(image, bounds);
    }
}
