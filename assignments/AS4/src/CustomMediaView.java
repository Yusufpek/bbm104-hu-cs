import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;

public class CustomMediaView extends MediaView {
    MediaPlayer mediaPlayer;

    CustomMediaView(String name) {
        super();
        System.out.println("Effects: " + name);
        Media media = new Media(new File(Effects.getEffect(name)).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(DuckHunt.VOLUME);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        this.setMediaPlayer(mediaPlayer);
    }

    CustomMediaView(String name, int repeatCount) {
        this(name);
        mediaPlayer.setCycleCount(repeatCount);
        this.setMediaPlayer(mediaPlayer);
    }

    void playMedia() {
        if (mediaPlayer.getStatus() == Status.PAUSED)
            mediaPlayer.play();
    }

    void pauseMedia() {
        if (mediaPlayer.getStatus() == Status.PLAYING)
            mediaPlayer.pause();
    }

    void setOnfinished(Function func) {
        mediaPlayer.setOnEndOfMedia(() -> func.onFinished());
    }
}
