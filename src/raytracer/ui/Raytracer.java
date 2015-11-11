package raytracer.ui;/**
 * Created on 10.11.2015.
 *
 * @author Marie Hennings
 */

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import raytracer.camera.Camera;
import raytracer.camera.OrthographicCamera;
import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.*;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

import java.awt.image.BufferedImage;

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
    World world;
    /**
     * Camera that is used, perspective or orthographic
     */
    Camera cam;

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
        final Pane pane = new Pane();

        //planeScene();
        //sphereScene();
        //boxScene();
        //triangleScene();
        //twoSpheresPerspective();
        //twoSpheresOrthographic();
        olliScene();

        Image image = SwingFXUtils.toFXImage(drawWorld(), null);
        ImageView view = new ImageView();
        view.setImage(image);
        pane.getChildren().add(view);

        final Scene scene = new Scene(pane, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method draws the world for the perspective of the given camera and saves it in a bufferedimage.
     * @return The BufferedImage the world is drawn in.
     */
    private BufferedImage drawWorld() {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Hit hit = world.hit(cam.rayFor(width, height, x, y));

                //TODO Color
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
        ;
    }

    /**
     * Sets a perspective camera and adds a sphere to the world.
     */
    private void sphereScene() {
        cam = new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0));
        world.addGeometry(new Sphere(new Point3(0, 0, -1), 0.5, new Color(1, 0, 0)));
    }

    /**
     * Sets a perspective camera and adds a box to the world.
     */
    private void boxScene() {
        cam = new PerspectiveCamera(new Point3(3, 3, 3), new Vector3(-3, -3, -3), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0));
        //TODO AxisAlignedBox
        //world.addGeometry();
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

    //for testing

    private void olliScene(){
        cam = new OrthographicCamera(new Point3(0, 0, 0), new Vector3(1, 1, 0), new Vector3(0, 0, 1), 20);
        //cam = new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(1, 1, 0), new Vector3(0, 0, 1), 40);
        world = new World(new Color(0,0,0));
        Geometry kugelRot = new Sphere(new Point3(6, 2, 0), 2, new raytracer.texture.Color(1, 0, 0));
        world.addGeometry(kugelRot);
        Geometry kugelGruen = new Sphere(new Point3(4, 4, 0), 2, new raytracer.texture.Color(0, 1, 0));
        world.addGeometry(kugelGruen);
        Geometry kugelBlau = new Sphere(new Point3(2, 6, 0), 2, new raytracer.texture.Color(0, 0, 1));
        world.addGeometry(kugelBlau);
        world.addGeometry(new Triangle(new Point3(8, 0, 1), new Point3(0, 8, 1), new Point3(4, 4, 7), new Color(1, 1, 0)));
        world.addGeometry(new Triangle(new Point3(8, 0, -1), new Point3(0, 8, -1), new Point3(4, 4, -7), new Color(1, 0, 1)));
        world.addGeometry(new Triangle(new Point3(10, 0, 0), new Point3(0, 10, 0), new Point3(10, 10, 0), new Color(0, 1, 1)));

        for (int i = 0; i < 100; i++) world.addGeometry(randomSphere());
    }

    private Sphere randomSphere(){
        return new Sphere(new Point3(10 + Math.random()*100, 10 + Math.random()*100, 20 - Math.random()*40), Math.random()*3, new Color(Math.random(),Math.random(),Math.random()));
    }
}
