package core.events.types;

import core.events.Event;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseEventFired extends Event {

    private Point position;
    private int button;

    public MouseEventFired(Type type, MouseEvent event) {
        super(type);
        this.button = event.getButton();
        this.position = event.getLocationOnScreen();
    }

    public int getButton() {
        return button;
    }

    public Point getPosition() {
        return position;
    }
}
