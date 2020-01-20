package core.objectManagers;

import core.loading.Resource;
import core.objects.Object;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ObjectManager {

    protected CopyOnWriteArrayList<Object> sharedObjects = new CopyOnWriteArrayList<>();
    protected CopyOnWriteArrayList<Resource> sharedResources = new CopyOnWriteArrayList<>();

    public synchronized void addResource(Resource resource) {
        sharedResources.add(resource);
    }

    public synchronized void addObject(Object object) {
        sharedObjects.add(object);
    }

    public synchronized void removeObject(Object object) {
        sharedObjects.remove(object);
    }

    private boolean initObjects() {
        Iterator<Resource> it = sharedResources.iterator();
        boolean passed = true;
        while (it.hasNext()) {
            if (it.next() instanceof Object) {
                if (it.next().isInitialised()) {
                    addObject((Object) it.next());
                    it.remove();
                } else passed = false;
            } else it.next().init();
        }
        return passed;
    }

    private void renderObjects() {
        for (Object sharedObject : sharedObjects) sharedObject.render();
    }

    private void updateObjects() {
        for (Object sharedObject : sharedObjects) sharedObject.update();
    }

    public void init() {
        initObjects();
    }

    public synchronized void update() {
        updateObjects();
    }

    public synchronized void render() {
        renderObjects();
    }

    public void cleanUp() {
        sharedObjects.clear();
    }
}
