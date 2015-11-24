package raytracer.geometry;

import raytracer.math.Normal3;
import raytracer.math.Ray;

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
     * The geometry.
     */
    public final Geometry geo;

    public final Normal3 normal;

    /**
     * This constructor creates a new hit of a ray with a geometry at a given t.
     *
     * @param t   The t.
     * @param ray The hit.
     * @param geo The geometry.
     */
    public Hit(final double t, final Ray ray, final Geometry geo, final Normal3 normal) {
        if (ray == null || geo == null) throw new IllegalArgumentException("Parameters must not be null.");
        this.t = t;
        this.ray = ray;
        this.geo = geo;
        this.normal = normal;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hit hit = (Hit) o;

        if (Double.compare(hit.t, t) != 0) return false;
        if (!ray.equals(hit.ray)) return false;
        return geo.equals(hit.geo);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(t);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + ray.hashCode();
        result = 31 * result + geo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Hit{" +
                "t=" + t +
                ", ray=" + ray +
                ", geo=" + geo +
                '}';
    }
}
