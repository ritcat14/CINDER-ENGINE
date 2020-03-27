package core.graphics.gui;

import core.graphics.PixelRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GuiPanel extends GuiComponent {

    private Color color;
    private BufferedImage image;

    public GuiPanel(double x, double y, double width, double height, Color color) {
        super(x, y, width, height);
        this.color = color;
    }

    public GuiPanel(double x, double y, double width, double height, BufferedImage image) {
        this(x, y, width, height, Color.RED);
        this.image = image;
    }

    public GuiPanel(double x, double y, BufferedImage image) {
        this(x, y, image.getWidth(), image.getHeight(), image);
    }

    @Override
    public void render(PixelRenderer pixelRenderer) {
        pixelRenderer.fillRectangle(x, y, width, height, color);
        if (image != null) {
            pixelRenderer.renderImage(image, x, y);
        }
        super.render(pixelRenderer);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
