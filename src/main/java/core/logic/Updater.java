package core.logic;

/*
 *  Class responsible for rendering the current game state
 */

import states.State;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Updater implements Runnable {

    private final int UPS_CAP = 60;

    private Thread thread;
    private int currentUPS;
    private boolean running = false;
    private State state;

    public Updater() {

    }

    public synchronized void start() {
        thread = new Thread(this, "UPDATER");
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double ns = 1000000000 / UPS_CAP;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                update();
                currentUPS++;
                delta--;
            }

            if(System.currentTimeMillis() - timer > 1000) {
                System.out.println("UPS:" + currentUPS);
                timer += 1000;
                currentUPS = 0;
            }
        }
        stop();
    }

    private void update() {
        if (state != null) state.update();
    }
}
