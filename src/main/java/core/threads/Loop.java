package core.threads;

public abstract class Loop implements Runnable {

    private final double SECOND = 1000000000;
    private final double MAX_TPS;
    private final Thread thread;
    private final FairLock lock = new FairLock();

    protected final ThreadManager threadManager;

    private int TPS;
    private int finalTPS;
    private boolean running = false;

    private volatile boolean locked = false;

    public Loop(ThreadManager threadManager, double MAX_TPS, String name) {
        this.threadManager = threadManager;
        threadManager.addLoop(this);
        this.MAX_TPS = MAX_TPS;
        this.thread = new Thread(this, name);
    }

    public synchronized void start() {
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void init();

    protected abstract void loop();

    private void threadLoop() {
        try {
            lock.lock();
            locked = true;
            loop();
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

    public synchronized boolean isLocked() {
        return locked;
    }

}
