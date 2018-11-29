package general;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class MyImage {

    private BufferedImage img;
    private int[][][] pixels;

    public MyImage(String path) throws IOException {
        this.img = ImageIO.read(GetImg(path));
        this.pixels = getRawPixels();
    }

    private MyImage(BufferedImage img) {
        this.img = img;
        this.pixels = getRawPixels();
    }

    private File GetImg(String name) throws FileNotFoundException{
        ClassLoader classLoader = getClass().getClassLoader();
        URL response = classLoader.getResource(name);
        if (response == null) {
            throw new FileNotFoundException(name + " not found");
        }
        return new File(response.getFile());
    }

    private int[][][] getRawPixels() {
        int w = img.getWidth();
        int h = img.getHeight();
        int[][][] pixels = new int[w][h][3];

        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                int pixel = img.getRGB(i, j);

                //Color mycolor = new Color(img.getRGB(i, j));
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

    public MyImage toGray() {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage grayImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for(int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int red = pixels[i][j][0];
                int green = pixels[i][j][1];
                int blue = pixels[i][j][2];

                int avg = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);
                int p = (avg<<24) | (avg<<16) | (avg<<8) | avg;
                grayImg.setRGB(i, j, p);
            }
        }
        return new MyImage(grayImg);
    }

    private int createPixel(int i, int j, int index, int[] symbols) {

        int p = 0;
        int[] px = new int[3];

        for (int k=0; k < 3; k++) {
            int color;
            if (index == 0) {
                color =  symbols[0];
            } else if (index >= symbols.length) {
                color = pixels[i][j][k];
            } else {
                color = (pixels[i][j][k] & 0xfc) | symbols[index];
            }
            index++;

            px[k] = color;
            if (k == 0) {
                p = (color << 24) | color << (16-8*k);
            } else {
                p = p | (color << (16-8*k));
            }

        }
        int rgb = new Color(px[0], px[1], px[2]).getRGB();
        return p;
    }

    public MyImage encode(String message) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        int len = 4*message.length();
        byte[] array = message.getBytes();

        int[] symbols = new int[len+1];
        symbols[0] = len;

        for (int i=0; i < message.length(); i++) {
            byte letter = array[i];
            symbols[4*i+1] = (letter >> 6) & 0x3; // save only 2 last bits
            symbols[4*i+2] = (letter >> 4) & 0x3;
            symbols[4*i+3] = (letter >> 2) & 0x3;
            symbols[4*i+4] = (letter) & 0x3;
       }

        int index = 0;

        for (int i=0; i < w; i++) {
            for (int j=0; j < h; j++) {

                int p;
                if (index < symbols.length) {
                    p = createPixel(i, j, index, symbols);
                    index += 3;
                } else {
                    int red = pixels[i][j][0];
                    int green = pixels[i][j][1];
                    int blue = pixels[i][j][2];
                    p = (red << 16) | (green << 8) | blue;
                }
                newImage.setRGB(i, j, p);
            }
        }
        return new MyImage(newImage);
    }

    public String decode(String line) {
        int first = pixels[0][0][0];
        int sec = pixels[0][0][1] & 0x3;
        int third = pixels[0][0][2] & 0x3;


        return line;
    }

    public void save(String path) throws IOException {
        ImageIO.write(img, "jpg", new File(path));
    }
}
