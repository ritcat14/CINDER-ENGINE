package core.objectManagers;

import core.objects.SharedObject;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ObjectManager {

    protected CopyOnWriteArrayList<SharedObject> sharedObjects = new CopyOnWriteArrayList<>();

    public synchronized void addObject(SharedObject object) {
        sharedObjects.add(object);
    }

    public synchronized void removeObject(SharedObject object) {
        sharedObjects.remove(object);
    }

    private void renderObjects() {
        intermediateCode();
        Iterator<SharedObject> it = sharedObjects.iterator();
        while (it.hasNext()) it.next().render();
    }

    private void updateObjects() {
        intermediateCode();
        Iterator<SharedObject> it = sharedObjects.iterator();
        while (it.hasNext()) it.next().update();
    }

    public synchronized void update() {
        updateObjects();
        onUpdate();
    }

    public synchronized void render() {
        renderObjects();
        onRender();
    }

    public synchronized void intermediateCode() {
        Iterator<SharedObject> it = sharedObjects.iterator();
        while (it.hasNext()) it.next().intermediateCode();
    }

    protected abstract void onUpdate();

    protected abstract void onRender();

    public void cleanUp() {
        sharedObjects.clear();
    }
}
