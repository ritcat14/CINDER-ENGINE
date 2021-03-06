package core.graphics;

import core.objects.Point;
import core.objects.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer {

    private static int SCALE = 1;

    private Graphics2D graphics2D;

    public void fillRectangle(double xpos, double ypos, double w, double h, Color color) {
        graphics2D.setColor(color);
        graphics2D.fillRect((int) xpos, (int) ypos, (int) w, (int) h);
    }

    public void fillRectangle(Rectangle rectangle, Color color) {
        fillRectangle(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), color);
    }

    public void drawRectangle(double x, double y, double w, double h, Color color) {
        Color pre = graphics2D.getColor();
        graphics2D.setColor(color);
        graphics2D.drawRect((int) x, (int) y, (int) w, (int) h);
        graphics2D.setColor(pre);
    }

    public void drawRectangle(Rectangle rectangle, Color color) {
        drawRectangle(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), color);
    }

    public void drawImage(BufferedImage image, double xpos, double ypos, double width, double height) {
        graphics2D.drawImage(image, (int) xpos, (int) ypos, (int) width, (int) height, null);
    }

    public void drawImage(BufferedImage image, Rectangle rectangle) {
        graphics2D.drawImage(image, (int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
    }

    public void drawImage(BufferedImage image, double xpos, double ypos) {
        graphics2D.drawImage(image, (int) xpos, (int) ypos, null);
    }

    public void drawImage(BufferedImage image, Point point) {
        graphics2D.drawImage(image, (int) point.getX(), (int) point.getY(), null);
    }

    public void drawString(Point point, String text, Color color) {
        Color pre = graphics2D.getColor();
        graphics2D.setColor(color);
        graphics2D.drawString(text, (int) point.getX(), (int) point.getY());
        graphics2D.setColor(pre);
    }

    public void drawString(Point point, String text, Color color, Font font) {
        Font pre = graphics2D.getFont();
        graphics2D.setFont(font);
        drawString(point, text, color);
        graphics2D.setFont(pre);
    }

    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    public static BufferedImage convertImage(BufferedImage image) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage convertedImage = gc.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
        return convertedImage;
    }

    public static int getSCALE() {
        return SCALE;
    }

    public static void setSCALE(int SCALE) {
        Renderer.SCALE = SCALE;
    }

    public static void hardwareAcceleration(boolean enable) {
        System.setProperty("sun.java2d.opengl", enable ? "true" : "false");
    }

}
