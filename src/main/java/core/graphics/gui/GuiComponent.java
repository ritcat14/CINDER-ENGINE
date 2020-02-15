package core.graphics.gui;

import core.events.Event;
import core.events.EventListener;
import core.objects.Object;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GuiComponent extends Object implements EventListener {

    protected double x, y, width, height;
    protected Rectangle bounds;

    private List<GuiComponent> components;
    private List<GuiComponent> initialisedComponents;

    public GuiComponent(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle((int)x, (int)y, (int)width, (int)height);
        components = new ArrayList<>();
        initialisedComponents = new ArrayList<>();
    }

    @Override
    public void init() {
        super.init();
        for (GuiComponent component : components) {
            component.init();
        }
    }

    public void addComponent(GuiComponent component) {
        components.add(component);
    }

    public void removeComponent(GuiComponent component) {
        components.remove(component);
    }

    public boolean contains(Point point) {
        return bounds.contains(point);
    }

    @Override
    public void update() {
        for (GuiComponent component : components) {
            if (!component.isInitialised()) component.init();
        }
        initialisedComponents.addAll(components);
        components.clear();
        for (GuiComponent component : initialisedComponents) component.update();
    }

    @Override
    public void remove() {
        super.remove();
        for (GuiComponent component : initialisedComponents) {
            component.remove();
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.fillRect((int) x, (int) y, (int) width, (int) height);
        bounds.setBounds((int)x, (int)y, (int)width, (int)height);
        for (GuiComponent component : initialisedComponents) component.render(graphics);
    }

    @Override
    public void onEvent(Event event) {
        for (GuiComponent component : initialisedComponents) component.onEvent(event);
    }
}
