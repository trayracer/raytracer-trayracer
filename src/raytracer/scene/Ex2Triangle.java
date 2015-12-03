package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.Triangle;
import raytracer.geometry.World;
import raytracer.material.SingleColorMaterial;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * Sets a perspective camera and adds a triangle to the world.
 *
 * @author TrayRacingTeam
 */
public class Ex2Triangle extends RtScene{
    public Ex2Triangle() {
        cam = new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0), new Color(1, 1, 1));
        world.addGeometry(new Triangle(new Point3(-0.5, 0.5, -3), new Point3(0.5, 0.5, -3), new Point3(0.5, -0.5, -3), new SingleColorMaterial(new Color(1, 0, 1))));
    }
}
