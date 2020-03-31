package temp;

import core.events.Event;
import core.events.EventDispatcher;
import core.events.types.MouseEventFired;
import core.graphics.Window;
import core.graphics.gui.GuiButton;
import core.graphics.gui.GuiPanel;
import core.graphics.gui.Scene;
import core.objects.Point;
import core.objects.Rectangle;
import core.states.State;
import files.ImageTools;

import java.awt.*;

public class Start extends State {

    private SettingMenu settingMenu;

    private Scene testScene;

    public Start() {
        super("start");
    }

    @Override
    public void init() {
        objectManager.addResource(testScene = new Scene(new Point(), "scenes/testScene.txt"));
        testScene.play();
        super.init();
    }

    private void initPanels() {
        objectManager.addResource(new GuiPanel(new Rectangle(50, 50, Window.getWindowWidth() - 100, Window.getWindowHeight() - 100),
                Color.CYAN));
        objectManager.addResource(new GuiButton(new Rectangle(70, 70, 200, 75),
                ImageTools.getImage("images/startButton1.png"),
                ImageTools.getImage("images/startButton2.png")) {
            @Override
            protected boolean mousePressed(MouseEventFired event) {
                if (super.mousePressed(event)) {
                    requestChange("game");
                    return true;
                }
                return false;
            }
        });

        objectManager.addResource(new GuiButton(new Rectangle(70, 150, 200, 75),
                ImageTools.getImage("images/stuffButton1.png"),
                ImageTools.getImage("images/stuffButton2.png")) {
            @Override
            protected boolean mousePressed(MouseEventFired event) {
                if (super.mousePressed(event)) {
                    settingMenu.toggleVisible();
                    return true;
                }
                return false;
            }
        });

        objectManager.addResource(settingMenu = new SettingMenu());
    }

    private boolean mousePressed(MouseEventFired eventFired) {
        System.out.println("Mouse pressed: " + eventFired.getButton());
        if (eventFired.getButton() == 1 && !testScene.isPaused()) {
            testScene.pause();
        } else if (eventFired.getButton() == 3 && testScene.isPaused()) {
            testScene.play();
        }
        return false;
    }

    @Override
    protected void eventFired(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> mousePressed((MouseEventFired) e));
    }

    @Override
    public void update() {
        super.update();

        if (testScene.isFinished() && !testScene.isRemoved()) {
            testScene.remove();
            initPanels();
        }
    }

}