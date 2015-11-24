package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.texture.Color;

/**
 * Created on 24.11.15.
 */
public class SingleColorMaterial extends Material {

    public final Color color;

    public SingleColorMaterial(final Color color){
        this.color = color;
    }

    @Override
    public Color colorFor(final Hit hit, final World world) {
        return null;
    }
}
