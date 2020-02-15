package cache.types;

import java.awt.image.BufferedImage;

public class BufferedImageBlock extends Block {

    private BufferedImage image;

    public BufferedImageBlock(BufferedImage image) {
        super();
        this.image = image;
    }

    public BufferedImageBlock(BufferedImage image, int life) {
        super(life);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }
}
