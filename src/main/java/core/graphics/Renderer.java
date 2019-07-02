package core.graphics;

/*
 *  Class responsible for rendering the current game state
 */

import states.State;

public class Renderer implements Runnable {

    private final int FPS_CAP = 120;

    private Thread thread;
    private int currentFPS;
    private boolean running = false;
    private Window window;
    private State state;

    public Renderer(double width, double height) {
        window = new Window(width, height);
    }

    public synchronized void start() {
        thread = new Thread(this, "RENDERER");
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
        window.init();
        long lastTime = System.nanoTime();
        double ns = 1000000000 / FPS_CAP;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                render();
                currentFPS++;
                delta--;
            }

            if(System.currentTimeMillis() - timer > 1000) {
                System.out.println("FPS:" + currentFPS);
                timer += 1000;
                currentFPS = 0;
            }
        }
        stop();
    }

    private void preRender() {
        if (state != null) state.preRender();
    }

    private void render() {
        preRender();
        window.render();
        if (state != null) state.render();
        postRender();
    }

    private void postRender() {
        if (state != null) state.postRender();
    }
}
