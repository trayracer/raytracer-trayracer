package projects;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageSaver extends Application {

    private int width = 640;
    private int height = 480;
    private ImageView view = new ImageView();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            refreshImage();

            final Pane pane = new Pane();
            pane.getChildren().add(view);
            final Scene scene = new Scene(pane, width, height);
            stage.setScene(scene);
            stage.setTitle("Image Saver");
            stage.show();

            scene.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    width = newValue.intValue();
                    refreshImage();
                }
            });

            scene.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    height = newValue.intValue();
                    refreshImage();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshImage() {
        Image image = SwingFXUtils.toFXImage(getThinRedLine(width, height), null);
        view.setImage(image);
    }

    private BufferedImage getThinRedLine(final int width, final int height) {

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                bufferedImage.setRGB(j, i, new Color(0, 0, 0).getRGB());
            }
        }

        for (int i = 0; i < Math.min(width, height); i++) {
            bufferedImage.setRGB(i, i, new Color(255, 0, 0).getRGB());
        }

        return bufferedImage;
    }

}