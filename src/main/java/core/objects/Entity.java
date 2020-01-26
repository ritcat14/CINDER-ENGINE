package core.objects;

import java.awt.*;

public abstract class Entity extends Object {

    protected double x, y;
    protected double width, height;

    public Entity(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }
}
