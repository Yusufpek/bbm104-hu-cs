import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class CustomImage extends Image {

    public CustomImage(String name, String path) throws FileNotFoundException {
        super(new FileInputStream(Images.getName(name, path)));
    }

}
