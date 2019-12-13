package temp;

import core.CinderEngine.RenderType;
import core.objectManagers.ObjectManager;
import core.states.State;

public class Start extends State {

    private int time = 0;

    public Start(ObjectManager objectManager) {
        super(objectManager, "start");
    }

    @Override
    public void init(RenderType renderType) {
        System.out.println("Initiated state start");
    }

    @Override
    public void update(RenderType renderType) {
        super.update(renderType);
        time++;
        if (time % 120 == 0) requestChange("game");
    }
}
