package core.objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Entity extends SharedObject {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    protected final Vector3f up = new Vector3f(0, 1, 0);

    protected Matrix4f transformation;

    public Entity(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    protected abstract void updateTransformation();

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
