package raytracer.geometry;

import raytracer.texture.Color;
import raytracer.math.Ray;

/**
 * An abstract class which represents a geometry.
 *
 * @author Steven Sobkowski
 */
public abstract class Geometry {
    /**
     * The color of the geometry.
     */
    final public Color color;

    public Geometry(final Color color){
        this.color = color;
    }

    /**
     * An abstract method to calculate a hit between a ray and a geometry.
     * @param r the ray
     * @return the hit of the geometry and the ray
     */
    public abstract Hit hit(final Ray r);

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Geometry geometry = (Geometry) o;

        return !(color != null ? !color.equals(geometry.color) : geometry.color != null);

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "color=" + color +
                '}';
    }
}
