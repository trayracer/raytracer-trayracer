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
import raytracer.texture.Color;
import raytracer.texture.ImageTexture;
import raytracer.texture.SingleColorTexture;
import raytracer.texture.TextureStock;

/**
 * This class represents the earth scene for texture demo.
 *
 * @author Marie Hennings
 */
public class Earth extends RtScene {
    public Earth() {
        cam = new PerspectiveCamera(new Point3(0, -8, 12), new Vector3(0, 0.5, -1), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0), new Color(0.5, 0.5, 0.5));

        world.addLight(new PointLight(new Color(1, 1, 1), new Point3(8, 8, 8), false));

        //world.addGeometry(new Plane(new Point3(0, 0, 0), new Normal3(0, 1, 0), new ReflectiveMaterial(new SingleColorTexture(new Color(0.1, 0.1, 0.1)), new SingleColorTexture(new Color(1, 1, 1)), 64, new SingleColorTexture(new Color(0.5, 0.5, 0.5)))));
        world.addGeometry(new Sphere(new Point3(-5, 0, 0), 2, new LambertMaterial(new ImageTexture(TextureStock.EARTH_NIGHT))));
        world.addGeometry(new Sphere(new Point3(0, 0, 0), 2, new LambertMaterial(new ImageTexture(TextureStock.EARTH_WINTER))));
        world.addGeometry(new Sphere(new Point3(5, 0, 0), 2, new LambertMaterial(new ImageTexture(TextureStock.EARTH))));

    }
}
