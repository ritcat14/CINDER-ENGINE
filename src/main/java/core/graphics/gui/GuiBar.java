package core.graphics.gui;

import core.graphics.PixelRenderer;
import core.objects.Point;
import core.objects.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GuiBar extends GuiPanel {

    private BufferedImage foregroundImage;
    private Color foregroundColour = Color.CYAN;
    private double maxValue;
    private double value;

    public GuiBar(Rectangle rectangle, Color color, Color foregroundColour,
                  double value, double maxValue) {
        super(rectangle, color);
        this.maxValue = maxValue;
        this.value = value;
        this.foregroundColour = foregroundColour;
    }

    public GuiBar(Rectangle rectangle, BufferedImage image, BufferedImage foregroundImage,
                  double value, double maxValue) {
        super(rectangle, image);
        this.maxValue = maxValue;
        this.value = value;
        this.foregroundImage = foregroundImage;
    }

    public GuiBar(Point point, BufferedImage image, BufferedImage foregroundImage,
                  double value, double maxValue) {
        super(point, image);
        this.maxValue = maxValue;
        this.value = value;
        this.foregroundImage = foregroundImage;
    }

    @Override
    public void render(PixelRenderer pixelRenderer) {
        if (!visible || isRemoved()) return;
        super.render(pixelRenderer);
        // calculate width of foreground
        double foreWidth = (value / maxValue) * (bounds.getWidth());
        if (foreWidth < 0) foreWidth = 1;
        pixelRenderer.fillRectangle(bounds.getX(), bounds.getY(), foreWidth, bounds.getHeight(), foregroundColour);

        if (foregroundImage != null) pixelRenderer.drawImage(foregroundImage, bounds.getX(), bounds.getY(),
                foreWidth, bounds.getHeight());
    }

    public void setForegroundColour(Color foregroundColour) {
        this.foregroundColour = foregroundColour;
    }

    public void setForegroundImage(BufferedImage foregroundImage) {
        this.foregroundImage = foregroundImage;
    }

    public Color getForegroundColour() {
        return foregroundColour;
    }

    public BufferedImage getForegroundImage() {
        return foregroundImage;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
}
