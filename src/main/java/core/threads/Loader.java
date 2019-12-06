package core.threads;

import core.loading.Resource;
import core.CinderEngine.RenderType;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Loader extends Loop {

    private CopyOnWriteArrayList<Resource> resources = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Resource> resourcesToRemove = new CopyOnWriteArrayList<>();

    public Loader(ThreadManager threadManager, RenderType renderType) {
        super(threadManager, 60, "LOADER", renderType);
    }

    public synchronized void addResources(CopyOnWriteArrayList<Resource> resources) {
        Iterator<Resource> it = resources.iterator();
        while (it.hasNext()) this.resources.add(it.next());
    }

    @Override
    public void init() {}

    @Override
    public void onLoop() {
        threadManager.updateResources();
        Iterator<Resource> it = resources.iterator();
        while (it.hasNext()) {
            Resource resource = it.next();
            resource.init(renderType);
            resourcesToRemove.add(resource);
        }
        resources.removeAll(resourcesToRemove);
        resourcesToRemove.clear();
    }
}
