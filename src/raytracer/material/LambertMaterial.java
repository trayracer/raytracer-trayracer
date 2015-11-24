package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.light.Light;
import raytracer.texture.Color;

import java.util.List;

/**
 * This class represents a Lambert material.
 *
 * @author Oliver Kniejski
 */
public class LambertMaterial extends Material{
    /**
     * The color property of this material.
     */
    public final Color color;

    /**
     * This constructor creates a new Lambert material.
     * @param color The color of the material.
     */
    public LambertMaterial(final Color color) {
        if (color == null)  throw new IllegalArgumentException("color must not be null.");
        this.color = color;
    }

    @Override
    public Color colorFor(final Hit hit, final World world) {
        if (hit == null || world == null) throw new IllegalArgumentException("Parameters must not be null.");
        List<Light> lights = world.getLights();
        Color c = color.mul(world.ambientColor);
        for (Light l : lights){
            c = c.add(color.mul(l.color).mul(Math.max(0, l.directionFrom(hit.ray.at(hit.t)).dot(hit.normal))));
        }
        return c;
    }
}
