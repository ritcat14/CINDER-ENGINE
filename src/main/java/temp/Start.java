package temp;

import core.states.State;

public class Start extends State {

    private int time = 0;

    public Start() {
        super("start");
    }

    @Override
    public void init() {
        System.out.println("Initiated state start");
    }

    @Override
    public void update() {
        super.update();
        time++;
        if (time % 120 == 0) requestChange("game");
    }
}
