package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.camera.StereoCamera;
import raytracer.geometry.Sphere;
import raytracer.geometry.World;
import raytracer.light.PointLight;
import raytracer.material.PhongMaterial;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;
import raytracer.texture.SingleColorTexture;
import raytracer.ui.GlobalConfig;

/**
 * A scene with a torus.
 *
 * @author Oliver Kniejski
 */
public class Torus extends RtScene {
    public Torus() {
        cam = new PerspectiveCamera(new Point3(10, 10, 10), new Vector3(-1, -1, -1), new Vector3(0, 0, 1), Math.PI / 4, GlobalConfig.CAMERA_SAMPLING_PATTERN);
//        cam = new StereoCamera(new Point3(10, 10, 10), new Vector3(-1, -1, -1), new Vector3(0, 0, 1), Math.PI / 4, 2, true);

        world = new World(new Color(0, 0, 0), new Color(0.2, 0.2, 0.2));
        world.addGeometry(new raytracer.geometry.Torus(3, 1, new PhongMaterial(new SingleColorTexture(new Color(1, 0.6, 0)), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new raytracer.geometry.Torus(4.2, 0.1, new PhongMaterial(new SingleColorTexture(new Color(1, 1, 1)), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new Sphere(new Point3(3, 0, 0), 1.1, new PhongMaterial(new SingleColorTexture(new Color(1, 1, 1)), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new Sphere(new Point3(-3, 0, 0), 1.1, new PhongMaterial(new SingleColorTexture(new Color(1, 1, 1)), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new Sphere(new Point3(0, 3, 0), 1.1, new PhongMaterial(new SingleColorTexture(new Color(1, 1, 1)), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new Sphere(new Point3(0, -3, 0), 1.1, new PhongMaterial(new SingleColorTexture(new Color(1, 1, 1)), new SingleColorTexture(new Color(1, 1, 1)), 64)));

        world.addLight(new PointLight(new Color(0.5, 0.5, 0.5), new Point3(0, 5, 5), true));
    }
}
