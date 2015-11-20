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
import raytracer.camera.OrthographicCamera;
import raytracer.camera.PerspectiveCamera;
import raytracer.camera.StereoCamera;
import raytracer.geometry.*;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
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

        //starts with the plane scene
        planeScene();
        generate();

        final Scene scene = new Scene(pane, width, height);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tray Racer v0.10");
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
                Hit hit = world.hit(cam.rayFor(width, height, x, height - y));

                if (hit != null) {
                    bufferedImage.setRGB(x, y, hit.geo.color.getRGB());
                } else {
                    bufferedImage.setRGB(x, y, world.backgroundColor.getRGB());
                }
            }
        }
        return bufferedImage;
    }

    /**
     * Sets a perspective camera and adds a plane to the world.
     */
    private void planeScene() {
        cam = new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0));
        world.addGeometry(new Plane(new Point3(0, -1, 0), new Normal3(0, 1, 0), new Color(0, 1, 0)));
    }

    /**
     * Sets a perspective camera and adds a sphere to the world.
     */
    private void sphereScene() {
        cam = new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0));
        world.addGeometry(new Sphere(new Point3(0, 0, -3), 0.5, new Color(1, 0, 0)));
    }

    /**
     * Sets a perspective camera and adds a box to the world.
     */
    private void boxScene() {
        cam = new PerspectiveCamera(new Point3(3, 3, 3), new Vector3(-3, -3, -3), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0));
        world.addGeometry(new AxisAlignedBox(new Point3(-0.5, 0, -0.5), new Point3(0.5, 1, 0.5), new Color(0, 0, 1)));
    }

    /**
     * Sets a perspective camera and adds a triangle to the world.
     */
    private void triangleScene() {
        cam = new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0));
        world.addGeometry(new Triangle(new Point3(-0.5, 0.5, -3), new Point3(0.5, 0.5, -3), new Point3(0.5, -0.5, -3), new Color(1, 0, 1)));
    }

    /**
     * Sets a perspective camera and adds two spheres to the world.
     */
    private void twoSpheresPerspective() {
        cam = new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0));
        world.addGeometry(new Sphere(new Point3(-1, 0, -3), 0.5, new Color(1, 0, 0)));
        world.addGeometry(new Sphere(new Point3(1, 0, -6), 0.5, new Color(1, 0, 0)));
    }

    /**
     * Sets a orthographic camera and adds two spheres to the world.
     */
    private void twoSpheresOrthographic() {
        cam = new OrthographicCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), 3);
        world = new World(new Color(0, 0, 0));
        world.addGeometry(new Sphere(new Point3(-1, 0, -3), 0.5, new Color(1, 0, 0)));
        world.addGeometry(new Sphere(new Point3(1, 0, -6), 0.5, new Color(1, 0, 0)));
    }

    /**
     * This is a method for additional testing
     *
     * @param test testcase
     */
    private void testScene(final boolean test) {
        if (test) {
            cam = new OrthographicCamera(new Point3(0, 0, 100), new Vector3(0, 0, -1), new Vector3(0, 1, 0), 100);
        }
        if (!test) {
            cam = new PerspectiveCamera(new Point3(0, 0, 100), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4);
        }
        world = new World(new Color(0, 0, 0));
        for (int i = 0; i < 100; i++) world.addGeometry(randomSphere());
        for (int i = 0; i < 100; i++) world.addGeometry(randomTriangle());
    }

    /**
     * This method creates a test scene with a cylinder.
     */
    private void cylinder() {
        cam = new PerspectiveCamera(new Point3(5, 5, 5), new Vector3(-1, -1, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0.2, 0.2, 0.2));
        world.addGeometry(new ZAxisAlignedCylinder(new Point3(0, -3, 0), -5, 1, new Color(0, 1, 0)));
        addAxes();
    }

    /**
     * This method creates a test scene for the stereoscopic camera.
     */
    private void stereoTest() {
        width = 500;
        height = 250;
        cam = new StereoCamera(new Point3(25, 20, 40), new Vector3(-25, -17, -40), new Vector3(0, 1, 0), Math.PI / 3.3, 6, true);
        world = new World(new Color(0.3, 0.3, 0.3));

        world.addGeometry(new Sphere(new Point3(0, 0, 0), 2, new Color(1, 1, 1)));
        world.addGeometry(new Sphere(new Point3(4, 0, 0), 2, new Color(1, 0, 0)));
        world.addGeometry(new Sphere(new Point3(0, 4, 0), 2, new Color(0, 1, 0)));
        world.addGeometry(new Sphere(new Point3(0, 0, 4), 2, new Color(0, 0, 1)));
        world.addGeometry(new Sphere(new Point3(5, 5, 0), 2, new Color(1, 1, 0)));
        world.addGeometry(new Sphere(new Point3(5, 0, 5), 2, new Color(1, 0, 1)));
        world.addGeometry(new Sphere(new Point3(0, 5, 5), 2, new Color(0, 1, 1)));
        world.addGeometry(new Triangle(new Point3(-1, -1, -1), new Point3(-1, 10, -1), new Point3(-1, -1, 10), new Color(0, 0.2, 0.2)));
        world.addGeometry(new Triangle(new Point3(-1, -1, -1), new Point3(10, -1, -1), new Point3(-1, -1, 10), new Color(0.2, 0, 0.2)));
        world.addGeometry(new Triangle(new Point3(-1, -1, -1), new Point3(10, -1, -1), new Point3(-1, 10, -1), new Color(0.2, 0.2, 0)));
        world.addGeometry(new Triangle(new Point3(-2, -2, -2), new Point3(-2, 11, -2), new Point3(-2, -2, 11), new Color(0, 0.4, 0.4)));
        world.addGeometry(new Triangle(new Point3(-2, -2, -2), new Point3(11, -2, -2), new Point3(-2, -2, 11), new Color(0.4, 0, 0.4)));
        world.addGeometry(new Triangle(new Point3(-2, -2, -2), new Point3(11, -2, -2), new Point3(-2, 11, -2), new Color(0.4, 0.4, 0)));
        world.addGeometry(new Triangle(new Point3(-3, -3, -3), new Point3(-3, 12, -3), new Point3(-3, -3, 12), new Color(0, 0.6, 0.6)));
        world.addGeometry(new Triangle(new Point3(-3, -3, -3), new Point3(12, -3, -3), new Point3(-3, -3, 12), new Color(0.6, 0, 0.6)));
        world.addGeometry(new Triangle(new Point3(-3, -3, -3), new Point3(12, -3, -3), new Point3(-3, 12, -3), new Color(0.6, 0.6, 0)));
        world.addGeometry(new Sphere(new Point3(15, 0, 0), 2, new Color(1, 0, 0)));
        world.addGeometry(new Sphere(new Point3(0, 15, 0), 2, new Color(0, 1, 0)));
        world.addGeometry(new Sphere(new Point3(0, 0, 15), 2, new Color(0, 0, 1)));
        world.addGeometry(new Sphere(new Point3(17, 17, 17), 2, new Color(1, 1, 1)));
    }

    /**
     * This method adds 6 colored Triangles representing the coordinate systems axes on the 0-Point to the world.
     */
    private void addAxes() {
        world.addGeometry(new Triangle(new Point3(1, 0, 0), new Point3(0, 0.01, 0), new Point3(0, -0.01, 0), new Color(1, 0, 0)));
        world.addGeometry(new Triangle(new Point3(1, 0, 0), new Point3(0, 0, 0.01), new Point3(0, 0, -0.01), new Color(1, 0, 0)));
        world.addGeometry(new Triangle(new Point3(0, 1, 0), new Point3(0.01, 0, 0), new Point3(-0.01, 0, 0), new Color(0, 1, 0)));
        world.addGeometry(new Triangle(new Point3(0, 1, 0), new Point3(0, 0, 0.01), new Point3(0, 0, -0.01), new Color(0, 1, 0)));
        world.addGeometry(new Triangle(new Point3(0, 0, 1), new Point3(0.01, 0, 0), new Point3(-0.01, 0, 0), new Color(0, 0, 1)));
        world.addGeometry(new Triangle(new Point3(0, 0, 1), new Point3(0, 0.01, 0), new Point3(0, -0.01, 0), new Color(0, 0, 1)));
    }

    /**
     * This method generates a rather random sphere. testing purposes only, see inside for details.
     *
     * @return a random sphere
     */
    private Sphere randomSphere() {
        return new Sphere(new Point3(50 - Math.random() * 100, 50 - Math.random() * 100, -Math.random() * 50), Math.random() * 10, new Color(Math.random(), Math.random(), Math.random()));
    }

    /**
     * This method generates a rather random triangle. testing purposes only, see inside for details.
     *
     * @return a random triangle
     */
    private Triangle randomTriangle() {
        return new Triangle(new Point3(50 - Math.random() * 100, 50 - Math.random() * 100, -Math.random() * 50), new Point3(50 - Math.random() * 100, 50 - Math.random() * 100, -Math.random() * 50), new Point3(50 - Math.random() * 100, 50 - Math.random() * 100, -Math.random() * 50), new Color(Math.random(), Math.random(), Math.random()));
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
        final Menu filemenu = new Menu("File");
        final MenuItem save = new MenuItem("Save...");
        save.setOnAction(e -> saveImage(primaryStage));
        filemenu.getItems().add(save);

        final Menu scenemenu = new Menu("Scene");
        final MenuItem plane = new MenuItem("plane");
        plane.setOnAction(e -> {
            planeScene();
            generate();
        });
        final MenuItem sphere = new MenuItem("sphere");
        sphere.setOnAction(e -> {
            sphereScene();
            generate();
        });
        final MenuItem box = new MenuItem("box");
        box.setOnAction(e -> {
            boxScene();
            generate();
        });
        final MenuItem triangle = new MenuItem("triangle");
        triangle.setOnAction(e -> {
            triangleScene();
            generate();
        });
        final MenuItem twoSpheresPerspective = new MenuItem("twoSpheresPerspective");
        twoSpheresPerspective.setOnAction(e -> {
            twoSpheresPerspective();
            generate();
        });
        final MenuItem twoSpheresOrthographic = new MenuItem("twoSpheresOrthographic");
        twoSpheresOrthographic.setOnAction(e -> {
            twoSpheresOrthographic();
            generate();
        });
        final MenuItem test1 = new MenuItem("orthographic test");
        test1.setOnAction(e -> {
            testScene(true);
            generate();
        });
        final MenuItem test2 = new MenuItem("perspective test");
        test2.setOnAction(e -> {
            testScene(false);
            generate();
        });
        final MenuItem cylinder = new MenuItem("cylinder");
        cylinder.setOnAction(e -> {
            cylinder();
            generate();
        });
        final MenuItem testArt = new MenuItem("stereoTest");
        testArt.setOnAction(e -> {
            stereoTest();

            generate();
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
        });
        scenemenu.getItems().add(plane);
        scenemenu.getItems().add(sphere);
        scenemenu.getItems().add(box);
        scenemenu.getItems().add(triangle);
        scenemenu.getItems().add(twoSpheresPerspective);
        scenemenu.getItems().add(twoSpheresOrthographic);
        scenemenu.getItems().add(test1);
        scenemenu.getItems().add(test2);
        scenemenu.getItems().add(cylinder);
        scenemenu.getItems().add(testArt);

        final Menu dimensionsMenu = new Menu("Dimensions");
        final MenuItem d640 = new MenuItem("640 x 480");
        d640.setOnAction(e -> {
            width = 640;
            height = 480;
            generate();
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
        });
        final MenuItem d800 = new MenuItem("800 x 600");
        d800.setOnAction(e -> {
            width = 800;
            height = 600;
            generate();
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
        });
        final MenuItem d1024 = new MenuItem("1024 x 768");
        d1024.setOnAction(e -> {
            width = 1024;
            height = 768;
            generate();
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
        });
        final MenuItem d1280 = new MenuItem("1280 x 960");
        d1280.setOnAction(e -> {
            width = 1280;
            height = 960;
            generate();
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
        });
        dimensionsMenu.getItems().add(d640);
        dimensionsMenu.getItems().add(d800);
        dimensionsMenu.getItems().add(d1024);
        dimensionsMenu.getItems().add(d1280);

        menubar.getMenus().add(filemenu);
        menubar.getMenus().add(scenemenu);
        menubar.getMenus().add(dimensionsMenu);

        return menubar;
    }

}
