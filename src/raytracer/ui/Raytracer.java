package raytracer.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;
import raytracer.scene.*;
import raytracer.scene.Torus;
import raytracer.texture.Color;
import raytracer.texture.SingleColorTexture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
    private int pixelsTotal;
    /**
     * Pixels already rendered. Render helper.
     */
    private int pixelsDone = 0;
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
     * The borderPane for the user interface.
     */
    private BorderPane pane = new BorderPane();
    /**
     * The progress bar.
     */
    private ProgressBar progressBar = new ProgressBar();
    /**
     * A bufferedImage for rendering in SWT.
     */
    private BufferedImage rImage;

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

        pane.setTop(createMenuBar(primaryStage));
        pane.setCenter(view);

        progressBar.prefWidthProperty().bind(primaryStage.widthProperty());
        pane.setBottom(progressBar);
        Scene scene = new Scene(pane, width, height + 50);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.setTitle("TrayRacer v0.60");

        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        loadScene(new DOFSpheres());
    }

    /**
     * This method resets the Image. Useful for size changes and setting it black.
     */
    private void resetImage() {
        if(GlobalConfig.FX_IMAGE) {
            if (GlobalConfig.DEBUG_MODE) System.out.println("# FX-Image");
            wImage = new WritableImage(width, height);
            pixelBuffer = new int[width * height];

            for (int pixel = 0; pixel < pixelBuffer.length; pixel++) {
                pixelBuffer[pixel] = GlobalConfig.TODO_COLOR;
            }

            refreshImage();
            view.setImage(wImage);
        }
        else {
            if (GlobalConfig.DEBUG_MODE) System.out.println("# SWT-Image");
            rImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    rImage.setRGB(x, y, GlobalConfig.TODO_COLOR);
                }
            }



            Image image = SwingFXUtils.toFXImage(rImage, null);
            view.setImage(image);
        }

    }

    /**
     * This method refreshes the View.
     */
    private void refreshImage() {
        if (GlobalConfig.FX_IMAGE) {
            wImage.getPixelWriter().setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixelBuffer, 0, width);
        } else {
            Image image = SwingFXUtils.toFXImage(rImage, null);
            view.setImage(image);
        }
    }

    /**
     * The RayTracer.
     * This method draws the world for the perspective of the given camera and saves it in a BufferedImage and converts the BufferedImage to a FXImage
     * which will be placed in the ImageView.
     */
    private void raytrace() {
        if (GlobalConfig.DEBUG_MODE){
            System.out.println("\n########");
            System.out.println("# Camera SamplingPattern: " + cam.pattern);
            System.out.println("# Recursion Depth: " + GlobalConfig.RECURSION_DEPTH);
        }

        killEmAll();
        pixelsDone = 0;
        pixelsTotal = width * height;
        resetImage();

        if (GlobalConfig.THREADED_RENDERING) {
            if (GlobalConfig.DEBUG_MODE) System.out.println("# threaded rendering with " + GlobalConfig.RENDER_THREADS + " Threads");
            renderers = Executors.newFixedThreadPool(GlobalConfig.RENDER_THREADS);

            if (GlobalConfig.LINEAR_RENDERING) {
                if (GlobalConfig.DEBUG_MODE) System.out.println("# linear rendering");
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        renderers.execute(pixelRenderer(x, y));
                    }
                }
            }

            else {
                if (GlobalConfig.DEBUG_MODE) System.out.println("# randomly distributed pixel rendering");
                LinkedList<int[]> pixelsToDo = new LinkedList<>();
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        pixelsToDo.add(new int[]{x, y});
                    }
                }
                Collections.shuffle(pixelsToDo);
                while (!pixelsToDo.isEmpty()) {
                    int[] coordinates = pixelsToDo.remove(0);
                    renderers.execute(pixelRenderer(coordinates[0], coordinates[1]));
                }
            }

            renderers.shutdown();

            Task<Void> refreshTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    if (GlobalConfig.DEBUG_MODE) System.out.print("# started updater...");
                    while (!renderers.isTerminated()) {
                        if (isCancelled()) {
                            if (GlobalConfig.DEBUG_MODE) System.out.println("! updater CANCELED!");
                            break;
                        }
                        updateProgress(pixelsDone, pixelsTotal);
                        refreshImage();

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException interrupted) {
                            if (isCancelled()) {
                                if (GlobalConfig.DEBUG_MODE) System.out.println("! updater CANCELED!");
                                break;
                            }
                        }

                    }
                    updateProgress(100, 100);
                    refreshImage();
                    if (GlobalConfig.DEBUG_MODE) System.out.println(" finished");
                    return null;
                }
            };

            Thread refreshThread = new Thread(refreshTask);
            refreshThread.setDaemon(true);
            progressBar.progressProperty().bind(refreshTask.progressProperty());
            refreshThread.start();

        } else { // Non-Threaded rendering with updateTask
            if (GlobalConfig.DEBUG_MODE) System.out.println("# non-threaded rendering");
            Task<Void> renderTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    if (GlobalConfig.DEBUG_MODE) System.out.print("# started updater...");
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            renderPixel(x, y);
                            updateProgress(pixelsDone, pixelsTotal);
                            refreshImage();
                        }
                    }
                    updateProgress(100, 100);
                    refreshImage();
                    if (GlobalConfig.DEBUG_MODE) System.out.println(" finished");
                    return null;
                }
            };

            Thread refreshThread = new Thread(renderTask);
            progressBar.progressProperty().bind(renderTask.progressProperty());
            refreshThread.start();

            // Non-Threaded rendering all in Main Thread
//            for (int y = 0; y < height; y++) {
//                for (int x = 0; x < width; x++) {
//                    renderPixel(x, y);
//                }
//            }
//            Platform.runLater(() -> refreshImage());
        }
    }


    /**
     * This method creates a Runnable for the renderer which calculates the specified pixel of the image.
     *
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @return The Runnable.
     */
    private Runnable pixelRenderer(final int x, final int y) {
        return () -> renderPixel(x, y);
    }

    /**
     * This method does the actual rendering. Writes directly into pixelBuffer.
     *
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     */
    private void renderPixel(final int x, final int y) {
        Color c = world.backgroundColor;
        LinkedList<Color> colors = new LinkedList<>();
        for (Ray ray : cam.rayFor(width, height, x, (height - 1) - y)) {
            Hit hit = world.hit(ray);
            if (hit != null) {
                colors.add(hit.material.colorFor(hit, world, new Tracer(GlobalConfig.RECURSION_DEPTH)));
            } else colors.add(c);
        }
        if (colors.size() > 0) {
            double r = 0.0;
            double g = 0.0;
            double b = 0.0;
            for (Color color : colors) {
                r += color.r;
                g += color.g;
                b += color.b;
            }
            c = new Color(r / colors.size(), g / colors.size(), b / colors.size());
        }

        if (GlobalConfig.FX_IMAGE) {
            pixelBuffer[y * width + x] = c.getRGB();
        } else {
            rImage.setRGB(x, y, c.getRGB());
        }
        pixelsDone++;
    }

    /**
     * Method for stopping all renderwork.
     */
    private void killEmAll(){
        if (renderers != null && !renderers.isTerminated()){
            renderers.shutdownNow();
            //TODO: KILL EM DEAD!
//            while (!renderers.isTerminated()){
//                //warten
//            }
        }

    }


    /**
     * Test for the stuck image problem. works while everything works and vice versa.
     */
    boolean goodImage = true;
    WritableImage unusedWImage = new WritableImage(width, height);
    private void toggleImage(){
        if(goodImage){
            goodImage = false;
            view.setImage(unusedWImage);
        } else{
            goodImage = true;

            view.setImage(wImage);
        }
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

    /**
     * This method is for converting a conventional stereoscopic image to a anaglyph image.
     *
     * @param stage The stage.
     */
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
            ShapeFromFile objGeo = new ShapeFromFile(objFile, new PhongMaterial(new SingleColorTexture(new Color(1, 1, 0)), new SingleColorTexture(new Color(1, 1, 1)), 64));
            Vector3 geoMiddle = new Vector3(objGeo.boundingBox.lbf.x, objGeo.boundingBox.lbf.y, objGeo.boundingBox.lbf.z).mul(0.5).add(new Vector3(objGeo.boundingBox.run.x, objGeo.boundingBox.run.y, objGeo.boundingBox.run.z).mul(0.5));
            double objHeight = objGeo.boundingBox.lbf.sub(objGeo.boundingBox.run).magnitude;
            //noinspection SuspiciousNameCombination
            cam = new PerspectiveCamera(new Point3(objHeight, objHeight, objHeight), new Vector3(geoMiddle.x - objHeight, geoMiddle.y - objHeight, geoMiddle.z - objHeight), new Vector3(0, 1, 0), Math.PI / 4, GlobalConfig.CAMERA_SAMPLING_PATTERN);
            world = new World(new Color(0.1, 0.1, 0.1), new Color(0.3, 0.3, 0.3));
            world.addGeometry(objGeo);
            //noinspection SuspiciousNameCombination
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
        final Menu fileMenu = new Menu("File");

        final MenuItem save = new MenuItem("Save...");
        save.setOnAction(e -> saveImage(primaryStage));
        fileMenu.getItems().add(save);

        final MenuItem open = new MenuItem("Open .obj");
        open.setOnAction(e -> renderObjFile(primaryStage));
        fileMenu.getItems().add(open);

        final MenuItem stop = new MenuItem("stop rendering");
        stop.setOnAction(e -> killEmAll());
        fileMenu.getItems().add(stop);

        final MenuItem refreshView = new MenuItem("toggle view");
        refreshView.setOnAction(e -> toggleImage());
        fileMenu.getItems().add(refreshView);

        final MenuItem anaglyph = new MenuItem("... anaglyph");
        anaglyph.setOnAction(e -> anaglyph(primaryStage));
        fileMenu.getItems().add(anaglyph);

        menubar.getMenus().add(fileMenu);

        // Scene Menu
        final Menu sceneMenu = new Menu("Scene");

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

        sceneMenu.getItems().add(ex2);

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

        sceneMenu.getItems().add(ex3);

        final Menu ex4 = new Menu("Exercise 4");

        final MenuItem ex4spheres = new MenuItem("Spheres");
        ex4spheres.setOnAction(e -> loadScene(new Ex4Spheres()));
        ex4.getItems().add(ex4spheres);

        final MenuItem ex4box = new MenuItem("Box");
        ex4box.setOnAction(e -> loadScene(new Ex4Box()));
        ex4.getItems().add(ex4box);

        sceneMenu.getItems().add(ex4);

        final Menu ex5 = new Menu("Exercise 5");

        final MenuItem ex5smartie = new MenuItem("Smartie");
        ex5smartie.setOnAction(e -> loadScene(new Ex5Smartie()));
        ex5.getItems().add(ex5smartie);

        final MenuItem ex5box = new MenuItem("Box");
        ex5box.setOnAction(e -> loadScene(new Ex5Box()));
        ex5.getItems().add(ex5box);

        sceneMenu.getItems().add(ex5);

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

        final MenuItem mirrorHall = new MenuItem("MirrorMirror");
        mirrorHall.setOnAction(e -> loadScene(new MirrorHall()));
        extra.getItems().add(mirrorHall);

        final MenuItem earth = new MenuItem("Earth");
        earth.setOnAction(e -> loadScene(new Earth()));
        extra.getItems().add(earth);

        sceneMenu.getItems().add(extra);

        menubar.getMenus().add(sceneMenu);

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
        killEmAll();
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
        killEmAll();
        this.width= width;
        this.height = height;
        stage.setWidth(this.width);
        stage.setHeight(this.height + 70);
        raytrace();
    }
}