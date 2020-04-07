package core.objects;

import core.graphics.Renderer;
import core.loading.Resource;

public abstract class Object extends Resource {

    boolean removed = false;

    public abstract void update();

    public abstract void render(Renderer renderer);

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

}
