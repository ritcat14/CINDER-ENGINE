package core.threads;

public class QueueObject {

    private boolean isNotified = false;

    public synchronized void doWait() throws InterruptedException {
        while (!isNotified) this.wait();
        isNotified = false;
    }

    public synchronized void doNotify() {
        this.isNotified = true;
        this.notify();
    }

    public boolean equals(Object o) {
        return this == o;
    }

}
