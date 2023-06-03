import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;

/**
 * A Custom MediaView for playing media files.
 * Extends the MediaView class (from fx).
 */
public class CustomMediaView extends MediaView {
    MediaPlayer mediaPlayer;

    /**
     * Constructs a CustomMediaView object with the specified media file name.
     * Loads and plays the media file using a MediaPlayer.
     * Sets the volume, cycle count, and media player for the MediaView.
     *
     * @param name the name of the media file
     */
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

    /**
     * Constructs a CustomMediaView object with the specified media file name and
     * repeat count.
     * Calls the single-argument constructor to load and play the media file.
     * Sets the repeat count for the media player.
     *
     * @param name        the name of the media file
     * @param repeatCount the number of times to repeat the media
     */
    CustomMediaView(String name, int repeatCount) {
        this(name);
        mediaPlayer.setCycleCount(repeatCount);
        this.setMediaPlayer(mediaPlayer);
    }

    /**
     * Resumes playing the media if it is paused.
     */
    void playMedia() {
        if (mediaPlayer.getStatus() == Status.PAUSED)
            mediaPlayer.play();
    }

    /**
     * Pauses the media if it is playing.
     */
    void pauseMedia() {
        if (mediaPlayer.getStatus() == Status.PLAYING)
            mediaPlayer.pause();
    }

    /**
     * Sets the function to be called when the media playback reaches its end.
     *
     * @param func the function to be called when the media playback finishes
     */
    void setOnfinished(Function func) {
        mediaPlayer.setOnEndOfMedia(() -> func.onFinished());
    }
}
