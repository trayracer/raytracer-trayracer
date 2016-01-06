package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.Plane;
import raytracer.geometry.Sphere;
import raytracer.geometry.World;
import raytracer.light.DirectionalLight;
import raytracer.light.PointLight;
import raytracer.material.LambertMaterial;
import raytracer.material.ReflectiveMaterial;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.sampling.SamplingPattern;
import raytracer.texture.*;
import raytracer.ui.GlobalConfig;

/**
 * This class represents the earth scene for texture demo.
 *
 * @author Marie Hennings
 */
public class Earth extends RtScene {
    public Earth() {
        cam = new PerspectiveCamera(new Point3(0, -15, 0), new Vector3(0, 1, 0), new Vector3(0, 0, 1), Math.PI / 4, GlobalConfig.CAMERA_SAMPLING_PATTERN);
        world = new World(new Color(0, 0, 0), new Color(0.05, 0.05, 0.05));

        world.addLight(new PointLight(new Color(1.5, 1.5, 1.5), new Point3(15, -4, 3), false));
//        world.addLight(new DirectionalLight(new Color(0.4, 0.4, 0.4), new Vector3(0, 1, 0), false));

        world.addGeometry(new Plane(new Point3(10, 10, 10), new Normal3(0, -1, 0), new LambertMaterial(new ImageTexture(TextureStock.SPACE)), 10));
//        world.addGeometry(new Sphere(new Point3(-5, 0, 0), 2, new LambertMaterial(new ImageTexture(TextureStock.EARTH_NN))));
//        world.addGeometry(new Sphere(new Point3(0, 0, 0), 2, new LambertMaterial(new InterpolatedImageTexture(TextureStock.EARTH_NN))));
        world.addGeometry(new Sphere(new Point3(0, 0, 0), 2, new LambertMaterial(new InterpolatedImageTexture(TextureStock.EARTH))));

    }
}
