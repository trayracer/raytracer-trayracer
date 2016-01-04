package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.texture.Color;

/**
 * This Material is for the Nodes, which do not change the Material of the contained Geometries.
 *
 * @author Oliver Kniejski
 */
public class NoMaterial extends Material{

    @Override
    public Color colorFor(Hit hit, World world, Tracer tracer) {
        return null;
    }

    @Override
    public String toString() {
        return "NoMaterial{} " + super.toString();
    }
}
