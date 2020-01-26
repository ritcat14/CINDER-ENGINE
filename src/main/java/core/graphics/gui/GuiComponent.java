package core.graphics.gui;

import core.objects.Object;

import java.awt.*;

public abstract class GuiComponent extends Object {

    protected double x, y, width, height;

    public GuiComponent(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.fillRect((int) x, (int) y, (int) width, (int) height);
    }
}
