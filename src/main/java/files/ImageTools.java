package files;

import cache.FileCache;
import cache.types.BufferedImageBlock;
import core.CinderEngine;
import core.objects.Rectangle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public static List<Rectangle> processObjects(BufferedImage objectImage, int colour, int posScale, int sizeScale) {
        List<Rectangle> rectangles = new ArrayList<>();
        int[] pixels = new int[objectImage.getWidth() * objectImage.getHeight()];
        objectImage.getRGB(0, 0, objectImage.getWidth(), objectImage.getHeight(), pixels, 0, objectImage.getWidth());

        for (int y = 0; y < objectImage.getHeight(); y++) {
            for (int x = 0; x < objectImage.getWidth(); x++) {
                int pixel = pixels[x + y * objectImage.getWidth()];
                if (isColour(pixel, colour)) {
                    // Check if we have already processed this object
                    boolean processed = false;
                    for (Rectangle object : rectangles) {
                        if (processed = object.contains(x * posScale, y * posScale)) break;
                    }
                    if (processed) continue;
                    // If not, process object, scan in cardinal direction for object
                    int right = scanRight(x, y, objectImage.getWidth(), pixels, colour);
                    int down = scanDown(x, y, objectImage.getWidth(), objectImage.getHeight(), pixels, colour);
                    rectangles.add(new Rectangle(x * posScale, y * posScale, right * sizeScale, down * sizeScale));
                }
            }
        }
        return rectangles;
    }

    private static int scanRight(int xp, int yp, int width, int[] pixels, int colour) {
        int ow = 0;
        for (int i = xp; i < width; i++) {
            int pixel = pixels[i + yp * width];
            if (isColour(pixel, colour)) ow++;
            else break;
        }
        return ow;
    }

    private static int scanDown(int xp, int yp, int width, int height, int[] pixels, int colour) {
        int oh = 0;
        for (int i = yp; i < height; i++) {
            int pixel = pixels[xp + i * width];
            if (isColour(pixel, colour)) oh++;
            else break;
        }
        return oh;
    }

    private static boolean isColour(int pixel, int colour) {
        return pixel == colour;
    }

}
