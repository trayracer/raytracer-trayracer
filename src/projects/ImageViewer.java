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
 * This class is an javafx-application to show a chosen image.
 *
 * @author Marie Hennings
 */
public class ImageViewer extends Application {

    /**
     * Array of possible image file extensions
     */
    private final String[] filesExtensions = new String[]{"*.bmp", "*.gif", "*.jpg", "*.jpeg", "*.png"};

    /**
     * Main method calling javafx-application main
     *
     * @param args Starting arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method opens a filechooser and shows the chosen image in a window with the images size
     *
     * @param primaryStage Main Stage of the program
     */
    public void start(final Stage primaryStage) {
        try {
            //choose file with an image extension
            final FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Images", filesExtensions));
            final File imageFile = fileChooser.showOpenDialog(primaryStage);

            //if a file has been chosen load image, close otherwise
            Image image;
            if (imageFile != null) {
                image = new Image("file:" + imageFile.getAbsolutePath());
            } else {
                //primaryStages needs to be shown before closing is possible
                primaryStage.show();
                primaryStage.close();
                return;
            }

            // show image in ImageView and put it in a pane and on a scene with the same size
            final ImageView imgViewer = new ImageView(image);
            final Pane pane = new Pane();
            pane.getChildren().add(imgViewer);
            final Scene scene = new Scene(pane, image.getWidth(), image.getHeight());

            //set scene, title and avoid resizability
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle(imageFile.getName());

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
