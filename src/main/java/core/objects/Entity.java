package core.objects;

public abstract class Entity extends Object {

    protected volatile Rectangle bounds;
    protected volatile int dir;

    public Entity(Rectangle rectangle) {
        this.bounds = rectangle;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
