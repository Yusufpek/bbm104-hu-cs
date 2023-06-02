import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.stage.Stage;

public abstract class DuckApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        DuckScene scene = new DuckScene();
        primaryStage.setTitle(Texts.WINDOW_TEXT);
        primaryStage.getIcons().add(new CustomImage("1", Images.FAVICON));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setMaxHeight(ScreenSize.SCREEN_HEIGHT);
        primaryStage.setMaxWidth(ScreenSize.SCREEN_WIDTH);
        primaryStage.show();

    }
}