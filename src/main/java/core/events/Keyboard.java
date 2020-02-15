package core.events;

import core.events.types.KeyEventFired;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static core.events.Event.Type.KEY_PRESSED;
import static core.events.Event.Type.KEY_RELEASED;

public class Keyboard implements KeyListener {

    private EventListener eventListener;

    public Keyboard(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        eventListener.onEvent(new KeyEventFired(KEY_PRESSED, keyEvent));
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        eventListener.onEvent(new KeyEventFired(KEY_RELEASED, keyEvent));
    }
}
