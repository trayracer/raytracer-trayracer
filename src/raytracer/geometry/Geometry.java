package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.Ray;

/**
 * An abstract class which represents a geometry.
 *
 * @author Steven Sobkowski
 */
public abstract class Geometry {
    /**
     * The material of the geometry.
     */
    final public Material material;

    /**
     * The constructor for the geometry.
     *
     * @param material
     */
    public Geometry(final Material material){
        if (material == null) throw new IllegalArgumentException("Material must not be null.");
        this.material = material;
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

        return !(material != null ? !material.equals(geometry.material) : geometry.material != null);

    }

    @Override
    public int hashCode() {
        return material != null ? material.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "material=" + material +
                '}';
    }
}
