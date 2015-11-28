package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.camera.StereoCamera;
import raytracer.geometry.*;
import raytracer.light.PointLight;
import raytracer.material.LambertMaterial;
import raytracer.material.PhongMaterial;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * A festive scene with 3 cones, 4 spheres, 2 triangles and a cylinder.
 *
 * @author Oliver Kniejski
 */
public class Cone extends RtScene {
    public Cone() {
        cam = new PerspectiveCamera(new Point3(9, 0, 1.5), new Vector3(-1, 0, 0), new Vector3(0, 0, 1), Math.PI / 4);
//        cam = new StereoCamera(new Point3(9, 0, 1.5), new Vector3(-1, 0, 0), new Vector3(0, 0, 1), Math.PI / 4, 2, true);

        world = new World(new Color(0, 0, 0), new Color(0.2, 0.2, 0.2));
        // Planes
        world.addGeometry(new Plane(new Point3(0, 0, -1), new Normal3(0, 0, 1), new LambertMaterial(new Color(1, 0.5, 0))));
        world.addGeometry(new Plane(new Point3(-3, 0, 0), new Normal3(-1, 0, 0), new LambertMaterial(new Color(0, 1, 1))));
        // Nails
        world.addGeometry(new ZAxisAlignedCone(new Point3(0, 0, 4), -2, 0, 5, new PhongMaterial(new Color(0, 1, 0), new Color(1, 1, 1), 64)));
        world.addGeometry(new ZAxisAlignedCone(new Point3(0, 0, 3), -2, 0, 3, new PhongMaterial(new Color(0, 1, 0), new Color(1, 1, 1), 64)));
        world.addGeometry(new ZAxisAlignedCone(new Point3(0, 0, 2), -2, 0, 2, new PhongMaterial(new Color(0, 1, 0), new Color(1, 1, 1), 64)));
        // Trunk
        world.addGeometry(new ZAxisAlignedCylinder(new Point3(0, 0, -1), 2, 0.2, new LambertMaterial(new Color(0.6, 0.4, 0.2))));
        // Spheres
        world.addGeometry(new Sphere(new Point3(0.5, 1, 1), 0.2, new PhongMaterial(new Color(1, 0, 0), new Color(1, 0.2, 0.2), 32)));
        world.addGeometry(new Sphere(new Point3(1.15, 0.5, 0), 0.2, new PhongMaterial(new Color(1, 0, 0), new Color(1, 0.2, 0.2), 32)));
        world.addGeometry(new Sphere(new Point3(0.5, -0.8, 1.5), 0.2, new PhongMaterial(new Color(1, 0, 0), new Color(1, 0.2, 0.2), 32)));
        world.addGeometry(new Sphere(new Point3(0.7, 0.4, 2.1), 0.2, new PhongMaterial(new Color(1, 0, 0), new Color(1, 0.2, 0.2), 32)));
        // Star
        world.addGeometry(new Triangle(new Point3(0, 0, 4), new Point3(0, 0.25, 4.3), new Point3(0, -0.25, 4.3), new PhongMaterial(new Color(1, 1, 0), new Color(1, 1, 1), 64)));
        world.addGeometry(new Triangle(new Point3(0, 0, 4.4), new Point3(0, -0.25, 4.1), new Point3(0, 0.25, 4.1), new PhongMaterial(new Color(1, 1, 0), new Color(1, 1, 1), 64)));
        // Gifts
        world.addGeometry(new AxisAlignedBox(new Point3(0, -0.9 ,-1), new Point3(0.5, -0.3, -0.4), new PhongMaterial(new Color(1, 1, 0), new Color(1, 1, 1), 64)));
        world.addGeometry(new AxisAlignedBox(new Point3(0.5, -1 ,-1), new Point3(1.5, 0, -0.8), new PhongMaterial(new Color(0, 0, 1), new Color(1, 1, 1), 64)));
        world.addGeometry(new AxisAlignedBox(new Point3(0.2, 0.2 ,-1), new Point3(1.3, 1, -0.4), new PhongMaterial(new Color(1, 0.5, 0.5), new Color(1, 1, 1), 64)));
        world.addGeometry(new AxisAlignedBox(new Point3(1, 0.9 ,-1), new Point3(1.2, 1.3, -0.1), new PhongMaterial(new Color(1, 0, 1), new Color(1, 1, 1), 64)));
        // Lights
        world.addLight(new PointLight(new Color(0.2, 0.2, 0.2), new Point3(6, 5, 0)));
        world.addLight(new PointLight(new Color(0.2, 0.2, 0.2), new Point3(6, -5, 0)));
        world.addLight(new PointLight(new Color(0.2, 0.2, 0.2), new Point3(1, 0.5, 4.2)));
    }
}
