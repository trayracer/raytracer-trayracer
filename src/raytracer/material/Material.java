package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;

/**
 *
 * 
 * @author Marie Hennings
 */
public abstract class Material {

    /**
     *
     * @param hit
     * @param world
     * @return
     */
    public abstract World colorFor(Hit hit, World world);
}
