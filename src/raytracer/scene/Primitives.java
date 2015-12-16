package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.*;
import raytracer.light.PointLight;
import raytracer.light.SpotLight;
import raytracer.material.PhongMaterial;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;
import raytracer.texture.ImageTexture;
import raytracer.texture.SingleColorTexture;
import raytracer.texture.TextureStock;

/**
 * This is a scene with all the primitive geometries and some light.
 *
 * @author Oliver Kniejski
 */
public class Primitives extends RtScene {
    public Primitives() {
        cam = new PerspectiveCamera(new Point3(10, 5, 10), new Vector3(-10, -5, -8), new Vector3(0, 0, 1), Math.PI / 4);
        world = new World(new Color(0, 0, 0), new Color(0.4, 0.4, 0.4));

        world.addGeometry(new Plane(new Point3(0, 0, -0.5), new Normal3(0, 0, 1), new PhongMaterial(new SingleColorTexture(new Color(0, 0, 1)),new SingleColorTexture( new Color(1, 1, 1)), 64)));
        world.addGeometry(new raytracer.geometry.Torus(2, 0.5, new PhongMaterial(new SingleColorTexture(new Color(1, 0, 0)), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new Sphere(new Point3(3, -3, 0.5), 2, new PhongMaterial(new ImageTexture(TextureStock.EARTH_WINTER), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new AxisAlignedBox(new Point3(-4, -6, -0.5), new Point3(-1, -4, 4), new PhongMaterial(new SingleColorTexture(new Color(1, 0, 1)), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new ZAxisAlignedCylinder(new Point3(-6, -2, -0.5), 4, 1.5, new PhongMaterial(new ImageTexture(TextureStock.EARTH_WINTER), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new ZAxisAlignedCone(new Point3(-5, 4, 5), -5.5, -2, 3, new PhongMaterial(new SingleColorTexture(new Color(1, 1, 0)), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new Triangle(new Point3(-7, 1, 2), new Point3(-8, -1, 3), new Point3(-9, 3, 4), new PhongMaterial(new SingleColorTexture(new Color(0, 1, 0)), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new Disc(new Point3(1, -6, 4), new Normal3(1, 1, 3), 1.5, new PhongMaterial(new SingleColorTexture(new Color(0.5, 0.5, 1)), new SingleColorTexture(new Color(1, 1, 1)), 16)));

        world.addLight(new PointLight(new Color(0.5, 0.5, 0), new Point3(0, 10, 5), true));
        world.addLight(new PointLight(new Color(0, 0.3, 0.3), new Point3(6, -7, 5), true));
        world.addLight(new SpotLight(new Color(0.5, 0.5, 0.5), new Point3(8, 3, 8), new Vector3(-3, -3, -4), Math.PI /8, true));
    }
}
