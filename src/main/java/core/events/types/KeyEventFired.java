package core.events.types;

import core.events.Event;

import java.awt.event.KeyEvent;

public class KeyEventFired extends Event {

    private int key;

    public KeyEventFired(Type type, KeyEvent event) {
        super(type);
        this.key = event.getKeyCode();
    }

    public int getKey() {
        return key;
    }
}
