package core.graphics.gui;

import files.FileReader;

import java.awt.*;

public class Scene extends GuiPanel {

    private Frame[] frames;
    private Frame currentFrame;
    private int frame = 0;
    private int frameNum;
    private String filePath;

    private boolean finished = false;
    private boolean running = false;
    private boolean paused = false;

    public Scene(double x, double y, String filePath) {
        super(x, y, 1, 1, Color.GRAY);
        this.filePath = filePath;
    }

    public void start() {
        if (paused) {
            paused = false;
            currentFrame.setBlurred(false);
        }
        running = true;
    }

    public void pause() {
        paused = true;
        if (currentFrame != null) currentFrame.setBlurred(true);
    }

    public void stop() {
        running = false;
        finished = true;
    }

    @Override
    public void init() {
        String[] frameData = FileReader.readFile(filePath);
        frameNum = frameData.length;

        frames = new Frame[frameNum];

        for (int i = 0; i < frameNum; i++) {
            frames[i] = new Frame(frameData[i]);
            frames[i].init();
        }

        currentFrame = frames[0];
        currentFrame.redraw();
        super.init();
    }

    @Override
    public void update() {
        if (running && !paused) {
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
        if (running) {
            super.render(graphics);
            currentFrame.render(graphics);
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isPaused() {
        return paused;
    }
}