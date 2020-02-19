package core.objects;

import java.awt.*;

public abstract class Entity extends Object {

    protected volatile double x, y;
    protected volatile double width, height;
    protected volatile int dir;

    public Entity(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public synchronized void render(Graphics graphics) {
        graphics.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public synchronized double getX() {
        return x;
    }

    public synchronized void setX(double x) {
        this.x = x;
    }

    public synchronized double getY() {
        return y;
    }

    public synchronized void setY(double y) {
        this.y = y;
    }

    public synchronized double getWidth() {
        return width;
    }

    public synchronized void setWidth(double width) {
        this.width = width;
    }

    public synchronized double getHeight() {
        return height;
    }

    public synchronized void setHeight(double height) {
        this.height = height;
    }
}
