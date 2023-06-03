import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * An abstract class representing a Duck Application.
 * Extends the FX Application class.
 */
public abstract class DuckApplication extends Application {

    /**
     * The entry point for the JavaFX application.
     * Initializes and displays the primary stage of the Duck Application.
     *
     * @param primaryStage the primary stage for the application
     * @throws FileNotFoundException if a file is not found
     */
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        DuckScene scene = new DuckScene();
        primaryStage.setTitle(Texts.WINDOW_TEXT);
        // Add the custom image to the icons of the primary stage
        primaryStage.getIcons().add(new CustomImage("1", Images.FAVICON));
        primaryStage.setScene(scene);
        // Disable resizing of the window
        primaryStage.setResizable(false);
        primaryStage.setMaxHeight(ScreenSize.SCREEN_HEIGHT);
        primaryStage.setMaxWidth(ScreenSize.SCREEN_WIDTH);
        primaryStage.show();
    }
}