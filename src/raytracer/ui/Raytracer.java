package raytracer.ui;

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
import raytracer.camera.Camera;
import raytracer.geometry.*;
import raytracer.material.SingleColorMaterial;
import raytracer.math.Point3;
import raytracer.scene.*;
import raytracer.scene.Torus;
import raytracer.texture.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is a FX Ray Tracer application. It has a menu bar with several options for displaying different scenes
 * and selecting several image dimensions and the option to save the generated image.
 *
 * @author Marie Hennings & Oliver Kniejski
 */
public class Raytracer extends Application {
    /**
     * Width of the image and window content
     */
    private int width = 640;
    /**
     * Height of the image and window content
     */
    private int height = 480;
    /**
     * World which will be shown
     */
    private World world;
    /**
     * Camera that is used, perspective or orthographic
     */
    private Camera cam;

    /**
     * ImageView shows the Image
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

    @Override
    public void start(final Stage primaryStage) {
        final BorderPane pane = new BorderPane();
        pane.setTop(createMenuBar(primaryStage));
        pane.setCenter(view);

        //starts with chosen Scene
//        width = 800;
//        height = 400;
        loadScene(new Ex3v3());
//        addAxes(new Point3(0, 0, 0), 1);

        final Scene scene = new Scene(pane, width, height);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tray Racer v0.20");
        primaryStage.show();
    }

    /**
     * This method calls the drawWorld method and converts the BufferedImage to a FXImage
     * which will be placed in the ImageView.
     */
    private void generate() {
        Image image = SwingFXUtils.toFXImage(drawWorld(), null);
        view.setImage(image);
    }

    /**
     * This method draws the world for the perspective of the given camera and saves it in a bufferedimage.
     *
     * @return The BufferedImage the world is drawn in.
     */
    private BufferedImage drawWorld() {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Hit hit = world.hit(cam.rayFor(width, height, x, (height - 1) - y));

                if (hit != null) {
                    bufferedImage.setRGB(x, y, hit.geo.material.colorFor(hit, world).getRGB());
                } else {
                    bufferedImage.setRGB(x, y, world.backgroundColor.getRGB());
                }
            }
        }
        return bufferedImage;
    }

    /**
     * This method saves the current image as a png or jpg.
     *
     * @param stage The stage.
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
     * This method creates a menu-bar and it's functionality for the raytracer.
     *
     * @param primaryStage The primary stage.
     * @return The MenuBar.
     */
    private MenuBar createMenuBar(Stage primaryStage) {
        final MenuBar menubar = new MenuBar();

        // File Menu
        final Menu filemenu = new Menu("File");
        final MenuItem save = new MenuItem("Save...");
        save.setOnAction(e -> saveImage(primaryStage));
        filemenu.getItems().add(save);
        menubar.getMenus().add(filemenu);

        // Scene Menu
        final Menu scenemenu = new Menu("Scene");

        final MenuItem plane = new MenuItem("Ex2 Plane");
        plane.setOnAction(e -> loadScene(new Ex2Plane()));
        scenemenu.getItems().add(plane);

        final MenuItem sphere = new MenuItem("Ex2 Sphere");
        sphere.setOnAction(e -> loadScene(new Ex2Sphere()));
        scenemenu.getItems().add(sphere);

        final MenuItem box = new MenuItem("Ex2 Box");
        box.setOnAction(e -> loadScene(new Ex2Box()));
        scenemenu.getItems().add(box);

        final MenuItem triangle = new MenuItem("Ex2 Triangle");
        triangle.setOnAction(e -> loadScene(new Ex2Triangle()));
        scenemenu.getItems().add(triangle);

        final MenuItem twoSpheresPerspective = new MenuItem("Ex2 Two Spheres Perspective");
        twoSpheresPerspective.setOnAction(e -> loadScene(new Ex2SpheresPerspective()));
        scenemenu.getItems().add(twoSpheresPerspective);

        final MenuItem twoSpheresOrthographic = new MenuItem("Ex2 Two Spheres Orthographic");
        twoSpheresOrthographic.setOnAction(e -> loadScene(new Ex2SpheresOrthographic()));
        scenemenu.getItems().add(twoSpheresOrthographic);

        final MenuItem ex3v1 = new MenuItem("Ex3 v1");
        ex3v1.setOnAction(e -> loadScene(new Ex3v1()));
        scenemenu.getItems().add(ex3v1);

        final MenuItem ex3v2 = new MenuItem("Ex3 v2");
        ex3v2.setOnAction(e -> loadScene(new Ex3v2()));
        scenemenu.getItems().add(ex3v2);

        final MenuItem ex3v3 = new MenuItem("Ex3 v3");
        ex3v3.setOnAction(e -> loadScene(new Ex3v3()));
        scenemenu.getItems().add(ex3v3);

        final MenuItem ex3v4 = new MenuItem("Ex3 v4");
        ex3v4.setOnAction(e -> loadScene(new Ex3v4()));
        scenemenu.getItems().add(ex3v4);

        final MenuItem ex3v5 = new MenuItem("Ex3 v5");
        ex3v5.setOnAction(e -> loadScene(new Ex3v5()));
        scenemenu.getItems().add(ex3v5);

        final MenuItem ex3v6 = new MenuItem("Ex3 v6");
        ex3v6.setOnAction(e -> loadScene(new Ex3v6()));
        scenemenu.getItems().add(ex3v6);

        final MenuItem okArt = new MenuItem("Abstrakte Kunst");
        okArt.setOnAction(e -> loadScene(new OkAbstractArt()));
        scenemenu.getItems().add(okArt);

        final MenuItem cylinder = new MenuItem("Cylinder (1min!)");
        cylinder.setOnAction(e -> loadScene(new Cylinder()));
        scenemenu.getItems().add(cylinder);

        final MenuItem torus = new MenuItem("Torus");
        torus.setOnAction(e -> loadScene(new Torus()));
        scenemenu.getItems().add(torus);

        final MenuItem cone = new MenuItem("Cone");
        cone.setOnAction(e -> loadScene(new Cone()));
        scenemenu.getItems().add(cone);

        final MenuItem stereoTest = new MenuItem("Stereo Test");
        stereoTest.setOnAction(e -> {
            setDimensions(primaryStage, 800, 400);
            loadScene(new StereoTest());
        });
        scenemenu.getItems().add(stereoTest);

        final MenuItem okCity = new MenuItem("Stadt mit Invasor (3min!)");
        okCity.setOnAction(e -> loadScene(new OkCity()));
        scenemenu.getItems().add(okCity);

        menubar.getMenus().add(scenemenu);

        // Dimension Menu
        final Menu dimensionsMenu = new Menu("Dimensions");

        final MenuItem d640 = new MenuItem("640 x 480");
        d640.setOnAction(e -> setDimensions(primaryStage, 640, 480));
        dimensionsMenu.getItems().add(d640);

        final MenuItem d800 = new MenuItem("800 x 600");
        d800.setOnAction(e -> setDimensions(primaryStage, 800, 600));
        dimensionsMenu.getItems().add(d800);

        final MenuItem d1024 = new MenuItem("1024 x 768");
        d1024.setOnAction(e -> setDimensions(primaryStage, 1024, 768));
        dimensionsMenu.getItems().add(d1024);

        final MenuItem d1280 = new MenuItem("1280 x 960");
        d1280.setOnAction(e -> setDimensions(primaryStage, 1280, 960));
        dimensionsMenu.getItems().add(d1280);

        final MenuItem d1440 = new MenuItem("1440 x 900 (Olli's Monitor)");
        d1440.setOnAction(e -> setDimensions(primaryStage, 1440, 900));
        dimensionsMenu.getItems().add(d1440);

        menubar.getMenus().add(dimensionsMenu);

        return menubar;
    }

    /**
     * This method loads a RayTracer scene.
     * @param scene The scene to load.
     */
    private void loadScene(RtScene scene){
        cam = scene.getCam();
        world = scene.getWorld();
        generate();
    }

    /**
     * This method sets the image and stage size.
     * @param stage The stage.
     * @param width The width.
     * @param height The height.
     */
    private void setDimensions(Stage stage, int width, int height){
        this.width = width;
        this.height = height;
        generate();
        stage.setWidth(width);
        stage.setHeight(height);
    }

    /**
     * This method adds 6 colored Triangles representing the positive coordinate systems directions
     * on the Point p with the length f to the world. Red indicating x, green indicating y, blue indicating z.
     */
    private void addAxes(final Point3 p, final double f) {
        double b = f / 10;
        world.addGeometry(new Triangle(new Point3(p.x + f, p.y, p.z), new Point3(p.x, p.y + b, p.z), new Point3(p.x, p.y - b, p.z), new SingleColorMaterial(new Color(1, 0, 0))));
        world.addGeometry(new Triangle(new Point3(p.x + f, p.y, p.z), new Point3(p.x, p.y, p.z + b), new Point3(p.x, p.y, p.z - b), new SingleColorMaterial(new Color(1, 0, 0))));
        world.addGeometry(new Triangle(new Point3(p.x, p.y + f, p.z), new Point3(p.x + b, p.y, p.z), new Point3(p.x - b, p.y, p.z), new SingleColorMaterial(new Color(0, 1, 0))));
        world.addGeometry(new Triangle(new Point3(p.x, p.y + f, p.z), new Point3(p.x, p.y, p.z + b), new Point3(p.x, p.y, p.z - b), new SingleColorMaterial(new Color(0, 1, 0))));
        world.addGeometry(new Triangle(new Point3(p.x, p.y, p.z + f), new Point3(p.x + b, p.y, p.z), new Point3(p.x - b, p.y, p.z), new SingleColorMaterial(new Color(0, 0, 1))));
        world.addGeometry(new Triangle(new Point3(p.x, p.y, p.z + f), new Point3(p.x, p.y + b, p.z), new Point3(p.x, p.y - b, p.z), new SingleColorMaterial(new Color(0, 0, 1))));
        generate();
    }
}
