package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.light.Light;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;
import raytracer.texture.Texture;

import java.util.List;

/**
 * This class represents a Lambert material with perfect diffuse reflection.
 *
 * @author Oliver Kniejski
 */
public class LambertMaterial extends Material {
    /**
     * The color property of this material.
     */
    public final Texture texture;

    /**
     * This constructor creates a new Lambert material.
     *
     * @param texture The texture of the material.
     */
    public LambertMaterial(final Texture texture) {
        if (texture == null) throw new IllegalArgumentException("Color must not be null.");
        this.texture = texture;
    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
        if (hit == null || world == null) throw new IllegalArgumentException("Parameters must not be null.");
        List<Light> lights = world.getLights();
        Color color = texture.getColor(hit.coord);
        Color c = color.mul(world.ambientColor);
        for (Light light : lights) {
            Point3 hitpoint = hit.ray.at(hit.t);
            if (light.illuminates(hitpoint, world)) {
                Normal3 n = hit.normal;
                Vector3 l = light.directionFrom(hitpoint).normalized();

                Color lambert = color.mul(light.color).mul(Math.max(0, l.dot(n)));

                c = c.add(lambert);
            }
        }
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LambertMaterial that = (LambertMaterial) o;

        return texture.equals(that.texture);

    }

    @Override
    public int hashCode() {
        return texture.hashCode();
    }

    @Override
    public String toString() {
        return "LambertMaterial{" +
                "texture=" + texture +
                '}';
    }
}
