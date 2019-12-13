package temp;

import core.objects.Entity;
import org.joml.Vector3f;
import core.CinderEngine.RenderType;

public class TestObject extends Entity {

    private static int initCounter = 0;

    public TestObject() {
        super(new Vector3f(), new Vector3f(), new Vector3f(1f, 1f, 1f));
    }

    @Override
    public void init(RenderType renderType) {
        System.out.println("Object " + initCounter + "  initialised.");
        initCounter++;
    }

    @Override
    public void updateTransformation() {
        //System.out.println("UPDATING TRANSFORM");
    }

    @Override
    public void update(RenderType renderType) {
        ///System.out.println("TEST OBJECT UPDATE");
    }

    @Override
    public void render(RenderType renderType) {
        //System.out.println("TEST OBJECT RENDER");
    }
}
