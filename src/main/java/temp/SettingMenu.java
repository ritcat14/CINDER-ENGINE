package temp;

import core.graphics.Window;
import core.graphics.gui.GuiPanel;
import core.objects.Rectangle;

import java.awt.*;


public class SettingMenu extends GuiPanel {

    public SettingMenu() {
        super(new Rectangle(270, 150, Window.getWindowWidth() - 470, Window.getWindowHeight() - 300), Color.GREEN);
        setVisible(false);
    }
}
