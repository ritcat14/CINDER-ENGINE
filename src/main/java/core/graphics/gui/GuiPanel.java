package core.graphics.gui;

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

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(color);
        super.render(graphics);
        if (image != null) {
            graphics.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
        }
    }
}
