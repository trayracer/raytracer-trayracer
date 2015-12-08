package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.Sphere;
import raytracer.geometry.Triangle;
import raytracer.geometry.World;
import raytracer.light.PointLight;
import raytracer.material.PhongMaterial;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * This is a method creates abstract Art.
 *
 * @author Oliver Kniejski
 */
public class OkAbstractArt extends RtScene{
    public OkAbstractArt() {
        cam = new PerspectiveCamera(new Point3(0, 0, 160), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0), new Color(0.5, 0.5, 0.5));
        world.addLight(new PointLight(new Color(0.8, 0.8, 0.8), new Point3(0, 0, 0), true));
        for (int i = 0; i < 100; i++) world.addGeometry(randomSphere());
        for (int i = 0; i < 10; i++) world.addGeometry(randomTriangle());
    }

    /**
     * This method generates a rather random sphere. testing purposes only, see inside for details.
     *
     * @return a random sphere
     */
    private Sphere randomSphere() {
        return new Sphere(new Point3(50 - Math.random() * 100, 50 - Math.random() * 100, -Math.random() * 50), Math.random() * 10, new PhongMaterial(new Color(Math.random(), Math.random(), Math.random()), new Color(1, 1, 1), 64));
    }

    /**
     * This method generates a rather random triangle. testing purposes only, see inside for details.
     *
     * @return a random triangle
     */
    private Triangle randomTriangle() {
        return new Triangle(new Point3(50 - Math.random() * 100, 50 - Math.random() * 100, -Math.random() * 50), new Point3(50 - Math.random() * 100, 50 - Math.random() * 100, -Math.random() * 50), new Point3(50 - Math.random() * 100, 50 - Math.random() * 100, -Math.random() * 50), new PhongMaterial(new Color(Math.random(), Math.random(), Math.random()), new Color(1, 1, 1), 64));
    }

}
