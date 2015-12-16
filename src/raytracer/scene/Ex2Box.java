package raytracer.scene;

import raytracer.camera.PerspectiveCamera;
import raytracer.geometry.AxisAlignedBox;
import raytracer.geometry.World;
import raytracer.light.DirectionalLight;
import raytracer.light.SpotLight;
import raytracer.material.PhongMaterial;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;
import raytracer.texture.SingleColorTexture;

/**
 * Sets a perspective camera and adds a box to the world.
 *
 * @author TrayRacingTeam
 */
public final class Ex2Box extends RtScene {
    public Ex2Box() {
        cam = new PerspectiveCamera(new Point3(3, 3, 3), new Vector3(-3, -3, -3), new Vector3(0, 1, 0), Math.PI / 4);
        world = new World(new Color(0, 0, 0), new Color(1, 1, 1));
        world.addGeometry(new AxisAlignedBox(new Point3(-0.5, 0, -0.5), new Point3(0.5, 1, 0.5), new PhongMaterial(new SingleColorTexture(new Color(0, 0, 1)), new SingleColorTexture(new Color(1, 1, 1)), 64)));
    }
}