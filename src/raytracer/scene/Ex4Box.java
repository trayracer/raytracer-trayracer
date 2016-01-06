package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.AxisAlignedBox;
import raytracer.geometry.Plane;
import raytracer.geometry.Sphere;
import raytracer.geometry.World;
import raytracer.light.PointLight;
import raytracer.material.LambertMaterial;
import raytracer.material.PhongMaterial;
import raytracer.material.SingleColorMaterial;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.sampling.SamplingPattern;
import raytracer.texture.Color;
import raytracer.texture.SingleColorTexture;
import raytracer.ui.GlobalConfig;

/**
 * This class represents demo-scene No. 1 for exercise 4.
 *
 * @author TrayRacingTeam
 */
public class Ex4Box extends RtScene {
    public Ex4Box() {
        cam = new PerspectiveCamera(new Point3(8, 8, 8), new Vector3(-1, -1, -1), new Vector3(0, 1, 0), Math.PI / 4, GlobalConfig.CAMERA_SAMPLING_PATTERN);
        world = new World(new Color(0, 0, 0), new Color(0, 0, 0));

        world.addLight(new PointLight(new Color(1, 1, 1), new Point3(8, 8, 0), true));

        world.addGeometry(new Plane(new Point3(0, 0, 0), new Normal3(0, 1, 0), new LambertMaterial(new SingleColorTexture(new Color(0.8, 0.8, 0.8)))));
        world.addGeometry(new AxisAlignedBox(new Point3(-0.5, 0, -0.5), new Point3(0.5, 1, 0.5), new LambertMaterial(new SingleColorTexture(new Color(1, 0, 0)))));
    }
}
