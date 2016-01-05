package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.*;
import raytracer.light.PointLight;
import raytracer.material.NoMaterial;
import raytracer.material.PhongMaterial;
import raytracer.math.Point3;
import raytracer.math.Transform;
import raytracer.math.Vector3;
import raytracer.sampling.SamplingPattern;
import raytracer.texture.Color;
import raytracer.texture.SingleColorTexture;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents demo-scene No. 2 for exercise 5.
 *
 * @author TrayRacer Team
 */
public class Ex5Box extends RtScene {
    public Ex5Box() {
        cam = new PerspectiveCamera(new Point3(0, 0, 5), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4, new SamplingPattern().regularPattern(4, 4));
        world = new World(new Color(0, 0, 0), new Color(0.1, 0.1, 0.1));
        AxisAlignedBox box = new AxisAlignedBox(new PhongMaterial(new SingleColorTexture(new Color(1, 1, 0)), new SingleColorTexture(new Color(1, 1, 1)), 64));
        List<Geometry> oneBox = new LinkedList<>();
        oneBox.add(box);
        world.addGeometry(new Node(new Transform().rotateY(Math.PI/4).rotateX(Math.PI/4).scale(3, 0.33, 1), oneBox, new NoMaterial()));

        world.addLight(new PointLight(new Color(0.7, 0.7, 0.7), new Point3(0, 0, 5), false));
    }
}
