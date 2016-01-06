package raytracer.scene;

import raytracer.camera.Camera;
import raytracer.geometry.World;

/**
 * This is an abstract class for collecting scene properties.
 *
 * @author Oliver Kniejski
 */
public abstract class RtScene {
    public Camera cam;
    public World world;

    public RtScene(){}

    public Camera getCam(){
        return cam;
    }

    public World getWorld(){
        return world;
    }
}
