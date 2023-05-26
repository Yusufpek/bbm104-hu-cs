import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomImageView extends ImageView {
    public CustomImageView() {
        super();
    }

    public CustomImageView(String name, String path) throws FileNotFoundException {
        super();
        Image image = new CustomImage(name, path);
        this.setImage(image);
        setSize(image);
    }

    public CustomImageView(Image duckImages) throws FileNotFoundException {
        super();
        this.setImage(duckImages);
        setSize(duckImages);
    }

    public void setSize(Image image) {
        this.setFitHeight(DuckHunt.SCALE * image.getHeight());
        this.setFitWidth(DuckHunt.SCALE * image.getWidth());
    }
}
