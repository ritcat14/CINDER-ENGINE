package core.objectManagers;

import core.events.Event;
import core.events.EventListener;
import core.graphics.gui.GuiComponent;
import core.loading.Resource;
import core.objects.Object;

import java.awt.*;
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

    protected synchronized void addObject(Object object) {
        sharedObjects.add(object);
    }

    protected synchronized void addObjects(List<Object> objectList) {
        sharedObjects.addAll(objectList);
    }

    private void updateResources() {
        Iterator<Resource> it = sharedResources.iterator();
        while (it.hasNext()) {
            Resource resource = it.next();
            if (resource instanceof Object) {
                if (resource.isInitialised()) {
                    if (resource instanceof GuiComponent) guiComponents.add((GuiComponent) resource);
                    else addObject((Object) resource);
                    it.remove();
                }
            }
        }
    }

    private void renderObjects(Graphics graphics) {
        for (Object sharedObject : sharedObjects) sharedObject.render(graphics);
        for (Object guiComponent : guiComponents) guiComponent.render(graphics);
    }

    private void checkList(ConcurrentLinkedQueue<Object> list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Object object = (Object) it.next();
            if (object.isRemoved()) it.remove();
        }
    }

    private void updateObjects() {
        updateResources();
        checkList(guiComponents);
        checkList(sharedObjects);
        for (Object sharedObject : sharedObjects) sharedObject.update();
        for (Object guiComponent : guiComponents) guiComponent.update();
    }

    public synchronized void update() {
        updateObjects();
    }

    public synchronized void render(Graphics graphics) {
        renderObjects(graphics);
    }

    public ConcurrentLinkedQueue<Object> getSharedObjects() {
        return sharedObjects;
    }

    public ConcurrentLinkedQueue<Resource> getSharedResources() {
        return sharedResources;
    }

    @Override
    public void onEvent(Event event) {
        for (Object sharedObject : sharedObjects) {
            if (sharedObject instanceof EventListener) {
                ((EventListener)sharedObject).onEvent(event);
            }
        }
        for (Object guiComponent : guiComponents) {
            if (guiComponent instanceof EventListener) {
                ((EventListener)guiComponent).onEvent(event);
            }
        }
    }

    public void cleanUp() {
        sharedObjects.clear();
        sharedResources.clear();
        guiComponents.clear();
    }
}
