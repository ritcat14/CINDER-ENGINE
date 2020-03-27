package core.objectManagers;

import core.events.Event;
import core.events.EventListener;
import core.graphics.PixelRenderer;
import core.graphics.gui.GuiComponent;
import core.loading.Resource;
import core.objects.Object;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ObjectManager implements EventListener {

    private ConcurrentLinkedQueue<Object> sharedObjects = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Resource> sharedResources = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Object> guiComponents = new ConcurrentLinkedQueue<>();

    public synchronized void addResource(Resource resource) {
        sharedResources.add(resource);
    }

    public synchronized void addResources(List<Resource> resources) {
        sharedResources.addAll(resources);
    }

    private synchronized void updateResources() {
        Iterator<Resource> it = sharedResources.iterator();
        while (it.hasNext()) {
            Resource resource = it.next();
            if (resource instanceof Object && resource.isInitialised()) {
                if (resource instanceof GuiComponent) guiComponents.add((GuiComponent) resource);
                else sharedObjects.add((Object) resource);
                it.remove();
            }
        }
    }

    private synchronized void renderObjects(PixelRenderer pixelRenderer) {
        for (Object sharedObject : sharedObjects) sharedObject.render(pixelRenderer);
        for (Object guiComponent : guiComponents) guiComponent.render(pixelRenderer);
    }

    private synchronized void checkList(ConcurrentLinkedQueue<Object> list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Object object = (Object) it.next();
            if (object.isRemoved()) it.remove();
        }
    }

    private synchronized void updateObjects() {
        updateResources();

        checkList(sharedObjects);
        for (Object sharedObject : sharedObjects) sharedObject.update();

        checkList(guiComponents);
        for (Object guiComponent : guiComponents) guiComponent.update();
    }

    public synchronized void update() {
        updateObjects();
    }

    public synchronized void render(PixelRenderer pixelRenderer) {
        renderObjects(pixelRenderer);
    }

    public ConcurrentLinkedQueue<Object> getSharedObjects() {
        return sharedObjects;
    }

    public ConcurrentLinkedQueue<Resource> getSharedResources() {
        return sharedResources;
    }

    @Override
    public synchronized void onEvent(Event event) {
        for (Object guiComponent : guiComponents) {
            if (guiComponent instanceof EventListener) {
                ((EventListener) guiComponent).onEvent(event);
            }
        }
        for (Object sharedObject : sharedObjects) {
            if (sharedObject instanceof EventListener) {
                ((EventListener) sharedObject).onEvent(event);
            }
        }
    }

    public synchronized void cleanUp() {
        sharedObjects.clear();
        sharedResources.clear();
        guiComponents.clear();
    }
}
