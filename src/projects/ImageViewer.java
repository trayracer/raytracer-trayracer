package projects;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;

/**
 * @author Marie Hennings
 */
public class ImageViewer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(final Stage primaryStage) {
        try {
            Scene scene = showImage(chooseImage(primaryStage));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Image chooseImage(final Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        //TODO Extensionfilter
        //fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Bild Dateien", "*.jpg", "*.png", "*.bmp", "*.gif", "*.jpeg"));

        File imageFile = fileChooser.showOpenDialog(primaryStage);

        if(imageFile != null) {
            return new Image("file:" + File.separator + File.separator + File.separator + imageFile.getPath());
        }
        return null;
    }

    private Scene showImage(final Image image) {
        double height = image.getHeight();
        double width = image.getWidth();
        ImageView imgViewer = new ImageView(image);
        Pane pane = new Pane();
        pane.getChildren().add(imgViewer);
        return new Scene(pane, width, height);
    }

}
