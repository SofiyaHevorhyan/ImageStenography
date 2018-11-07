package general;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyImage {
    private String path;
    private BufferedImage img;

    public MyImage(String path) throws IOException {
        this.path = path;
        this.img = ImageIO.read(new File(path));
    }

    private MyImage(BufferedImage img) {
        this.img = img;
    }

    public int[][][] getRawPixels() {
        int w = img.getWidth();
        int h = img.getHeight();
        int[][][] pixels = new int[w][h][3];

        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                int pixel = img.getRGB(i, j);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                pixels[i][j][0] = red;
                pixels[i][j][1] = green;
                pixels[i][j][2] = blue;
            }
        }
        return pixels;
    }
    public void save(String path) throws IOException {
        ImageIO.write(img, "jpg", new File(path));
    }

    public MyImage toGray() {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage grayImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        System.out.println(w + " " + h);

        for(int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int pixel = img.getRGB(i, j);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                int avg = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);
                int p = (avg<<24) | (avg<<16) | (avg<<8) | avg;
                grayImg.setRGB(i, j, p);
            }
        }
        return new MyImage(grayImg);
    }
}
