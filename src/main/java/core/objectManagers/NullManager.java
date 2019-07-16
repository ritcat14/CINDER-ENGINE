package core.objectManagers;

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
    public synchronized void init() {
    }

    @Override
    public synchronized void update() {
    }

    @Override
    public synchronized void render() {
    }

}
