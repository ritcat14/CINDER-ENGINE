package core.threads;

import core.sout.LogType;
import core.sout.Logger;

public abstract class Loop implements Runnable, LoopInterface {

    private final double SECOND = 1000000000;
    private final double MAX_TPS;
    private final String name;
    private final FairLock lock = new FairLock();

    protected volatile ThreadManager threadManager;

    private Thread thread;
    private int TPS;
    private int finalTPS;

    private volatile boolean locked = false;
    private volatile boolean running = false;
    private volatile boolean closed = false;

    public Loop(ThreadManager threadManager, double MAX_TPS, String name) {
        this.name = name.toUpperCase();
        this.threadManager = threadManager;
        this.MAX_TPS = MAX_TPS;
    }

    public abstract void init();

    public synchronized void start() {
        running = true;
        thread = new Thread(this, name);
        thread.start();
    }

    public synchronized void stop() {
        Logger.PRINT(LogType.THREAD, "Closing thread " + thread.getName());
        closed = true;
        if (locked) {
            lock.unlock();
            locked = false;
        }
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public synchronized void stopRunning() {
        running = false;
    }

    private void threadLoop() {
        try {
            lock.lock();
            locked = true;
            onLoop();
            TPS++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            locked = false;
        }
    }

    @Override
    public void run() {
        init();
        long lastTime = System.nanoTime();
        double ns = SECOND / (MAX_TPS/2);
        double delta = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                threadLoop();
                TPS++;
                delta--;
            }
            if(System.currentTimeMillis() - timer > 1000) {
                finalTPS = TPS;
                timer += 1000;
                TPS = 0;
            }
        }
        stop();
    }

    public synchronized int getFinalTPS() {
        return finalTPS;
    }

    public boolean isClosed() {
        return closed;
    }

    public synchronized String getName() {
        return name;
    }
}
