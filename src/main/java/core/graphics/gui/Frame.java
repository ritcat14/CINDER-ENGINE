package core.graphics.gui;

import core.graphics.Window;
import core.objects.Object;
import files.ImageTools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame extends Object {

    private final double life;

    private final String fileName;

    private BufferedImage image;

    private int time = 0;

    private boolean alive = true;

    public Frame(double life, String fileName) {
        this.life = life;
        this.fileName = fileName;
    }

    @Override
    public void init() {
        image = ImageTools.getImage(fileName);
        super.init();
    }

    public void redraw() {
        alive = true;
    }

    @Override
    public void update() {
        if (alive) {
            time++;
            if (time % (120 * life) == 0) alive = false;
        }
    }

    @Override
    public void render(Graphics graphics) {
        if (alive) graphics.drawImage(image, 0, 0,
                (int) Window.getWindowWidth(), (int) Window.getWindowHeight(), null);
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void remove() {
        super.remove();
        image = null;
    }
}