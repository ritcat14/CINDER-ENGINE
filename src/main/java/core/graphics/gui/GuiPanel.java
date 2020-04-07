package core.graphics.gui;

import core.graphics.Renderer;
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
        super(rectangle);
        this.image = image;
    }

    public GuiPanel(Point point, BufferedImage image) {
        this(new Rectangle(point.getX(), point.getY(), image.getWidth(), image.getHeight()), image);
    }

    @Override
    public void render(Renderer renderer) {
        if (!visible || isRemoved()) return;
        if (color != null)
            renderer.fillRectangle(bounds, color);
        if (image != null) {
            renderer.drawImage(image, bounds);
        }
        super.render(renderer);
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
