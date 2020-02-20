package core.graphics.gui;

import core.graphics.Window;
import core.objects.Object;
import files.ImageTools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame extends Object {

    private final int life;

    private final String fileName;

    private BufferedImage image;
    private BufferedImage blurredImage;

    private int currentLife;
    private int time = 0;

    private boolean alive = true;
    private boolean blurred = false;

    public Frame(String data) {
        String[] parts = data.split(":");
        this.life = Integer.parseInt(parts[0]);
        this.fileName = parts[1];
    }

    @Override
    public void init() {
        image = ImageTools.getImage(fileName);
        blurredImage = ImageTools.blur(image);
        super.init();
    }

    public void redraw() {
        alive = true;
        currentLife = life;
    }

    @Override
    public void update() {
        alive = currentLife > 0;
        if (alive) {
            time++;
            if (time % 60 == 0) currentLife--;
            if (life <= 0) alive = false;
        }
    }

    @Override
    public void render(Graphics graphics) {
        if (alive) {
            if (!blurred) graphics.drawImage(image, 0, 0,
                    (int) Window.getWindowWidth(), (int) Window.getWindowHeight(), null);
            else graphics.drawImage(blurredImage, 0, 0,
                    (int) Window.getWindowWidth(), (int) Window.getWindowHeight(), null);
        }
    }

    public void setBlurred(boolean blurred) {
        this.blurred = blurred;
    }

    public boolean isAlive() {
        return alive;
    }

}