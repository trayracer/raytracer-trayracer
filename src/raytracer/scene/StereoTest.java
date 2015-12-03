package raytracer.scene;

import raytracer.camera.StereoCamera;
import raytracer.geometry.Sphere;
import raytracer.geometry.Triangle;
import raytracer.geometry.World;
import raytracer.light.PointLight;
import raytracer.material.PhongMaterial;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * This method creates a test scene for the stereoscopic camera.
 *
 * @author Oliver Kniejski
 */
public class StereoTest extends RtScene {
    public StereoTest() {
        cam = new StereoCamera(new Point3(25, 20, 40), new Vector3(-25, -17, -40), new Vector3(0, 1, 0), Math.PI / 3.3, 6, true);
        world = new World(new Color(0, 0, 0), new Color(0.2, 0.2, 0.2));
        world.addLight(new PointLight(new Color(0.6, 0.6, 0.6), new Point3(20, 40, 40)));

        world.addGeometry(new Sphere(new Point3(0, 0, 0), 2, new PhongMaterial(new Color(1, 1, 1), new Color(1, 1, 1), 64)));
        world.addGeometry(new Sphere(new Point3(4, 0, 0), 2, new PhongMaterial(new Color(1, 0, 0), new Color(1, 1, 1), 64)));
        world.addGeometry(new Sphere(new Point3(0, 4, 0), 2, new PhongMaterial(new Color(0, 1, 0), new Color(1, 1, 1), 64)));
        world.addGeometry(new Sphere(new Point3(0, 0, 4), 2, new PhongMaterial(new Color(0, 0, 1), new Color(1, 1, 1), 64)));
        world.addGeometry(new Sphere(new Point3(5, 5, 0), 2, new PhongMaterial(new Color(1, 1, 0), new Color(1, 1, 1), 64)));
        world.addGeometry(new Sphere(new Point3(5, 0, 5), 2, new PhongMaterial(new Color(1, 0, 1), new Color(1, 1, 1), 64)));
        world.addGeometry(new Sphere(new Point3(0, 5, 5), 2, new PhongMaterial(new Color(0, 1, 1), new Color(1, 1, 1), 64)));
        world.addGeometry(new Triangle(new Point3(-1, -1, -1), new Point3(-1, 10, -1), new Point3(-1, -1, 10), new PhongMaterial(new Color(0, 0.6, 0.6), new Color(1, 1, 1), 64)));
        world.addGeometry(new Triangle(new Point3(-1, -1, -1), new Point3(10, -1, -1), new Point3(-1, -1, 10), new PhongMaterial(new Color(0.6, 0, 0.6), new Color(1, 1, 1), 64)));
        world.addGeometry(new Triangle(new Point3(-1, -1, -1), new Point3(10, -1, -1), new Point3(-1, 10, -1), new PhongMaterial(new Color(0.6, 0.6, 0), new Color(1, 1, 1), 64)));
        world.addGeometry(new Triangle(new Point3(-2, -2, -2), new Point3(-2, 11, -2), new Point3(-2, -2, 11), new PhongMaterial(new Color(0, 0.8, 0.8), new Color(1, 1, 1), 64)));
        world.addGeometry(new Triangle(new Point3(-2, -2, -2), new Point3(11, -2, -2), new Point3(-2, -2, 11), new PhongMaterial(new Color(0.8, 0, 0.8), new Color(1, 1, 1), 64)));
        world.addGeometry(new Triangle(new Point3(-2, -2, -2), new Point3(11, -2, -2), new Point3(-2, 11, -2), new PhongMaterial(new Color(0.8, 0.8, 0), new Color(1, 1, 1), 64)));
        world.addGeometry(new Triangle(new Point3(-3, -3, -3), new Point3(-3, 12, -3), new Point3(-3, -3, 12), new PhongMaterial(new Color(0, 1, 1), new Color(1, 1, 1), 64)));
        world.addGeometry(new Triangle(new Point3(-3, -3, -3), new Point3(12, -3, -3), new Point3(-3, -3, 12), new PhongMaterial(new Color(1, 0, 1), new Color(1, 1, 1), 64)));
        world.addGeometry(new Triangle(new Point3(-3, -3, -3), new Point3(12, -3, -3), new Point3(-3, 12, -3), new PhongMaterial(new Color(1, 1, 0), new Color(1, 1, 1), 64)));
        world.addGeometry(new Sphere(new Point3(15, 0, 0), 2, new PhongMaterial(new Color(1, 0, 0), new Color(1, 1, 1), 64)));
        world.addGeometry(new Sphere(new Point3(0, 15, 0), 2, new PhongMaterial(new Color(0, 1, 0), new Color(1, 1, 1), 64)));
        world.addGeometry(new Sphere(new Point3(0, 0, 15), 2, new PhongMaterial(new Color(0, 0, 1), new Color(1, 1, 1), 64)));
        world.addGeometry(new Sphere(new Point3(17, 17, 17), 2, new PhongMaterial(new Color(1, 1, 1), new Color(1, 1, 1), 64)));
    }
}
