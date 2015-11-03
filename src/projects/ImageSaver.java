package projects;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

            final BorderPane pane = new BorderPane();
            pane.setCenter(view);
            final Scene scene = new Scene(pane, width, height);
            stage.setScene(scene);
            stage.setTitle("Image Saver");

            MenuBar menubar = new MenuBar();
            Menu filemenu = new Menu("File");
            MenuItem save = new MenuItem("Save...");
            save.setOnAction(e -> saveImage(stage));

            filemenu.getItems().add(save);
            menubar.getMenus().add(filemenu);

            pane.setTop(menubar);
            stage.show();

            scene.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    int cachewidth = newValue.intValue();
                    if (cachewidth > 0) {
                        width = cachewidth;
                    }
                    refreshImage();
                }
            });

            scene.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    int cacheheight = newValue.intValue();
                    if (cacheheight > 0) {
                        height = cacheheight;
                    }
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

    private void saveImage(Stage stage) {
        FileChooser fileDialog = new FileChooser();
        fileDialog.setTitle("Save");
        fileDialog.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("JPG Image", "*.jpg"));

        File file = fileDialog.showSaveDialog(stage);

        if (file != null) {
            String fileExt = fileDialog.getSelectedExtensionFilter().getExtensions().get(0);

            try {
                ImageIO.write(getThinRedLine(width, height), fileExt.split("\\.")[1], file);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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