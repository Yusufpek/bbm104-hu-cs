import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomImageView extends ImageView {
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

    private void setSize(Image image) {
        this.setFitHeight(ScreenSize.SCALE * image.getHeight());
        this.setFitWidth(ScreenSize.SCALE * image.getWidth());
    }
}
