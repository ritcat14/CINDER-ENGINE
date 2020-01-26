package core.threads;

import core.loading.Resource;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Loader extends Loop {

    private ConcurrentLinkedQueue<Resource> resources = new ConcurrentLinkedQueue<>();

    public Loader(ThreadManager threadManager) {
        super(threadManager, 60, "LOADER");
    }

    public synchronized void addResources(ConcurrentLinkedQueue<Resource> resources) {
        this.resources.addAll(resources);
    }

    @Override
    public void init() {
    }

    @Override
    public void onLoop() {
        threadManager.updateResources();
        Iterator<Resource> it = resources.iterator();
        while (it.hasNext()) {
            Resource resource = it.next();
            if (!resource.isInitialised()) resource.init();
            it.remove();
        }
    }
}
