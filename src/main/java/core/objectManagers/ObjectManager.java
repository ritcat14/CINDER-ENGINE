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

    private void initObjects(RenderType renderType) {
        Iterator<Object> it = sharedObjects.iterator();
        while (it.hasNext()) it.next().init(renderType);
    }

    private void renderObjects(RenderType renderType) {
        Iterator<Object> it = sharedObjects.iterator();
        while (it.hasNext()) it.next().render(renderType);
    }

    private void updateObjects(RenderType renderType) {
        Iterator<Object> it = sharedObjects.iterator();
        while (it.hasNext()) it.next().update(renderType);
    }

    public synchronized void init(RenderType renderType) {
        initObjects(renderType);
    }

    public synchronized void update(RenderType renderType) {
        updateObjects(renderType);
    }

    public synchronized void render(RenderType renderType) {
        renderObjects(renderType);
    }

    public void cleanUp() {
        sharedObjects.clear();
    }
}
