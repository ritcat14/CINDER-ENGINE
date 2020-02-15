package core.graphics.gui;

import core.events.Event;
import core.events.EventDispatcher;
import core.events.types.MouseEventFired;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GuiButton extends GuiPanel {

    private BufferedImage background, foreground;
    private Color backgroundColour, foregroundColour;

    public GuiButton(double x, double y, double width, double height, Color backgroundColour) {
        super(x, y, width, height, backgroundColour);
        this.backgroundColour = backgroundColour;
    }

    public GuiButton(double x, double y, double width, double height, Color backgroundColour, Color foregroundColour) {
        super(x, y, width, height, backgroundColour);
        this.backgroundColour = backgroundColour;
        this.foregroundColour = foregroundColour;
    }

    public GuiButton(double x, double y, double width, double height, BufferedImage background) {
        super(x, y, width, height, background);
        this.background = background;
    }

    public GuiButton(double x, double y, double width, double height, BufferedImage background, BufferedImage foreground) {
        super(x, y, width, height, background);
        this.background = background;
        this.foreground = foreground;
    }

    protected boolean mousePressed(MouseEventFired event) {
        if (contains(event.getPosition())) {
            return true;
        }
        return false;
    }

    protected boolean mouseReleased(MouseEventFired event) {
        if (contains(event.getPosition())) {
            return true;
        }
        return false;
    }

    protected boolean mouseMoved(MouseEventFired event) {
        if (contains(event.getPosition())) {
            if (foreground != null) setImage(foreground);
            else if (foregroundColour != null) setColor(foregroundColour);
            return true;
        }
        if (background != null) setImage(background);
        else setColor(backgroundColour);
        return false;
    }

    @Override
    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> mousePressed((MouseEventFired)e));
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> mouseReleased((MouseEventFired)e));
        dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> mouseMoved((MouseEventFired)e));
    }
}
