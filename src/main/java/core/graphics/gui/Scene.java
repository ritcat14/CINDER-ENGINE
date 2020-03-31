package core.graphics.gui;

import core.graphics.PixelRenderer;
import core.objects.Point;
import core.objects.Rectangle;
import files.FileReader;
import files.ImageTools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Scene extends GuiPanel {

    private volatile LinkedList<BufferedImage> images = new LinkedList();
    private volatile BufferedImage currentFrame;

    private final String filePath;

    private String[] frameData;
    private double[] lifes;

    private int frame = 0;
    private int frameNum;
    private int time = 0;

    private boolean finished = false;
    private boolean running = false;
    private boolean paused = false;


    public Scene(Point point, String filePath) {
        super(new Rectangle(point.getX(), point.getY(), 1, 1), Color.GRAY);
        this.filePath = filePath;
    }

    @Override
    public void init() {
        if (!visible || isRemoved()) return;
        frameData = FileReader.readFile(filePath);
        String imageDir = frameData[0];

        frameNum = Integer.parseInt(frameData[1].split(":")[0]);

        BufferedImage[] frames = ImageTools.loadSprites(imageDir, frameNum);

        lifes = new double[frameNum];
        for (int i = 0; i < frameNum; i++) lifes[i] = Double.parseDouble(frameData[1].split(":")[1]);

        // Deposit sprites[] into new list
        images = new LinkedList(Arrays.asList(frames));
        super.init();
    }

    public void play() {
        if (!visible || isRemoved()) return;
        if (paused) paused = false;
        running = true;
        finished = false;
    }

    public void pause() {
        if (!visible || isRemoved()) return;
        paused = true;
    }

    public void stop() {
        if (!visible || isRemoved()) return;
        paused = true;
        running = false;
        finished = true;
    }

    public void setFrame(int frame) {
        this.frame = frame;
        currentFrame = images.get(frame);
    }

    @Override
    public void update() {
        if (!visible || isRemoved()) return;
        if (running && !paused) {
            super.update();
            time++;
            if (time % (120 * lifes[frame]) == 0) {
                Iterator<BufferedImage> iterator = images.iterator();
                while (iterator.hasNext()) {
                    BufferedImage nextImage = iterator.next();
                    if (nextImage == currentFrame) {
                        iterator.remove();
                    } else {
                        currentFrame = nextImage;
                        frame++;
                        if (frame >= frameNum) {
                            finished = true;
                            return;
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void render(PixelRenderer pixelRenderer) {
        if (!visible || isRemoved()) return;
        if (running) {
            super.render(pixelRenderer);
            pixelRenderer.drawImage(currentFrame, 0, 0);
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isPaused() {
        return paused;
    }
}