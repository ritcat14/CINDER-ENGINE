package core.graphics.gui;

import java.awt.Graphics;
import java.awt.Color;

import files.FileReader;
import files.ImageTools;

public class Scene extends GuiPanel {

    private Frame[] frames;
    private Frame currentFrame;
    private int frame = 0;
    private int frameNum;
    private String filePath;

    private boolean finished = false;

    public Scene(double x, double y, String filePath) {
        super(x, y, 1, 1, Color.GRAY);
        this.filePath = filePath;
    }

    @Override
    public void init() {
        String[] frameData = FileReader.readFile(filePath);
        frameNum = frameData.length - 1;

        frames = new Frame[frameNum];

        for (int i = 1; i < frameNum + 1; i++) frames[i - 1] = new Frame(frameData[i], ImageTools.getImage(frameData[i].split("|")[1]));

        currentFrame = frames[0];
        currentFrame.redraw();
        super.init();
    }

    /**
     * @return finished
     */
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void update() {
        if (!finished) {
            super.update();
            currentFrame.update();
            if (!currentFrame.isAlive()) {
                frame++;
                if (frame >= frameNum) finished = true;
                else {
                    currentFrame = frames[frame];
                    currentFrame.redraw();
                }
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        if (!finished) {
            super.render(graphics);
            currentFrame.render(graphics);
        }
    }
    
}