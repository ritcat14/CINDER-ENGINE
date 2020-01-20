package temp;

import core.objects.Entity;
import org.joml.Vector3f;

public class TestObject extends Entity {

    private static int initCounter = 0;

    public TestObject() {
        super(new Vector3f(), new Vector3f(), new Vector3f(1f, 1f, 1f));
    }

    @Override
    public void init() {
        super.init();
        System.out.println("Object " + initCounter + "  initialised.");
        initCounter++;
    }

    @Override
    public void updateTransformation() {
        //System.out.println("UPDATING TRANSFORM");
    }

    @Override
    public void update() {
        updateTransformation();
    }

    @Override
    public void render() {

    }
}
