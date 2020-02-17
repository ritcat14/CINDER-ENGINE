package files;

import cache.FileCache;
import cache.types.BufferedImageBlock;
import core.CinderEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.util.Arrays;

public abstract class ImageTools {

    public static BufferedImage[] loadSprites(String fileName, int amount) {
        BufferedImage[] images = new BufferedImage[amount];

        for (int i = 0; i < amount; i++) {
            images[i] = getImage(fileName + i + ".png");
        }

        return images;
    }

    public static BufferedImage getImage(String fileName) {
        BufferedImageBlock loadedBlock = (BufferedImageBlock)FileCache.getBlock(fileName);
        BufferedImage loadedImage = null;
        if (loadedBlock != null) {
            loadedImage = loadedBlock.getImage();
            if (loadedImage != null) return loadedImage;
        } else {
            try {
                loadedImage = ImageIO.read(CinderEngine.class.getClassLoader().getResourceAsStream(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (loadedImage != null) FileCache.addBlock(fileName, new BufferedImageBlock(loadedImage));
        return loadedImage;
    }

    public static BufferedImage blur(BufferedImage image) {
        int radius = 11;
        int size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];

        Arrays.fill(data, weight);

        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        //tbi is BufferedImage
        return op.filter(image, null);
    }

}
