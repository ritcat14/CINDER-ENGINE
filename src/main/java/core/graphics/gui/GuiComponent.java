package core.graphics.gui;

import core.events.Event;
import core.events.EventListener;
import core.graphics.Renderer;
import core.objects.Object;
import core.objects.Point;
import core.objects.Rectangle;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class GuiComponent extends Object implements EventListener {

    protected Rectangle bounds;
    protected boolean visible = true;

    private ConcurrentLinkedQueue<GuiComponent> components;
    private ConcurrentLinkedQueue<GuiComponent> initialisedComponents;

    public GuiComponent(Rectangle rectangle) {
        this.bounds = rectangle;
        components = new ConcurrentLinkedQueue<>();
        initialisedComponents = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void init() {
        if (!visible || isRemoved()) return;
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
        if (!visible || isRemoved()) return;
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
    public void render(Renderer renderer) {
        if (!visible || isRemoved()) return;
        for (GuiComponent initialisedComponent : initialisedComponents) initialisedComponent.render(renderer);
    }

    @Override
    public void onEvent(Event event) {
        if (!visible || isRemoved()) return;

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

    public Rectangle getBounds() {
        return bounds;
    }
}
