import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.stage.Stage;

public abstract class CustomApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        DuckScene scene = new DuckScene();
        primaryStage.setTitle(Texts.WINDOW_TEXT);
        primaryStage.getIcons().add(new CustomImage("1", Images.FAVICON));
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}