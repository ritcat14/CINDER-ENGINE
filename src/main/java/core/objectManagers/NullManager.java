package core.objectManagers;

import core.CinderEngine.RenderType;
import core.objects.Object;

public class NullManager extends ObjectManager {

    @Override
    @Deprecated
    public synchronized void addObject(Object object) {
    }

    @Override
    @Deprecated
    public synchronized void removeObject(Object object) {
    }

    @Override
    public synchronized void init(RenderType renderType) {
    }

    @Override
    public synchronized void update(RenderType renderType) {
    }

    @Override
    public synchronized void render(RenderType renderType) {
    }

}
