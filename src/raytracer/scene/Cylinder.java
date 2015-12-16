package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.*;
import raytracer.light.PointLight;
import raytracer.material.PhongMaterial;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;
import raytracer.texture.SingleColorTexture;

/**
 * This method creates a greek test scene with some cylinders n stuff.
 *
 * @author Oliver Kniejski
 */
public class Cylinder extends RtScene {
    public Cylinder() {
        cam = new PerspectiveCamera(new Point3(35, 0, 1), new Vector3(-30, 0, 1), new Vector3(0, 0, 1), Math.PI / 4);
//        cam = new PerspectiveCamera(new Point3(35, 20, 2), new Vector3(-35, -20, 0), new Vector3(0, 0, 1), Math.PI / 4);
        world = new World(new Color(0.2, 0.2, 0.2), new Color(0.2, 0.2, 0.2));

        // Roof
        world.addGeometry(new Triangle(new Point3(12, 0, 8.75), new Point3(12,-13, 6.75), new Point3(12, 13, 6.75), new PhongMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.7)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
        world.addGeometry(new Sphere(new Point3(11.8, 0, 7.75), 0.6, new PhongMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.7)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
        world.addGeometry(new AxisAlignedBox(new Point3(-12.75, -14.25, 6.5), new Point3(12.75, 14.25 , 6.75), new PhongMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.7)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
        world.addGeometry(new AxisAlignedBox(new Point3(-12.5, -14, 6.25), new Point3(12.5, 14 , 6.5), new PhongMaterial(new SingleColorTexture(new Color(0.7, 0.7, 0.6)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
        world.addGeometry(new AxisAlignedBox(new Point3(-12.75, -14.25, 6), new Point3(12.75, 14.25 , 6.25), new PhongMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.7)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
        // Base
        world.addGeometry(new AxisAlignedBox(new Point3(-12.75, -14.25, -0.5), new Point3(12.75, 14.25 , 0), new PhongMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.7)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
        world.addGeometry(new AxisAlignedBox(new Point3(-13.25, -14.75, -1), new Point3(13.25, 14.75 , -0.5), new PhongMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.7)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
        // columns
        for (double x : new double[]{-10.5, 10.5}){
            for (int y = -12; y < 13; y += 3) {
                if(y != 0) makeColumn(x, y);
            }
        }
        for (int y : new int[]{-12, -3, 3, 12}){
            for (double x = -7.5; x < 8; x += 3) makeColumn(x, y);
        }
        // lights
        world.addLight(new PointLight(new Color(0.5, 0.5, 0.5), new Point3(0, 7.5, 0.5), true));
        world.addLight(new PointLight(new Color(0.5, 0.5, 0.5), new Point3(0, -7.5, 0.5), true));
        world.addLight(new PointLight(new Color(0.5, 0.5, 0.5), new Point3(15, -5, 8), true));
    }

    /**
     * This method creates a greek column at the specified coordinates with z = 0;
     * @param x The x-coordinate.
     * @param y The z-coordinate.
     */
    private void makeColumn(final double x, final double y){
        world.addGeometry(new AxisAlignedBox(new Point3(x - 0.75, y - 0.75, 5.7), new Point3(x + 0.75, y + 0.75 , 6), new PhongMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.7)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
        world.addGeometry(new AxisAlignedBox(new Point3(x - 0.6, y - 0.6, 5.5), new Point3(x + 0.6, y + 0.6 , 5.7), new PhongMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.7)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
        world.addGeometry(new Sphere(new Point3(x, y, 5.5), 0.55, new PhongMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.7)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
        world.addGeometry(new ZAxisAlignedCylinder(new Point3(x, y, 0.5), 5, 0.5, new PhongMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.7)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
        world.addGeometry(new AxisAlignedBox(new Point3(x - 0.6, y - 0.6, 0.3), new Point3(x + 0.6, y + 0.6 , 0.5), new PhongMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.7)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
        world.addGeometry(new AxisAlignedBox(new Point3(x - 0.75, y - 0.75, 0), new Point3(x + 0.75, y + 0.75 , 0.3), new PhongMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.7)), new SingleColorTexture(new Color(1, 1, 1)), 32)));
    }
}
