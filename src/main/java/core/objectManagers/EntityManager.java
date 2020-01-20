package core.objectManagers;

import core.objects.Entity;
import core.objects.Object;

import java.util.Iterator;

public class EntityManager extends ObjectManager {

    public EntityManager() {
    }

    @Override
    public void update() {
        Iterator<Object> it = sharedObjects.iterator();
        while (it.hasNext()) ((Entity) it.next()).updateTransformation();
        super.update();
    }
}
