package core.objects;

import core.graphics.Renderer;

public abstract class Object {

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
