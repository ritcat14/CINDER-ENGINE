package core.objects;

import core.loading.Resource;

import java.awt.*;

public abstract class Object extends Resource {

    boolean removed = false;

    public abstract void update();

    public abstract void render(Graphics graphics);

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

}
