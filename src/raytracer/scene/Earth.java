package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.Plane;
import raytracer.geometry.Sphere;
import raytracer.geometry.World;
import raytracer.light.PointLight;
import raytracer.material.LambertMaterial;
import raytracer.material.ReflectiveMaterial;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.sampling.SamplingPattern;
import raytracer.texture.*;

/**
 * This class represents the earth scene for texture demo.
 *
 * @author Marie Hennings
 */
public class Earth extends RtScene {
    public Earth() {
        cam = new PerspectiveCamera(new Point3(0, -15, 0), new Vector3(0, 1, 0), new Vector3(0, 0, 1), Math.PI / 4, new SamplingPattern());
        world = new World(new Color(0, 0, 0), new Color(1, 1, 1));

        world.addLight(new PointLight(new Color(1, 1, 1), new Point3(8, 8, 8), false));

        world.addGeometry(new Plane(new Point3(10, 10, 10), new Normal3(0, 1, 0), new LambertMaterial(new ImageTexture(TextureStock.GALAXY)), 10));
        world.addGeometry(new Sphere(new Point3(-5, 0, 0), 2, new LambertMaterial(new ImageTexture(TextureStock.EARTH_NN))));
        world.addGeometry(new Sphere(new Point3(0, 0, 0), 2, new LambertMaterial(new InterpolatedImageTexture(TextureStock.EARTH_NN))));
        world.addGeometry(new Sphere(new Point3(5, 0, 0), 2, new LambertMaterial(new InterpolatedImageTexture(TextureStock.EARTH))));

    }
}
