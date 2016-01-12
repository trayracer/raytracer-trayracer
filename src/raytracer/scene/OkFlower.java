package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.*;
import raytracer.material.LambertMaterial;
import raytracer.material.Material;
import raytracer.material.NoMaterial;
import raytracer.material.PhongMaterial;
import raytracer.math.Point3;
import raytracer.math.Transform;
import raytracer.math.Vector3;
import raytracer.texture.Color;
import raytracer.texture.ImageTexture;
import raytracer.texture.SingleColorTexture;
import raytracer.texture.TextureStock;
import raytracer.ui.GlobalConfig;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ok on 12.01.16.
 */
public class OkFlower extends RtScene {
    public OkFlower() {
        cam = new PerspectiveCamera(new Point3(0, 0, -10), new Vector3(0, 0, 10), new Vector3(0, 1, 0), Math.PI /4, GlobalConfig.CAMERA_SAMPLING_PATTERN);
        world = new World(new Color(0, 0, 0), new Color(0.1, 0.1, 0.1));

        Sphere mittelKugel = new Sphere(new LambertMaterial(new ImageTexture(TextureStock.CHRISTMAS_BALL)));
        List<Geometry> kugelListe = new LinkedList<Geometry>(Arrays.asList(mittelKugel));
        Node mitte = new Node(new Transform().scale(1, 0.2, 1), kugelListe, new NoMaterial());

//        Disc leaf = new Disc();
    }
}
