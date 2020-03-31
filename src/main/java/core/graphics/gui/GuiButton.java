package core.graphics.gui;

import core.events.Event;
import core.events.EventDispatcher;
import core.events.types.MouseEventFired;
import core.objects.Point;
import core.objects.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GuiButton extends GuiPanel {

    private BufferedImage background, foreground;
    private Color backgroundColour, foregroundColour;

    public GuiButton(Rectangle rectangle, Color backgroundColour) {
        super(rectangle, backgroundColour);
        this.backgroundColour = backgroundColour;
    }

    public GuiButton(Rectangle rectangle, Color backgroundColour, Color foregroundColour) {
        super(rectangle, backgroundColour);
        this.backgroundColour = backgroundColour;
        this.foregroundColour = foregroundColour;
    }

    public GuiButton(Point point, BufferedImage background) {
        super(point, background);
    }

    public GuiButton(Point point, BufferedImage background, BufferedImage foreground) {
        super(point, background);
        this.background = background;
        this.foreground = foreground;
    }

    public GuiButton(Rectangle rectangle, BufferedImage background) {
        super(rectangle, background);
        this.background = background;
    }

    public GuiButton(Rectangle rectangle, BufferedImage background, BufferedImage foreground) {
        super(rectangle, background);
        this.background = background;
        this.foreground = foreground;
    }

    protected boolean mousePressed(MouseEventFired event) {
        return contains(event.getPosition());
    }

    protected boolean mouseReleased(MouseEventFired event) {
        return contains(event.getPosition());
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
        if (!visible || isRemoved()) return;
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> mousePressed((MouseEventFired) e));
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> mouseReleased((MouseEventFired) e));
        dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> mouseMoved((MouseEventFired) e));
    }

    public BufferedImage getBackground() {
        return background;
    }

    public BufferedImage getForeground() {
        return foreground;
    }

    public Color getBackgroundColour() {
        return backgroundColour;
    }

    public Color getForegroundColour() {
        return foregroundColour;
    }

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public void setForeground(BufferedImage foreground) {
        this.foreground = foreground;
    }

    public void setBackgroundColour(Color backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public void setForegroundColour(Color foregroundColour) {
        this.foregroundColour = foregroundColour;
    }
}
