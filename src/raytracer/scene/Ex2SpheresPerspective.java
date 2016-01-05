package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.Sphere;
import raytracer.geometry.World;
import raytracer.material.SingleColorMaterial;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.sampling.SamplingPattern;
import raytracer.texture.Color;

/**
 * Sets a perspective camera and adds two spheres to the world.
 *
 * @author TrayRacingTeam
 */
public class Ex2SpheresPerspective extends RtScene {
    public Ex2SpheresPerspective() {
        cam = new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4, new SamplingPattern());
        world = new World(new Color(0, 0, 0), new Color(1, 1, 1));
        world.addGeometry(new Sphere(new Point3(-1, 0, -3), 0.5, new SingleColorMaterial(new Color(1, 0, 0))));
        world.addGeometry(new Sphere(new Point3(1, 0, -6), 0.5, new SingleColorMaterial(new Color(1, 0, 0))));
    }
}
