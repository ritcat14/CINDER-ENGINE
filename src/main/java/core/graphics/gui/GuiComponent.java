package core.graphics.gui;

import core.events.Event;
import core.events.EventListener;
import core.graphics.Renderer;
import core.objects.Object;
import core.objects.Point;
import core.objects.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class GuiComponent extends Object implements EventListener {

    protected Rectangle bounds;
    protected boolean visible = true;


    private List<GuiComponent> components = Collections.synchronizedList(new ArrayList<>());

    public GuiComponent(Rectangle rectangle) {
        this.bounds = rectangle;
    }

    public void init() {
        if (!visible || isRemoved()) return;

        Iterator<GuiComponent> it = components.iterator();
        while (it.hasNext()) it.next().init();
    }

    public void addComponent(GuiComponent component) {
        synchronized (components) {
            components.add(component);
        }
    }

    public void removeComponent(GuiComponent component) {
        synchronized (components) {
            components.remove(component);
        }
    }

    public boolean contains(Point point) {
        return bounds.contains(point);
    }

    @Override
    public void update() {
        if (!visible || isRemoved()) return;

        synchronized (components) {
            Iterator<GuiComponent> iterator = components.iterator();
            while (iterator.hasNext()) {
                GuiComponent component = iterator.next();
                if (component.isRemoved()) iterator.remove();
                else component.update();
            }
        }
    }

    @Override
    public void remove() {
        if (!visible) return;
        super.remove();
        components.clear();
    }

    @Override
    public void render(Renderer renderer) {
        if (!visible || isRemoved()) return;

        synchronized (components) {
            Iterator<GuiComponent> iterator = components.iterator();
            while (iterator.hasNext()) {
                GuiComponent component = iterator.next();
                if (component.isRemoved()) iterator.remove();
                else component.render(renderer);
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        if (!visible || isRemoved()) return;


        synchronized (components) {
            Iterator<GuiComponent> iterator = components.iterator();
            while (iterator.hasNext()) {
                GuiComponent component = iterator.next();
                if (component.isRemoved()) iterator.remove();
                else component.onEvent(event);
            }
        }
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
