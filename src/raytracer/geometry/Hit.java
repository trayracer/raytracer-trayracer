package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.Normal3;
import raytracer.math.Ray;
import raytracer.texture.TexCoord2;

/**
 * This class represents a hit of a ray with a geometry.
 *
 * @author Marie Hennings
 */
public class Hit {
    /**
     * The t.
     */
    public final double t;
    /**
     * The ray.
     */
    public final Ray ray;
    /**
     * The material.
     */
    public final Material material;
    /**
     * The normal.
     */
    public final Normal3 normal;
    /**
     * The coordinates for texture.
     */
    public final TexCoord2 coord;

    /**
     * This constructor creates a new hit of a ray with a geometry at a given t.
     *
     * @param t        The t.
     * @param ray      The hit.
     * @param material The material.
     * @param normal   The normal.
     * @param coord    The TextureCoordinates.
     */
    public Hit(final double t, final Ray ray, final Material material, final Normal3 normal, final TexCoord2 coord) {
        if (ray == null || material == null || normal == null)
            throw new IllegalArgumentException("Parameters must not be null.");
        this.t = t;
        this.ray = ray;
        this.material = material;
        this.normal = normal;
        this.coord = coord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hit hit = (Hit) o;

        if (Double.compare(hit.t, t) != 0) return false;
        if (!ray.equals(hit.ray)) return false;
        if (!material.equals(hit.material)) return false;
        return normal.equals(hit.normal);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(t);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + ray.hashCode();
        result = 31 * result + material.hashCode();
        result = 31 * result + normal.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Hit{" +
                "t=" + t +
                ", ray=" + ray +
                ", material=" + material +
                ", normal=" + normal +
                '}';
    }
}
