package core.graphics.gui;

import core.graphics.PixelRenderer;
import core.objects.Point;
import core.objects.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GuiPanel extends GuiComponent {

    private Color color;
    private BufferedImage image;

    public GuiPanel(Rectangle rectangle, Color color) {
        super(rectangle);
        this.color = color;
    }

    public GuiPanel(Rectangle rectangle, BufferedImage image) {
        this(rectangle, Color.RED);
        this.image = image;
    }

    public GuiPanel(Point point, BufferedImage image) {
        this(new Rectangle(point.getX(), point.getY(), image.getWidth(), image.getHeight()), image);
    }

    @Override
    public void render(PixelRenderer pixelRenderer) {
        if (!visible || isRemoved()) return;
        if (color != null)
            pixelRenderer.fillRectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), color);
        if (image != null) {
            pixelRenderer.renderImage(image, bounds.getX(), bounds.getY());
        }
        super.render(pixelRenderer);
    }

    public BufferedImage getImage() {
        return image;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
