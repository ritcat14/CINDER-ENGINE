package core.objectManagers;

import core.loading.Resource;
import core.objects.Object;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ObjectManager implements Resource {

    protected CopyOnWriteArrayList<Object> sharedObjects = new CopyOnWriteArrayList<>();

    public synchronized void addObject(Object object) {
        sharedObjects.add(object);
    }

    public synchronized void removeObject(Object object) {
        sharedObjects.remove(object);
    }

    private void initObjects() {
        Iterator<Object> it = sharedObjects.iterator();
        while (it.hasNext()) it.next().init();
    }

    private void renderObjects() {
        Iterator<Object> it = sharedObjects.iterator();
        while (it.hasNext()) it.next().render();
    }

    private void updateObjects() {
        Iterator<Object> it = sharedObjects.iterator();
        while (it.hasNext()) it.next().update();
    }

    public synchronized void init() {
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
