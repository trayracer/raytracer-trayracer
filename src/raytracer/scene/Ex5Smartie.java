package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.Geometry;
import raytracer.geometry.Node;
import raytracer.geometry.Sphere;
import raytracer.geometry.World;
import raytracer.light.PointLight;
import raytracer.material.NoMaterial;
import raytracer.material.PhongMaterial;
import raytracer.math.Point3;
import raytracer.math.Transform;
import raytracer.math.Vector3;
import raytracer.texture.Color;
import raytracer.texture.SingleColorTexture;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents demo-scene No. 1 for exercise 5.
 *
 * @author Oliver Kniejski
 */
public class Ex5Smartie extends RtScene {
    public Ex5Smartie() {
        cam = new PerspectiveCamera(new Point3(0, -5, 0), new Vector3(0, 1, 0), new Vector3(0, 0, 1), Math.PI / 4);
        world = new World(new Color(0, 0, 0), new Color(0.1, 0.1, 0.1));
        Sphere ball = new Sphere(new PhongMaterial(new SingleColorTexture(new Color(1, 0, 0)), new SingleColorTexture(new Color(1, 1, 1)), 64));
        List<Geometry> geos = new LinkedList<>();
        geos.add(ball);
        Transform trans = new Transform();
        Node smartie = new Node(trans.scale(1, 1, 0.3).rotateX(Math.PI/4).rotateZ(Math.PI/4), geos, new NoMaterial() );
        world.addGeometry(smartie);
        world.addLight(new PointLight(new Color(0.7, 0.7, 0.7), new Point3(-1, -4, 0), false));
    }
}
