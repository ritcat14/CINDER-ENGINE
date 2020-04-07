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

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import java.util.*;

public class Window extends Canvas implements WindowListener {

    private static double width;
    private static double height;

    private boolean shouldClose;
    private volatile Graphics2D graphics2D;
    private volatile JFrame frame;
    private volatile BufferedImage currentFrame, guiFrame;
    private volatile BufferedImage blurredFrame;
    private volatile Renderer renderer;
    private volatile boolean isBlurred = false;
    private volatile boolean requestedBlur = false;

    private static volatile List<String> fontsToRegister = new ArrayList<>();
    public static Map<String, Font> registeredFonts = new HashMap<>();

    public Window(double width, double height, EventListener eventListener) {
        Window.width = width;
        Window.height = height;
        currentFrame = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_4BYTE_ABGR);
        guiFrame = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_4BYTE_ABGR);
        renderer = new Renderer();
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

    public static synchronized void registerFont(String name) {
        fontsToRegister.add(name);
    }

    public synchronized void update() {
        String path = "";
        String name = "";
        InputStream inputStream;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Iterator it = fontsToRegister.iterator();
            while (it.hasNext()) {
                name = (String) it.next();
                path = "fonts/" + name + ".ttf";
                inputStream = ClassLoader.getSystemResourceAsStream(path);
                Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream));
                ge.registerFont(font);
                registeredFonts.put(name, font);
                inputStream.close();
                it.remove();
            }
        } catch (Exception e) {
            Logger.PRINT_ERROR(e, "Failed to register font " + path, true);
        }
    }

    public synchronized void render(StateManager stateManager) {
        requestFocus();
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        // GAME

        currentFrame = Renderer.convertImage(currentFrame);

        graphics2D = (Graphics2D) currentFrame.getGraphics();
        renderer.setGraphics2D(graphics2D);
        graphics2D.setColor(Color.GRAY);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        stateManager.render(renderer);
        int imageWidth = currentFrame.getWidth() * Renderer.getSCALE();
        int imageHeight = currentFrame.getHeight() * Renderer.getSCALE();
        int widthDiff = imageWidth - getWidth();
        int heightDiff = imageHeight - getHeight();
        bs.getDrawGraphics().drawImage(currentFrame, -(widthDiff / 2), -(heightDiff / 2),
                imageWidth, imageHeight, null);
        graphics2D.dispose();

        // GUI

        guiFrame = Renderer.convertImage(guiFrame);

        graphics2D = (Graphics2D) guiFrame.getGraphics();
        renderer.setGraphics2D(graphics2D);
        graphics2D.setBackground(new Color(255, 255, 255, 0));
        graphics2D.clearRect(0, 0, getWidth(), getHeight());
        stateManager.renderGUI(renderer);
        bs.getDrawGraphics().drawImage(guiFrame, 0, 0, getWidth(), getHeight(), null);
        graphics2D.dispose();

        bs.show();
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
