package core.graphics.gui;

import files.FileReader;

import java.awt.*;

public class Scene extends GuiPanel {

    private final String filePath;

    private int MAX_LOAD = 40;

    private Frame[] frames;
    private String[] frameData;
    private Frame currentFrame;
    private int frame = 0;
    private int loadedPointer = -1;
    private int frameNum;

    private String imageDir;

    private boolean finished = false;
    private boolean running = false;
    private boolean paused = false;

    public Scene(double x, double y, String filePath) {
        super(x, y, 1, 1, Color.GRAY);
        this.filePath = filePath;
    }

    @Override
    public void init() {
        frameData = FileReader.readFile(filePath);
        imageDir = frameData[0];

        frameNum = Integer.parseInt(frameData[1].split(":")[0]);

        frames = new Frame[frameNum];

        for (int i = 0; i < frameNum; i++) {
            frames[i] = new Frame(Double.parseDouble(frameData[1].split(":")[1]), imageDir + i + ".png");
        }

        loadNext();

        currentFrame = frames[0];
        currentFrame.redraw();
        super.init();
    }

    private void loadNext() {
        if (loadedPointer == frameNum - 1) return;

        if (loadedPointer + MAX_LOAD >= frameNum) MAX_LOAD = frameNum - loadedPointer - 1;
        System.out.println("Loading next: " + MAX_LOAD);
        for (int i = 1; i < MAX_LOAD + 1; i++) frames[loadedPointer + i].init();
        loadedPointer += MAX_LOAD;
    }

    public void start() {
        if (paused) paused = false;
        running = true;
    }

    public void pause() {
        paused = true;
    }

    public void stop() {
        running = false;
        finished = true;
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
                    currentFrame.remove();
                    frames[frame - 1] = null;
                    currentFrame = frames[frame];
                    currentFrame.redraw();
                }
            }
            if (frame == (loadedPointer - (MAX_LOAD / 2))) loadNext();
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