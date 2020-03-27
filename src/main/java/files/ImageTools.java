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
import java.util.Objects;

public abstract class ImageTools {

    private static final int radius = 11;
    private static final int size = radius * 2 + 1;
    private static final float weight = 1.0f / (size * size);
    private static final float[] data = new float[size * size];

    private static Kernel kernel;
    private static ConvolveOp convolveOp;

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
                loadedImage = ImageIO.read(Objects.requireNonNull(CinderEngine.class.getClassLoader().getResourceAsStream(fileName)));
            } catch (NullPointerException e) {
                System.out.println("Failed to find file: " + fileName);
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (loadedImage != null) FileCache.addBlock(fileName, new BufferedImageBlock(loadedImage));
        return loadedImage;
    }

    public static BufferedImage blur(BufferedImage image) {
        Arrays.fill(data, weight);
        kernel = new Kernel(size, size, data);
        convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        return convolveOp.filter(image, null);
    }

    public static BufferedImage[] splitImage(String image, int rows, int cols, int chunkWidth, int chunkHeight) {
        return splitImage(getImage(image), rows, cols, chunkWidth, chunkHeight);
    }

    public static BufferedImage[] splitImage(BufferedImage image, int rows, int cols, int chunkWidth, int chunkHeight) {
        int chunks = rows * cols;
        int count = 0;

        BufferedImage[] imgs = new BufferedImage[chunks];

        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                imgs[count++] = image.getSubimage(x * chunkWidth, y * chunkHeight, chunkWidth, chunkHeight);
            }
        }
        return imgs;
    }

}
