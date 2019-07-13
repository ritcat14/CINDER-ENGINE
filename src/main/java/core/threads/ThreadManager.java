package core.threads;


import java.util.ArrayList;
import java.util.List;

public class ThreadManager {

    private List<Loop> loops = new ArrayList<>();

    public void addLoop(Loop loop) {
        this.loops.add(loop);
    }

    public String getTPS() {
        String TPS = "CINDER-ENGINE:";
        for (int i = 0; i < loops.size(); i++) {
            if (i == 0) TPS += "UPS:" + loops.get(i).getFinalTPS();
            if (i == 1) TPS += "|FPS:" + loops.get(i).getFinalTPS();
        }
        return TPS;
    }

    public synchronized void intermediateCode() {
        System.out.println(Thread.currentThread().getName());
    }
}
