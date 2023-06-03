import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

/**
 * A custom image class that extends the JavaFX Image class.
 */
public class CustomImage extends Image {

    /**
     * Constructs a CustomImage object with the specified name and path.
     *
     * @param name the name of the image
     * @param path the file path of the image
     * @throws FileNotFoundException if the image file is not found
     */
    public CustomImage(String name, String path) throws FileNotFoundException {
        super(new FileInputStream(Images.getName(name, path)));
    }
}