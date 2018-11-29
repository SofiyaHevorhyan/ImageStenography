import general.MyImage;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String inName = "Your-path-here/src/main/resources/img/cat1.jpg";
        String outName = "Your-path-here/src/main/resources/img/greycat.jpg";
        String outName2 = "Your-path-here/src/main/resources/img/encodecat.jpg";
        MyImage img = new MyImage(inName);
        img.toGray().save(outName);

        String line = "William";
        img.encode(line).save(outName2);
        System.out.println("Message 'William' has been encoded");
    }
}
