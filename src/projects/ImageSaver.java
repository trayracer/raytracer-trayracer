package projects;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class draws a BufferedImage pixel by pixel and displays it in a JavaFX-ImageView.
 * It is possible to save the Image in png or jpg.
 *
 * @author Oliver Kniejski, Marie Hennings, Steven Sobkowski
 */
public class ImageSaver extends Application {
    /**
     * Width of the image and window content
     */
    private int width = 640;
    /**
     * Height of the image and window content
     */
    private int height = 480;
    /**
     * The ImageView shows the Image.
     */
    private final ImageView view = new ImageView();

    /**
     * Main method calling javafx-application main
     *
     * @param args Starting arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method opens a window with a menu bar in which the ImageView and the Image is placed
     *
     * @param stage Main stage of the program
     */
    @Override
    public void start(final Stage stage) {
        try {
            refreshImage();

            final BorderPane pane = new BorderPane();
            pane.setCenter(view);
            final Scene scene = new Scene(pane, width, height);
            stage.setScene(scene);
            stage.setTitle("Image Saver");

            //the menubar
            final MenuBar menubar = new MenuBar();
            //the menu
            final Menu filemenu = new Menu("File");
            //the menuitem
            final MenuItem save = new MenuItem("Save...");
            save.setOnAction(e -> saveImage(stage));

            filemenu.getItems().add(save);
            menubar.getMenus().add(filemenu);

            pane.setTop(menubar);
            stage.show();

            // a Listener, which observes the width of the window and sets the new value to the width-variable and then reloads the image
            scene.widthProperty().addListener((observable, oldValue, newValue) -> {
                final int cachewidth = newValue.intValue();
                if (cachewidth > 0) {
                    width = cachewidth;
                }
                refreshImage();
            });

            // a Listener, which observes the height of the window and sets the new value to the height-variable and then reloads the image
            scene.heightProperty().addListener((observable, oldValue, newValue) -> {
                int cacheheight = newValue.intValue();
                if (cacheheight > 0) {
                    height = cacheheight;
                }
                refreshImage();
            });

        } catch (Exception e) {
            // Suppress exception
        }
    }

    /**
     * Converts the BufferedImage to a FXImage and sets it to the view
     */
    private void refreshImage() {
        Image image = SwingFXUtils.toFXImage(getThinRedLine(width, height), null);
        view.setImage(image);
    }

    /**
     * Opens a save-dialog which saves the current image as a jpg or png-file in the file system.
     *
     * @param stage the given stage in which the dialog is shown
     */
    private void saveImage(final Stage stage) {
        FileChooser fileDialog = new FileChooser();
        fileDialog.setTitle("Save");
        fileDialog.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("JPG Image", "*.jpg"));

        File file = fileDialog.showSaveDialog(stage);

        if (file != null) {
            String fileExt = fileDialog.getSelectedExtensionFilter().getExtensions().get(0);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(view.getImage(), null), fileExt.split("\\.")[1], file);
            } catch (IOException e) {
                // Suppress exception
            }
        }

    }

    /**
     * Creates a new BufferedImage with the given height and width.
     * It then draws the background black- pixel by pixel- and after that it draws a diagonal red line from the left upper corner.
     *
     * @param width  the given width for the BufferedImage
     * @param height the given height for the BufferedImage
     * @return the drawn image
     */
    private BufferedImage getThinRedLine(final int width, final int height) throws IllegalArgumentException {
        if (width <= 0 || height <= 0) throw new IllegalArgumentException("Parameter must be greater than 0");

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bufferedImage.setRGB(x, y, new Color(0, 0, 0).getRGB());
            }
        }

        for (int i = 0; i < Math.min(width, height); i++) {
            bufferedImage.setRGB(i, i, new Color(255, 0, 0).getRGB());
        }

        return bufferedImage;
    }

}