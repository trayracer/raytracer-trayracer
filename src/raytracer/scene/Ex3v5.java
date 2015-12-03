package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.*;
import raytracer.light.SpotLight;
import raytracer.material.PhongMaterial;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * This class represents demo-scene Nr. 5 for exercise 3.
 *
 * @author Oliver Kniejski
 */
public class Ex3v5 extends RtScene{
    public Ex3v5() {
        cam = new PerspectiveCamera(new Point3(4, 4, 4), new Vector3(-1, -1, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0), new Color(0, 0, 0));

        world.addLight(new SpotLight(new Color(1, 1, 1), new Point3(4, 4, 4), new Vector3(-1, -1, -1), Math.PI / 14));

        world.addGeometry(new Plane(new Point3(0, 0, 0), new Normal3(0, 1, 0), new PhongMaterial(new Color(1, 0, 0),new Color(1,1,1),64)));
        world.addGeometry(new Sphere(new Point3(1, 1, 1), 0.5, new PhongMaterial(new Color(0, 1, 0),new Color(1,1,1),64)));
        world.addGeometry(new AxisAlignedBox(new Point3(-1.5, 0.5, 0.5), new Point3(-0.5, 1.5, 1.5), new PhongMaterial(new Color(0, 0, 1),new Color(1,1,1),64)));
        world.addGeometry(new Triangle(new Point3(0, 0, -1), new Point3(1, 0, -1), new Point3(1, 1, -1), new PhongMaterial(new Color(1, 1, 0),new Color(1,1,1),64)));
    }
}
