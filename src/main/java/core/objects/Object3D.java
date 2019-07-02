package core.objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static core.CinderEngine.ASPECT_RATIO;

public abstract class Object3D extends Object {

    protected Vector3f position;
    protected Vector3f rotation;
    protected Vector3f scale;

    public Object3D(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Object3D(Vector3f position, Vector3f scale) {
        this.position = position;
        this.rotation = new Vector3f();
        this.scale = scale;
    }

    public Object3D(Vector3f scale) {
        this.position = new Vector3f();
        this.rotation = new Vector3f();
        this.scale = scale;
    }

    @Override
    protected void updateTransformation() {
        new Matrix4f()
                .perspective((float)Math.toRadians(45.0f), (float)ASPECT_RATIO, 0.1f, 1000f, transformation)
                .lookAt(rotation, position, up);
    }

}
