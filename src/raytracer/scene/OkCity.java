package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.AxisAlignedBox;
import raytracer.geometry.Sphere;
import raytracer.geometry.World;
import raytracer.light.DirectionalLight;
import raytracer.light.PointLight;
import raytracer.material.PhongMaterial;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * Created by ok on 26.11.15.
 */
public class OkCity extends RtScene {
    public OkCity() {
        cam = new PerspectiveCamera(new Point3(-10, -10, 15), new Vector3(15, 15, -10), new Vector3(0, 0, 1), Math.PI /4);
        world = new World(new Color(0, 0, 0), new Color(0.1, 0.1, 0.1));
        world.addLight(new DirectionalLight(new Color(0.1, 0.1, 0.1), new Vector3(-1,2,-7)));
        world.addLight(new PointLight(new Color(0.4, 0, 0), new Point3(-0.25, -0.25, 12)));
        world.addLight(new PointLight(new Color(0.2, 0, 0), new Point3(-0.25, -0.25, 0)));

        world.addGeometry(new Sphere(new Point3(-0.25, -0.25, 12), 2.5, new PhongMaterial(new Color(1, 0, 0), new Color(1, 1, 1), 64)));

        for (int y = -10; y < 10; y++){
            for (int x = -10; x < 10; x++){
                world.addGeometry(new AxisAlignedBox(new Point3(x, y, 0), new Point3(x + 0.5, y + 0.5, Math.random() * Math.max(Math.abs(x), Math.abs(y))/2 + Math.max(Math.abs(x), Math.abs(y)) + 0.001), new PhongMaterial(new Color(0.8, 0.8, 0.8), new Color(1, 1, 1), 64)));
            }
        }
    }
}
