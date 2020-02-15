package core.graphics.gui;

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
}
