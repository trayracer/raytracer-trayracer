package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.texture.Color;

/**
 * Created by ok on 08.12.15.
 */
public class ReflectiveMaterial extends Material{

    public final Color diffuse;
    public final Color specular;
    public final int exponent;
    public final Color reflection;

    public ReflectiveMaterial(final Color diffuse, final Color specular, final int exponent, final Color reflection){
        this.diffuse = diffuse;
        this.specular = specular;
        this.exponent = exponent;
        this.reflection = reflection;
    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
        return null;
    }
}
