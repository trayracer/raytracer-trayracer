package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.texture.Color;

/**
 * An abstract class which represents a material.
 *
 * @author Marie Hennings
 */
public abstract class Material {

    /**
     * This method returns the color of a hit in the world.
     *
     * @param hit   The hit.
     * @param world The world with the lights.
     * @return Color of the Point of the Hit.
     */
    public abstract Color colorFor(final Hit hit, final World world);
}
