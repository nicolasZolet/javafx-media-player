package org.openjfx;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    private MediaView mediaView;

    private static final String PATH_PROJECT = System.getProperty("user.dir");
    private static final String PATH_MEDIAS = PATH_PROJECT + "/src/main/resources/media/";

    private final String[] VIDEOS_SAMPLE = new String[]{
            PATH_MEDIAS + "ForBiggerBlazes.mp4",
            PATH_MEDIAS + "WeAreGoingOnBullrun.mp4"
    };

    @Override
    public void start(Stage stage) {
        stage.setTitle("JavaFx Media Player");

        mediaView = new MediaView();

        final DoubleProperty width = mediaView.fitWidthProperty();
        final DoubleProperty height = mediaView.fitHeightProperty();

        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

        mediaView.setPreserveRatio(true);

        StackPane root = new StackPane();
        root.getChildren().add(mediaView);

        final Scene scene = new Scene(root, 960, 540);
        scene.setFill(Color.BLACK);

        stage.setScene(scene);
//        stage.setFullScreen(true);
        stage.show();

        play(VIDEOS_SAMPLE[currentMedia]);

        looper(3000);

    }

    int currentMedia = 0;
    private void looper(int sleepTime){
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        currentMedia = currentMedia+1 >= VIDEOS_SAMPLE.length ? 0 : currentMedia+1;
                        play(VIDEOS_SAMPLE[currentMedia]);
                        looper(3000);
                    }
                },
                sleepTime
        );
    }

    private static MediaPlayer mediaPlayer;
    public void play (String videoPath){
        mediaPlayer = new MediaPlayer(new Media(getMediaPath(videoPath)));
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        mediaPlayer.setMute(true);
    }

    public String getMediaPath(String path){
        File file = new File(path);
        return file.toURI().toString();
    }

}