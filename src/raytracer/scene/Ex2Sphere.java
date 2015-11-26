package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.Sphere;
import raytracer.geometry.World;
import raytracer.light.DirectionalLight;
import raytracer.light.PointLight;
import raytracer.material.LambertMaterial;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * Sets a perspective camera and adds a sphere to the world.
 */
public class Ex2Sphere extends RtScene {
    public Ex2Sphere() {
        cam = new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0), new Color(0, 0, 0));
        world.addLight(new DirectionalLight(new Color(0.5, 0.5, 0.5), new Vector3(0, -1, -1)));
        world.addLight(new PointLight(new Color(0.5, 0.5, 0.5), new Point3(3, 3, 4)));
        world.addGeometry(new Sphere(new Point3(0, 0, -3), 0.5, new LambertMaterial(new Color(1, 0, 0))));
    }
}
