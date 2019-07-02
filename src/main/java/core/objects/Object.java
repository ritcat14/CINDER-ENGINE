package core.objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Object {

    protected final Vector3f up = new Vector3f(0, 1, 0);

    protected Matrix4f transformation;

    protected abstract void updateTransformation();

    public abstract void update();

    public abstract void render();

}
