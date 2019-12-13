package core.objectManagers;

import core.CinderEngine.RenderType;
import core.objects.Entity;
import core.objects.Object;

import java.util.Iterator;

public class EntityManager extends ObjectManager {

    public EntityManager() {
    }

    @Override
    public void update(RenderType renderType) {
        Iterator<Object> it = sharedObjects.iterator();
        while (it.hasNext()) ((Entity) it.next()).updateTransformation();
        super.update(renderType);
    }
}
