package core.events.types;

import core.events.Event;
import core.objects.Point;

import java.awt.event.MouseEvent;

public class MouseEventFired extends Event {

    private Point position;
    private int button;

    public MouseEventFired(Type type, MouseEvent event) {
        super(type);
        this.button = event.getButton();
        this.position = new Point(event.getLocationOnScreen().x, event.getLocationOnScreen().y);
    }

    public int getButton() {
        return button;
    }

    public Point getPosition() {
        return position;
    }
}
