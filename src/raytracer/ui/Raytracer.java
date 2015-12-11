package raytracer.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import raytracer.camera.Camera;
import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.*;
import raytracer.light.PointLight;
import raytracer.material.PhongMaterial;
import raytracer.material.Tracer;
import raytracer.math.Constants;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.scene.*;
import raytracer.scene.Torus;
import raytracer.texture.Color;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.*;

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
     * Pixels currently to render. Render helper.
     */
    private int pixels;
    /**
     * Pixels already rendered. Render helper.
     */
    private int counter = 0;
    /**
     * Executor service for multithreaded rendering.
     */
    private ExecutorService renderers;
    /**
     * Pixel buffer for rendering.
     */
    private int[] pixelBuffer;
    /**
     * The image.
     */
    private WritableImage wImage;


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

        final Scene scene = new Scene(pane, width, height);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tray Racer v0.20");

        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        loadScene(new MirrorHall());
    }

    /**
     * This method resets the Image. Useful for size changes and setting it black.
     */
    private void resetImage() {
        wImage = new WritableImage(width, height);
        pixelBuffer = new int[width * height];

        for (int pixel = 0; pixel < pixelBuffer.length; pixel++) {
            pixelBuffer[pixel] = new java.awt.Color(0, 0, 0, 255).getRGB();
        }

        wImage.getPixelWriter().setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixelBuffer, 0, 1);

        view.setImage(wImage);
    }

    /**
     * The raytracer.
     * This method draws the world for the perspective of the given camera and saves it in a bufferedimage and converts the BufferedImage to a FXImage
     * which will be placed in the ImageView.
     */
    private void raytrace() {
        if (renderers != null) renderers.shutdownNow();
        counter = 0;
        pixels = width * height;
        resetImage();

        int maxThreads = Runtime.getRuntime().availableProcessors();
        renderers = Executors.newFixedThreadPool(maxThreads - 2);

        // randomly distributed pixel rendering
        LinkedList<int[]> pixelsToDo = new LinkedList();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelsToDo.add(new int[]{x, y});
            }
        }
        Collections.shuffle(pixelsToDo);
        while (!pixelsToDo.isEmpty()) {
            int[] coords = pixelsToDo.remove(0);
            renderers.execute(pixelRenderer(coords[0], coords[1]));
        }

        // Line by line rendering
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                renderers.execute(pixelRenderer(x, y));
//            }
//        }

        renderers.shutdown();
        Thread thread = new Thread(refresher());
        thread.start();
    }

    /**
     * This method creates a Runnable for the renderer which calculates the specified pixel of the image.
     *
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @return The Runnable.
     */
    private Runnable pixelRenderer(final int x, final int y) {
        return () -> {
            Hit hit = world.hit(cam.rayFor(width, height, x, (height - 1) - y));
            Color c;
            if (hit != null) {
                c = hit.geo.material.colorFor(hit, world, new Tracer(Constants.RECOUNTER));
            } else {
                c = world.backgroundColor;
            }
            pixelBuffer[y * width + x] = c.getRGB();
            counter++;
        };
    }


    /**
     * This method creates a runnable that refreshes the UI while rendering.
     *
     * @return The Runnable.
     */
    private Runnable refresher() {
        return () -> {
            while (!renderers.isTerminated()) {
                wImage.getPixelWriter().setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixelBuffer, 0, width);
            }
            wImage.getPixelWriter().setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixelBuffer, 0, width);
        };
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
            } catch (IOException ignore) {
            }
        }
    }

    private void anaglyph(final Stage stage) {
        Image sourceImg = view.getImage();
        WritableImage targetImg = new WritableImage((int) (sourceImg.getWidth() / 2), (int) sourceImg.getHeight());
        for (int y = 0; y < sourceImg.getHeight(); y++) {
            for (int x = 0; x < sourceImg.getWidth() / 2; x++) {
                double lGreen = sourceImg.getPixelReader().getColor(x, y).getGreen();
                double lBlue = sourceImg.getPixelReader().getColor(x, y).getBlue();
                double rRed = sourceImg.getPixelReader().getColor(x + (int) (sourceImg.getWidth() / 2), y).getRed();

                targetImg.getPixelWriter().setColor(x, y, new javafx.scene.paint.Color(rRed, lGreen, lBlue, 1));
            }
        }

        this.width = (int) (sourceImg.getWidth() / 2);
        this.height = (int) sourceImg.getHeight();
        stage.setWidth(width);
        stage.setHeight(height);
        view.setImage(targetImg);
    }

    /**
     * This method loads and renders a .obj file.
     *
     * @param stage The FxStage.
     */
    private void renderObjFile(final Stage stage) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.obj"));
        final File objFile = fileChooser.showOpenDialog(stage);
        if (objFile != null) {
//            ShapeFromFile objGeo = new ShapeFromFile(objFile, new LambertMaterial(new Color(1, 1, 0)));
            ShapeFromFile objGeo = new ShapeFromFile(objFile, new PhongMaterial(new Color(1, 1, 0), new Color(1, 1, 1), 64));
            Vector3 geoMiddle = new Vector3(objGeo.boundingBox.lbf.x, objGeo.boundingBox.lbf.y, objGeo.boundingBox.lbf.z).mul(0.5).add(new Vector3(objGeo.boundingBox.run.x, objGeo.boundingBox.run.y, objGeo.boundingBox.run.z).mul(0.5));
            double objHeight = objGeo.boundingBox.lbf.sub(objGeo.boundingBox.run).magnitude;
            cam = new PerspectiveCamera(new Point3(objHeight, objHeight, objHeight), new Vector3(geoMiddle.x - objHeight, geoMiddle.y - objHeight, geoMiddle.z - objHeight), new Vector3(0, 1, 0), Math.PI / 4);
            world = new World(new Color(0.1, 0.1, 0.1), new Color(0.3, 0.3, 0.3));
            world.addGeometry(objGeo);
            world.addLight(new PointLight(new Color(0.5, 0.5, 0.5), new Point3(objHeight, objHeight, objHeight / 2), false));
            raytrace();
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

        final MenuItem open = new MenuItem("Open .obj");
        open.setOnAction(e -> renderObjFile(primaryStage));
        filemenu.getItems().add(open);

        final MenuItem anaglyph = new MenuItem("... anaglyph");
        anaglyph.setOnAction(e -> anaglyph(primaryStage));
        filemenu.getItems().add(anaglyph);

        menubar.getMenus().add(filemenu);

        // Scene Menu
        final Menu scenemenu = new Menu("Scene");

        final Menu ex2 = new Menu("Exercise 2");

        final MenuItem plane = new MenuItem("Plane");
        plane.setOnAction(e -> loadScene(new Ex2Plane()));
        ex2.getItems().add(plane);

        final MenuItem sphere = new MenuItem("Sphere");
        sphere.setOnAction(e -> loadScene(new Ex2Sphere()));
        ex2.getItems().add(sphere);

        final MenuItem box = new MenuItem("Box");
        box.setOnAction(e -> loadScene(new Ex2Box()));
        ex2.getItems().add(box);

        final MenuItem triangle = new MenuItem("Triangle");
        triangle.setOnAction(e -> loadScene(new Ex2Triangle()));
        ex2.getItems().add(triangle);

        final MenuItem twoSpheresPerspective = new MenuItem("Two Spheres Perspective");
        twoSpheresPerspective.setOnAction(e -> loadScene(new Ex2SpheresPerspective()));
        ex2.getItems().add(twoSpheresPerspective);

        final MenuItem twoSpheresOrthographic = new MenuItem("Two Spheres Orthographic");
        twoSpheresOrthographic.setOnAction(e -> loadScene(new Ex2SpheresOrthographic()));
        ex2.getItems().add(twoSpheresOrthographic);

        scenemenu.getItems().add(ex2);

        final Menu ex3 = new Menu("Exercise 3");

        final MenuItem ex3v1 = new MenuItem("Scene 1");
        ex3v1.setOnAction(e -> loadScene(new Ex3v1()));
        ex3.getItems().add(ex3v1);

        final MenuItem ex3v2 = new MenuItem("Scene 2");
        ex3v2.setOnAction(e -> loadScene(new Ex3v2()));
        ex3.getItems().add(ex3v2);

        final MenuItem ex3v3 = new MenuItem("Scene 3");
        ex3v3.setOnAction(e -> loadScene(new Ex3v3()));
        ex3.getItems().add(ex3v3);

        final MenuItem ex3v4 = new MenuItem("Scene 4");
        ex3v4.setOnAction(e -> loadScene(new Ex3v4()));
        ex3.getItems().add(ex3v4);

        final MenuItem ex3v5 = new MenuItem("Scene 5");
        ex3v5.setOnAction(e -> loadScene(new Ex3v5()));
        ex3.getItems().add(ex3v5);

        final MenuItem ex3v6 = new MenuItem("Scene 6");
        ex3v6.setOnAction(e -> loadScene(new Ex3v6()));
        ex3.getItems().add(ex3v6);

        scenemenu.getItems().add(ex3);

        final Menu ex4 = new Menu("Exercise 4");

        final MenuItem ex4spheres = new MenuItem("Spheres");
        ex4spheres.setOnAction(e -> loadScene(new Ex4Spheres()));
        ex4.getItems().add(ex4spheres);

        final MenuItem ex4box = new MenuItem("Box");
        ex4box.setOnAction(e -> loadScene(new Ex4Box()));
        ex4.getItems().add(ex4box);

        scenemenu.getItems().add(ex4);

        final Menu extra = new Menu("Additional Scenes");

        final MenuItem primitives = new MenuItem("Primitive");
        primitives.setOnAction(e -> loadScene(new Primitives()));
        extra.getItems().add(primitives);

        final MenuItem okArt = new MenuItem("Abstrakte Kunst");
        okArt.setOnAction(e -> loadScene(new OkAbstractArt()));
        extra.getItems().add(okArt);

        final MenuItem cylinder = new MenuItem("The Greeks!");
        cylinder.setOnAction(e -> loadScene(new Cylinder()));
        extra.getItems().add(cylinder);

        final MenuItem torus = new MenuItem("Lifebelt");
        torus.setOnAction(e -> loadScene(new Torus()));
        extra.getItems().add(torus);

        final MenuItem cone = new MenuItem("X-Mas Scene");
        cone.setOnAction(e -> loadScene(new Cone()));
        extra.getItems().add(cone);

        final MenuItem stereoTest = new MenuItem("Stereo Test");
        stereoTest.setOnAction(e -> {
            setDimensions(primaryStage, 800, 400);
            loadScene(new StereoTest());
        });
        extra.getItems().add(stereoTest);

        final MenuItem okCity = new MenuItem("Invader Over City (heavy!)");
        okCity.setOnAction(e -> loadScene(new OkCity()));
        extra.getItems().add(okCity);

        final MenuItem mirrorhall = new MenuItem("MirrorMirror");
        mirrorhall.setOnAction(e -> loadScene(new MirrorHall()));
        extra.getItems().add(mirrorhall);

        scenemenu.getItems().add(extra);

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
     *
     * @param scene The scene to load.
     */
    private void loadScene(RtScene scene) {
        cam = scene.getCam();
        world = scene.getWorld();
        raytrace();

    }

    /**
     * This method sets the image and stage size.
     *
     * @param stage  The stage.
     * @param width  The width.
     * @param height The height.
     */
    private void setDimensions(Stage stage, int width, int height) {
        this.width = width;
        this.height = height;
        raytrace();
        stage.setWidth(width);
        stage.setHeight(height);
    }
}
