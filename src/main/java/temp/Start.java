package temp;

import core.events.Event;
import core.events.types.MouseEventFired;
import core.graphics.gui.GuiButton;
import core.states.State;
import files.ImageTools;

public class Start extends State {

    public Start() {
        super("start");
    }

    @Override
    public void init() {
        super.init();
        System.out.println("Initiated state start");
        objectManager.addResource(new GuiButton(10, 10, 200, 75,
                ImageTools.getImage("images/startButton1.png"), ImageTools.getImage("images/startButton2.png")) {
            @Override
            protected boolean mousePressed(MouseEventFired event) {
                if (super.mousePressed(event)) {
                    requestChange("game");
                    return true;
                }
                return false;
            }
        });
    }
}
