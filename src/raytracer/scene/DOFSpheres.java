package raytracer.scene;

import raytracer.camera.FocusCamera;
import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.Plane;
import raytracer.geometry.Sphere;
import raytracer.geometry.World;
import raytracer.light.PointLight;
import raytracer.material.PhongMaterial;
import raytracer.material.ReflectiveMaterial;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;
import raytracer.texture.InterpolatedImageTexture;
import raytracer.texture.SingleColorTexture;
import raytracer.texture.TextureStock;
import raytracer.ui.GlobalConfig;

/**
 * This class represents demo-scene No. 2 for exercise 4.
 *
 * @author TrayRacingTeam
 */
public class DOFSpheres extends RtScene {
    public DOFSpheres() {
        double dist = new Point3(0, 1, 0).sub(new Point3(8, 8, 8)).magnitude;
        System.out.println("dist = " + dist);
        cam = new FocusCamera(new Point3(8, 8, 8), new Vector3(-1, -1, -1), new Vector3(0, 1, 0), Math.PI / 4, dist, 0.5, GlobalConfig.CAMERA_SAMPLING_PATTERN);
        world = new World(new Color(0, 0, 0), new Color(0.25, 0.25, 0.25));

        world.addLight(new PointLight(new Color(1, 1, 1), new Point3(8, 8, 8), false));

        world.addGeometry(new Plane(new Point3(0, 0, 0), new Normal3(0, 1, 0), new PhongMaterial(new InterpolatedImageTexture(TextureStock.WALL),new SingleColorTexture( new Color(1, 1, 1)), 64), 4));
        world.addGeometry(new Sphere(new Point3(-3, 1, 0), 1, new ReflectiveMaterial(new SingleColorTexture(new Color(1, 0, 0)), new SingleColorTexture(new Color(1, 1, 1)), 64, new SingleColorTexture(new Color(0.5, 0.5, 0.5)))));
        world.addGeometry(new Sphere(new Point3(0, 1, 0), 1, new ReflectiveMaterial(new SingleColorTexture(new Color(0, 1, 0)), new SingleColorTexture(new Color(1, 1, 1)), 64, new SingleColorTexture(new Color(0.5, 0.5, 0.5)))));
        world.addGeometry(new Sphere(new Point3(3, 1, 0), 1, new ReflectiveMaterial(new SingleColorTexture(new Color(0, 0, 1)), new SingleColorTexture(new Color(1, 1, 1)), 64, new SingleColorTexture(new Color(0.5, 0.5, 0.5)))));

    }
}
