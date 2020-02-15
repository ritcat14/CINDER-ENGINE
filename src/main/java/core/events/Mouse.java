package core.events;

import core.events.types.MouseEventFired;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static core.events.Event.Type.*;

public class Mouse implements MouseListener, MouseMotionListener {

    private EventListener eventListener;

    public Mouse(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        eventListener.onEvent(new MouseEventFired(MOUSE_PRESSED, mouseEvent));
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        eventListener.onEvent(new MouseEventFired(MOUSE_RELEASED, mouseEvent));
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        eventListener.onEvent(new MouseEventFired(MOUSE_DRAGGED, mouseEvent));
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        eventListener.onEvent(new MouseEventFired(MOUSE_MOVED, mouseEvent));
    }
}
