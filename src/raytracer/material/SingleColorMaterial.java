package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.texture.Color;

/**
 * This class represents a a single color material.
 *
 * @author Marie Hennings
 */
public class SingleColorMaterial extends Material {
    /**
     * The color of the material.
     */
    public final Color color;

    /**
     * The constructor.
     *
     * @param color Color of the material.
     */
    public SingleColorMaterial(final Color color) {
        if (color == null) throw new IllegalArgumentException("Color must not be null.");
        this.color = color;
    }

    @Override
    public Color colorFor(final Hit hit, final World world) {
        if (hit == null || world == null) throw new IllegalArgumentException("Parameters must not be null.");
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleColorMaterial that = (SingleColorMaterial) o;

        return color.equals(that.color);

    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

    @Override
    public String toString() {
        return "SingleColorMaterial{" +
                "color=" + color +
                '}';
    }
}
