import general.MyImage;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        String inName = "img/cat.jpg";
        String outName = "your-path-here/src/main/resources/img/greyCat.jpg";
        String outName2 = "your-path-here/src/main/resources/img/encodedCat.jpg";

        System.out.println("Please, type message. Only in ascii, less then 256 symbols: ");
        Scanner sc = new Scanner(System.in);
        String message = sc.next();
        while (message.length() > 256) {
            System.out.println("Invalid length. Try again");
            message = sc.next();
        }

        MyImage img = new MyImage(inName);
        img.toGray().save(outName);
        System.out.println("Creating a monochrome image of cat.jpg");

        MyImage mg2 = img.encode(message);
        mg2.save(outName2);
        System.out.format("Message '%s' has been encoded in encodedCat.jpg\n", message);

        System.out.format("We decode the message: %s", mg2.decode());
    }
}
