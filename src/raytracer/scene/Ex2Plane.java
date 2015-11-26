package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.Plane;
import raytracer.geometry.World;
import raytracer.material.SingleColorMaterial;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * Sets a perspective camera and adds a plane to the world.
 */
public class Ex2Plane extends RtScene{
    public Ex2Plane(){
        cam = new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0), new Color(1, 1, 1));
        world.addGeometry(new Plane(new Point3(0, -1, 0), new Normal3(0, 1, 0), new SingleColorMaterial(new Color(0, 1, 0))));
    }
}
