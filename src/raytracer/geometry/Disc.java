package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.Constants;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Ray;

/**
 * This class represents a Disk.
 *
 * @author Oliver Kniejski
 */
public class Disc extends Geometry {
    /**
     * The radius.
     */
    private final double radius;
    /**
     * The center.
     */
    private final Point3 c;
    /**
     * The normal.
     */
    private final Normal3 n;

    /**
     * This constructor creates a new Disk.
     *
     * @param c        The center.
     * @param n        The normal.
     * @param radius   The radius.
     * @param material The material.
     */
    public Disc(final Point3 c, final Normal3 n, final double radius, final Material material) {
        super(material);
        if (c == null || n == null) throw new IllegalArgumentException("Parameters must not be null.");
        this.radius = radius;
        this.c = c;
        this.n = n;
    }

    @Override
    public Hit hit(final Ray ray) {
        if (ray == null) throw new IllegalArgumentException("Ray must not be null.");
        double t = ((c.sub(ray.o)).dot(n)) / ((ray.d).dot(n));
        if (t > Constants.EPSILON && ray.at(t).sub(c).dot(ray.at(t).sub(c)) <= Math.pow(radius, 2)) {
            return new Hit(t, ray, this, n);
        }
        return null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Disc disc = (Disc) o;

        return Double.compare(disc.radius, radius) == 0 && !(c != null ? !c.equals(disc.c) : disc.c != null)
                && !(n != null ? !n.equals(disc.n) : disc.n != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(radius);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (c != null ? c.hashCode() : 0);
        result = 31 * result + (n != null ? n.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Disc{" +
                "radius=" + radius +
                ", c=" + c +
                ", n=" + n +
                "} " + super.toString();
    }
}
