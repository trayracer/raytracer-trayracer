package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.World;
import raytracer.geometry.ZAxisAlignedCylinder;
import raytracer.light.PointLight;
import raytracer.material.PhongMaterial;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * This method creates a test scene with a cylinder.
 *
 * @author Oliver Kniejski
 */
public class Cylinder extends RtScene {
    public Cylinder() {
        cam = new PerspectiveCamera(new Point3(5, 5, 5), new Vector3(-1, -1, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0.2, 0.2, 0.2), new Color(0.2, 0.2, 0.2));
        world.addGeometry(new ZAxisAlignedCylinder(new Point3(0, -3, -5), 5, 1, new PhongMaterial(new Color(0, 1, 0), new Color(1, 1, 1), 64)));

        world.addLight(new PointLight(new Color(0.5, 0.5, 0.5), new Point3(5, 5, 0)));
    }
}
