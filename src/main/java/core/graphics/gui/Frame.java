package core.graphics.gui;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Frame {

    private BufferedImage image;
    private final int life;
    private int currentLife;
    private int time = 0;
    private boolean alive = true;

    public Frame(String data, BufferedImage image) {
        String[] parts = data.split("|");
        this.life = Integer.parseInt(parts[0]);
        this.image = image;
    }

    public void redraw() {
        alive = true;
        currentLife = life;
    }

    public void update() {
        alive = currentLife > 0;
        if (alive) {
            time++;
            if (time % 60 == 0) currentLife--;
            if (life <= 0) alive = false;
        }
    }

    public void render(Graphics graphics) {
        if (alive) {
            graphics.drawImage(image, 0, 0, null);
        }
    }

    public boolean isAlive() {
        return alive;
    }


}