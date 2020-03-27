package core.objects;

import core.graphics.PixelRenderer;
import core.loading.Resource;

public abstract class Object extends Resource {

    boolean removed = false;

    public abstract void update();

    public abstract void render(PixelRenderer pixelRenderer);

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

}
