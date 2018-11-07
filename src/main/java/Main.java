import general.MyImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String inname = "E:/AProgramming/JavaPrograms/ImageCreator/src/main/resources/img/cat1.jpg";
        String outname = "E:/AProgramming/JavaPrograms/ImageCreator/src/main/resources/img/greycat.jpg";
        MyImage img = new MyImage(inname);
        img.toGray().save(outname);

//        BufferedImage img = ImageIO.read(new File(filename));
//        int w = img.getWidth();
//        int h = img.getHeight();
//        BufferedImage grayImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//
//        System.out.println(w + " " + h);
//
//        for(int i = 0; i < w; i++) {
//            for (int j = 0; j < h; j++) {
//                int pixel = img.getRGB(i, j);
//                int red = (pixel >> 16) & 0xff;
//                int green = (pixel >> 8) & 0xff;
//                int blue = (pixel) & 0xff;
//                int avg = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);
//                int p = (avg<<24) | (avg<<16) | (avg<<8) | avg;
//                grayImg.setRGB(i, j, p);
//            }
//        }
//        ImageIO.write(grayImg, "jpg", new File(outname));
    }
}
