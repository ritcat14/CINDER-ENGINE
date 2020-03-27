package core.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class PixelRenderer {

    private Graphics2D graphics2D;
    private int[] pixels;

    public PixelRenderer(BufferedImage currentFrame) {
        this.pixels = ((DataBufferInt) currentFrame.getRaster().getDataBuffer()).getData();
    }

    public void fillRectangle(double xpos, double ypos, double w, double h, Color color) {
        graphics2D.setColor(color);
        graphics2D.fillRect((int) xpos, (int) ypos, (int) w, (int) h);
    }

    public void fillRectangle(Rectangle rectangle, Color color) {
        fillRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height, color);
    }

    public void drawRectangle(double x, double y, double w, double h, Color color) {
        Color pre = graphics2D.getColor();
        graphics2D.setColor(color);
        graphics2D.drawRect((int) x, (int) y, (int) w, (int) h);
        graphics2D.setColor(pre);
    }

    public void drawRectangle(Rectangle rectangle, Color color) {
        drawRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height, color);
    }

    public void renderImage(BufferedImage image, double xpos, double ypos, double width, double height) {
        graphics2D.drawImage(image, (int) xpos, (int) ypos, (int) width, (int) height, null);
    }

    public void renderImage(BufferedImage image, double xpos, double ypos) {
        graphics2D.drawImage(image, (int) xpos, (int) ypos, null);
    }

    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }
}
