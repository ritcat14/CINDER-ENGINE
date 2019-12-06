package core.objectManagers;

import core.loading.Resource;
import core.objects.Object;
import core.CinderEngine.RenderType;

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

    private void renderObjects(RenderType renderType) {
        Iterator<Object> it = sharedObjects.iterator();
        while (it.hasNext()) it.next().render(renderType);
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

    public synchronized void render(RenderType renderType) {
        renderObjects(renderType);
    }

    public void cleanUp() {
        sharedObjects.clear();
    }
}
