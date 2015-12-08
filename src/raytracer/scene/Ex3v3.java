package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.*;
import raytracer.light.PointLight;
import raytracer.material.PhongMaterial;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * This class represents demo-scene Nr. 3 for exercise 3.
 *
 * @author Steven Sobkowski
 */
public class Ex3v3 extends RtScene {
    public Ex3v3() {
        cam = new PerspectiveCamera(new Point3(4, 4, 4), new Vector3(-1, -1, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0), new Color(0, 0, 0));

        world.addLight(new PointLight(new Color(1, 1, 1), new Point3(4, 4, 4), false));

        world.addGeometry(new Plane(new Point3(0, 0, 0), new Normal3(0, 1, 0), new PhongMaterial(new Color(1, 0, 0),new Color(1,1,1),64)));
        world.addGeometry(new Sphere(new Point3(1, 1, 1), 0.5, new PhongMaterial(new Color(0, 1, 0),new Color(1,1,1),64)));
        world.addGeometry(new AxisAlignedBox(new Point3(-1.5, 0.5, 0.5), new Point3(-0.5, 1.5, 1.5), new PhongMaterial(new Color(0, 0, 1),new Color(1,1,1),64)));
        world.addGeometry(new Triangle(new Point3(0, 0, -1), new Point3(1, 0, -1), new Point3(1, 1, -1), new PhongMaterial(new Color(1, 1, 0),new Color(1,1,1),64)));
    }
}
