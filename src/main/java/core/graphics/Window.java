package core.graphics;

/*
 *  Main window class
 */

import core.events.EventListener;
import core.events.Keyboard;
import core.events.Mouse;
import core.objectManagers.StateManager;
import core.sout.LogType;
import core.sout.Logger;
import files.ImageTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.*;

public class Window extends Canvas implements WindowListener {

    private static double width;
    private static double height;

    private boolean shouldClose;
    private volatile Graphics2D graphics2D;
    private volatile JFrame frame;
    private volatile BufferedImage currentFrame;
    private volatile BufferedImage blurredFrame;
    private volatile PixelRenderer pixelRenderer;
    private volatile boolean isBlurred = false;
    private volatile boolean requestedBlur = false;

    private static volatile List<String> fontsToRegister = new ArrayList<>();
    public static Map<String, Font> registeredFonts = new HashMap<>();

    public Window(double width, double height, EventListener eventListener) {
        Window.width = width;
        Window.height = height;
        currentFrame = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_RGB);
        pixelRenderer = new PixelRenderer(currentFrame);
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

        addKeyListener(new Keyboard(eventListener));
        Mouse mouse = new Mouse(eventListener);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public static synchronized void registerFont(String name) throws URISyntaxException {
        fontsToRegister.add(name);
    }

    public synchronized void update() {
        String name = "";
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Iterator it = fontsToRegister.iterator();
            while (it.hasNext()) {
                name = (String) it.next();
                File file = new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader()
                        .getResource("fonts/" + name + ".ttf")).toURI());
                Font font = Font.createFont(Font.TRUETYPE_FONT, file);
                ge.registerFont(font);
                registeredFonts.put(name, font);
                it.remove();
            }
        } catch (Exception e) {
            Logger.PRINT_ERROR(e, "Failed to register font" + name);
        }
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
        pixelRenderer.setGraphics2D(graphics2D);
        stateManager.render(pixelRenderer);
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
    public void windowOpened(WindowEvent windowEvent) {}

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        shouldClose = true;
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {}

    @Override
    public void windowIconified(WindowEvent windowEvent) {}

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {}

    @Override
    public void windowActivated(WindowEvent windowEvent) {}

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {}

    public static void CLOSE() {
        Logger.PRINT(LogType.INFO, "Closing Game.");
        System.exit(0);
    }

}
