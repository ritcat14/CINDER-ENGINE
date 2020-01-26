package core.graphics;

/*
 *  Main window class
 */

import core.objectManagers.StateManager;
import files.ImageTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window extends Canvas implements WindowListener {

    private static double width;
    private static double height;

    private boolean shouldClose;
    private volatile Graphics2D graphics2D;
    private volatile JFrame frame;
    private volatile BufferedImage currentFrame;
    private volatile BufferedImage blurredFrame;
    private volatile boolean isBlurred = false;
    private volatile boolean requestedBlur = false;

    public Window(double width, double height) {
        Window.width = width;
        Window.height = height;
        currentFrame = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension((int) width, (int) height));
        requestFocus();

        frame = new JFrame();
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.add(this);

        frame.pack();
        frame.addWindowListener(this);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.requestFocus();
    }

    public synchronized void update() {
        //if (graphics2D != null) update(graphics2D);
    }

    public synchronized void render(StateManager stateManager) {
        requestFocus();
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        graphics2D = (Graphics2D) currentFrame.getGraphics();
        graphics2D.setColor(Color.GRAY);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        stateManager.render(graphics2D);
        bs.getDrawGraphics().drawImage(currentFrame, 0, 0, getWidth(), getHeight(), null);
        graphics2D.dispose();
        bs.show();
    }

    public BufferedImage getCurrentFrame() {
        if (requestedBlur && !isBlurred) {
            isBlurred = true;
            requestedBlur = false;
            return (blurredFrame = ImageTools.blur(currentFrame));
        } else if (isBlurred) return blurredFrame;
        else return currentFrame;
    }

    public synchronized boolean shouldClose() {
        return shouldClose;
    }

    public static double getWindowWidth() {
        return width;
    }

    public static double getWindowHeight() {
        return height;
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        shouldClose = true;
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {
    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {
    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {
    }
}
