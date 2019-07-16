package temp;

import core.objects.Entity;
import org.joml.Vector3f;

public class TestObject extends Entity {

    public TestObject() {
        super(new Vector3f(), new Vector3f(), new Vector3f(1f, 1f, 1f));
    }

    @Override
    public void intermediateCode() {
        //System.out.println("TEST OBJECT SHARED CODE");
    }

    @Override
    protected void updateTransformation() {

    }

    @Override
    public void update() {
        //System.out.println("TEST OBJECT UPDATE");
    }

    @Override
    public void render() {
        //System.out.println("TEST OBJECT RENDER");
    }
}
