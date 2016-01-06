package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.*;
import raytracer.light.DirectionalLight;
import raytracer.light.PointLight;
import raytracer.material.Material;
import raytracer.material.PhongMaterial;
import raytracer.material.ReflectiveMaterial;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;
import raytracer.texture.SingleColorTexture;
import raytracer.ui.GlobalConfig;

/**
 * A reflective scene containing a reflective scene containing a reflective scene containing ...
 *
 * @author Oliver Kniejski
 */
public class MirrorHall extends RtScene {
    public MirrorHall() {
        cam = new PerspectiveCamera(new Point3(9.9, 2, 3), new Vector3(-1, 0.1, 0), new Vector3(0, 0, 1), Math.PI / 4, GlobalConfig.CAMERA_SAMPLING_PATTERN);
        world = new World(new Color(0, 0, 0.2), new Color(0.1, 0.1, 0.1));
        Material mirror = new ReflectiveMaterial(new SingleColorTexture(new Color(0.01, 0.05, 0.01)), new SingleColorTexture(new Color(0, 0, 0)), 64, new SingleColorTexture(new Color(1, 1, 1)));
        world.addGeometry(new AxisAlignedBox(new Point3(0, 0, 0), new Point3(0.2, 30, 20), mirror));
        world.addGeometry(new AxisAlignedBox(new Point3(10, 0, 0), new Point3(10.2, 30, 20), mirror));
        world.addGeometry(new Plane(new Point3(0, 0, 0), new Normal3(0, 0, 1), new ReflectiveMaterial(new SingleColorTexture(new Color(0.5, 0.5, 0.5)), new SingleColorTexture(new Color(0.8, 0.8, 0.8)), 64, new SingleColorTexture(new Color(0.5, 0.5, 0.5)))));
        world.addGeometry(new Sphere(new Point3(5, 6, 1), 1, new PhongMaterial(new SingleColorTexture(new Color(0, 0, 1)), new SingleColorTexture(new Color(1, 1, 1)), 64)));

        world.addLight(new PointLight(new Color(1, 1, 1), new Point3(5, 15, 20), true));
        world.addLight(new DirectionalLight(new Color(0.2, 0.2, 0.2), new Vector3(0.1, 0.1, -1), true));
    }
}
