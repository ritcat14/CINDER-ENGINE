package core.objects;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import static core.CinderEngine.ASPECT_RATIO;

public abstract class Object2D extends Object {

    protected Vector2f position;
    protected Vector2f rotation;
    protected Vector2f scale;

    public Object2D(Vector2f position, Vector2f rotation, Vector2f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Object2D(Vector2f position, Vector2f scale) {
        this.position = position;
        this.rotation = new Vector2f();
        this.scale = scale;
    }

    public Object2D(Vector2f scale) {
        this.position = new Vector2f();
        this.rotation = new Vector2f();
        this.scale = scale;
    }

    @Override
    protected void updateTransformation() {
        new Matrix4f()
                .perspective((float)Math.toRadians(45.0f), (float)ASPECT_RATIO, 0.1f, 1000f, transformation)
                .lookAt(rotation.x, 0, rotation.y, position.x, position.y, 0, up.x, up.y, up.z,
                        transformation);
    }

}
