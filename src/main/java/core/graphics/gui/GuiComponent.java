package core.graphics.gui;

import core.events.Event;
import core.events.EventListener;
import core.graphics.PixelRenderer;
import core.objects.Object;

import java.awt.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class GuiComponent extends Object implements EventListener {

    protected double x, y, width, height;
    protected Rectangle bounds;
    protected boolean visible = true;

    private ConcurrentLinkedQueue<GuiComponent> components;
    private ConcurrentLinkedQueue<GuiComponent> initialisedComponents;

    public GuiComponent(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle((int)x, (int)y, (int)width, (int)height);
        components = new ConcurrentLinkedQueue<>();
        initialisedComponents = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void init() {
        if (!visible) return;
        super.init();

        Iterator<GuiComponent> it = components.iterator();
        while (it.hasNext()) it.next().init();
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
        if (!visible) return;
        Iterator<GuiComponent> it = components.iterator();
        while (it.hasNext()) {
            GuiComponent component = it.next();
            if (!component.isInitialised()) continue;
            initialisedComponents.add(component);
            it.remove();
        }

        it = initialisedComponents.iterator();
        while (it.hasNext()) it.next().update();
    }

    @Override
    public void remove() {
        if (!visible) return;

        super.remove();

        Iterator<GuiComponent> it = initialisedComponents.iterator();
        while (it.hasNext()) it.next().remove();

        it = components.iterator();
        while (it.hasNext()) it.next().remove();

        initialisedComponents.clear();
        components.clear();
    }

    @Override
    public void render(PixelRenderer pixelRenderer) {
        if (!visible) return;
        bounds.setBounds((int) x, (int) y, (int) width, (int) height);
        for (GuiComponent initialisedComponent : initialisedComponents) initialisedComponent.render(pixelRenderer);
    }

    @Override
    public void onEvent(Event event) {
        if (!visible) return;

        Iterator<GuiComponent> it = initialisedComponents.iterator();
        while (it.hasNext()) it.next().onEvent(event);
    }

    public void toggleVisible() {
        this.visible = !visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
