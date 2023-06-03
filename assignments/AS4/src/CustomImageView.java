import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Custom Image View class extends the Image View (from FX).
public class CustomImageView extends ImageView {
    /**
     * Constructs a new instance of CustomImageView with no image.
     */
    public CustomImageView() {
        super();
    }

    /**
     * Constructs a new instance of CustomImageView with the specified image.
     *
     * @param name The name of the image.
     * @param path The path to the image file.
     * @throws FileNotFoundException if the image file is not found.
     */
    public CustomImageView(String name, String path) throws FileNotFoundException {
        super();
        Image image = new CustomImage(name, path);
        this.setImage(image);
        setSize(image);
    }

    /**
     * Constructs a new instance of CustomImageView with the specified image.
     *
     * @param duckImages The image to be displayed.
     * @throws FileNotFoundException if the image file is not found.
     */
    public CustomImageView(Image duckImages) throws FileNotFoundException {
        super();
        this.setImage(duckImages);
        setSize(duckImages);
    }

    /**
     * Sets the size of the image view based on the dimensions of the provided
     * image.
     *
     * @param image The image whose dimensions are used to set the size of the image
     *              view.
     */
    public void setSize(Image image) {
        this.setFitHeight(DuckHunt.SCALE * image.getHeight());
        this.setFitWidth(DuckHunt.SCALE * image.getWidth());
    }
}
