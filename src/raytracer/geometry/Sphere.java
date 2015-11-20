package raytracer.geometry;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.texture.Color;

/**
 * This class represents a sphere.
 *
 * @author Steven Sobkowski
 */
public class Sphere extends Geometry {
    /**
     * The middle point of the sphere.
     */
    public final Point3 c;

    /**
     * The radius of the sphere represented by a double.
     */
    public final double radius;

    /**
     * This constructor creates a sphere with a point a double and a color.
     *
     * @param c      Represents the center of the sphere.
     * @param radius Represents the radius.
     * @param color  Represents the color, the sphere is supposed to have.
     */
    public Sphere(final Point3 c, final double radius, final Color color) {
        super(color);
        if (radius <= 0 ){
            throw new IllegalArgumentException("Radius must be greater than zero.");
        }
        this.c = c;
        this.radius = radius;
    }

    /**
     * This method creates and returns the hit of this sphere and a ray.
     *
     * @param r The ray.
     * @return The hit of the ray and the geometry.
     */
    public final Hit hit(final Ray r) {
        double a = r.d.dot(r.d);
        double b = r.d.dot((r.o.sub(c)).mul(2));
        double c = ((r.o.sub(this.c)).dot(r.o.sub(this.c))) - Math.pow(radius, 2);
        double d = Math.pow(b, 2) - 4 * (a * c);

        if (d < 0) {
            return null;
        }
        if (d == 0) {
            double t = (-b) / (2 * a);
            if (t < 0) return null;
            return new Hit(t, r, this);
        }
        if (d > 0) {
            double t1 = ((-b) + Math.sqrt(d)) / (2 * a);
            double t2 = ((-b) - Math.sqrt(d)) / (2 * a);
            if (t1 < t2 && t1 > 0) {
                return new Hit(t1, r, this);
            }
            if (t2 > 0) {
                return new Hit(t2, r, this);
            }
        }
        return null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Sphere sphere = (Sphere) o;

        return Double.compare(sphere.radius, radius) == 0 && c.equals(sphere.c);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + c.hashCode();
        temp = Double.doubleToLongBits(radius);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "c=" + c +
                ", radius=" + radius +
                '}';
    }
}
