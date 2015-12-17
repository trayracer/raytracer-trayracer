package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.*;
import raytracer.light.PointLight;
import raytracer.material.LambertMaterial;
import raytracer.material.PhongMaterial;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;
import raytracer.texture.ImageTexture;
import raytracer.texture.SingleColorTexture;
import raytracer.texture.TextureStock;

/**
 * A festive scene with 3 cones, 4 spheres, 2 triangles, 4 boxes and a cylinder.
 *
 * @author Oliver Kniejski
 */
public class Cone extends RtScene {
    public Cone() {
        cam = new PerspectiveCamera(new Point3(9, 0, 1.5), new Vector3(-1, 0, 0), new Vector3(0, 0, 1), Math.PI / 4);
        world = new World(new Color(0, 0, 0), new Color(0.2, 0.2, 0.2));
        // Planes
        world.addGeometry(new Plane(new Point3(0, 0, -1), new Normal3(0, 0, 1), new LambertMaterial(new ImageTexture(TextureStock.XMAS_CARPET)), 5));
        world.addGeometry(new Plane(new Point3(-3, 0, 0), new Normal3(1, 0, 0), new LambertMaterial(new ImageTexture(TextureStock.WALL)), 4));
        // Nails
        world.addGeometry(new ZAxisAlignedCone(new Point3(0, 0, 4), -2, 0, 5, new PhongMaterial(new ImageTexture(TextureStock.FIR_BRANCHES), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new ZAxisAlignedCone(new Point3(0, 0, 3), -2, 0, 3, new PhongMaterial(new ImageTexture(TextureStock.FIR_BRANCHES), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new ZAxisAlignedCone(new Point3(0, 0, 2), -2, 0, 2, new PhongMaterial(new ImageTexture(TextureStock.FIR_BRANCHES), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        // Trunk
        world.addGeometry(new ZAxisAlignedCylinder(new Point3(0, 0, -1), 2, 0.2, new LambertMaterial(new ImageTexture(TextureStock.BARK))));
        // Spheres
        world.addGeometry(new Sphere(new Point3(0.5, 1, 1), 0.2, new PhongMaterial(new ImageTexture(TextureStock.CHRISTMAS_BALL), (new SingleColorTexture(new Color(1, 0.2, 0.2))), 32)));
        world.addGeometry(new Sphere(new Point3(1.15, 0.5, 0), 0.2, new PhongMaterial(new ImageTexture(TextureStock.CHRISTMAS_BALL), (new SingleColorTexture(new Color(1, 0.2, 0.2))), 32)));
        world.addGeometry(new Sphere(new Point3(0.5, -0.8, 1.5), 0.2, new PhongMaterial(new ImageTexture(TextureStock.CHRISTMAS_BALL), new SingleColorTexture(new Color(1, 0.2, 0.2)), 32)));
        world.addGeometry(new Sphere(new Point3(0.7, 0.4, 2.1), 0.2, new PhongMaterial(new ImageTexture(TextureStock.CHRISTMAS_BALL), new SingleColorTexture(new Color(1, 0.2, 0.2)), 32)));
        // Star
        world.addGeometry(new Triangle(new Point3(0, 0, 4), new Point3(0, 0.25, 4.3), new Point3(0, -0.25, 4.3), new PhongMaterial(new ImageTexture(TextureStock.STAR), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        world.addGeometry(new Triangle(new Point3(0, 0, 4.4), new Point3(0, -0.25, 4.1), new Point3(0, 0.25, 4.1), new PhongMaterial(new ImageTexture(TextureStock.STAR), new SingleColorTexture(new Color(1, 1, 1)), 64)));
        // Gifts
        world.addGeometry(new AxisAlignedBox(new Point3(0, -0.9 ,-1), new Point3(0.5, -0.3, -0.4), new PhongMaterial(new ImageTexture(TextureStock.WRAPPING_PAPER1), new SingleColorTexture(new Color(1, 1, 1)), 64), 1));
        world.addGeometry(new AxisAlignedBox(new Point3(0.5, -1 ,-1), new Point3(1.5, 0, -0.8), new PhongMaterial(new ImageTexture(TextureStock.WRAPPING_PAPER3), new SingleColorTexture(new Color(1, 1, 1)), 64), 1));
        world.addGeometry(new AxisAlignedBox(new Point3(0.2, 0.2 ,-1), new Point3(1.3, 1, -0.4), new PhongMaterial(new ImageTexture(TextureStock.WRAPPING_PAPER2), new SingleColorTexture(new Color(1, 1, 1)), 64), 1));
        world.addGeometry(new AxisAlignedBox(new Point3(1, 1.1 ,-1), new Point3(1.2, 1.5, -0.1), new PhongMaterial(new ImageTexture(TextureStock.WRAPPING_PAPER4), new SingleColorTexture(new Color(1, 1, 1)), 64), 1));
        // Lights
        world.addLight(new PointLight(new Color(0.2, 0.2, 0.2), new Point3(6, 5, 0), true));
        world.addLight(new PointLight(new Color(0.2, 0.2, 0.2), new Point3(6, -5, 0), true));
        world.addLight(new PointLight(new Color(0.2, 0.2, 0.2), new Point3(1, 0.5, 4.2), true));
    }
}
