package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.*;
import raytracer.texture.Color;
import raytracer.texture.TexCoord2;

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
     * @param c        Represents the center of the sphere.
     * @param radius   Represents the radius.
     * @param material Represents the color, the sphere is supposed to have.
     */
    public Sphere(final Point3 c, final double radius, final Material material) {
        super(material);
        if (radius <= 0) throw new IllegalArgumentException("Radius must be greater than zero.");
        if (c == null) throw new IllegalArgumentException("Sphere center must not be null.");
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
        if (r == null) throw new IllegalArgumentException("Ray must not be null.");
        double a = r.d.dot(r.d);
        double b = r.d.dot((r.o.sub(c)).mul(2));
        double c = ((r.o.sub(this.c)).dot(r.o.sub(this.c))) - Math.pow(radius, 2);
        double d = Math.pow(b, 2) - 4 * (a * c);

        if (d < 0) return null;
        if (d == 0) {
            double t = (-b) / (2 * a);
            if (t > Constants.EPSILON) {
                return new Hit(t, r, this, normalAt(r, t), calcTexCoord(r, t));
            }
        }
        if (d > 0) {
            double t1 = ((-b) + Math.sqrt(d)) / (2 * a);
            double t2 = ((-b) - Math.sqrt(d)) / (2 * a);
            if (t1 < t2 && t1 > Constants.EPSILON) {
                return new Hit(t1, r, this, normalAt(r, t1), calcTexCoord(r, t1));
            }
            if (t2 > Constants.EPSILON) {
                return new Hit(t2, r, this, normalAt(r, t2), calcTexCoord(r, t2));
            }
        }
        return null;
    }

    public TexCoord2 calcTexCoord(final Ray ray, final double t) {
        Point3 hitpoint = ray.at(t);
        double phi = Math.atan(hitpoint.x / hitpoint.z);
        double theta = Math.acos(hitpoint.y);
        double u = phi / (2 * Math.PI);
        double v = 1 - (theta / Math.PI);
        return new TexCoord2(u, v);
    }

    /**
     * This method takes a ray and a double t and calculates the hitpoint and returns the normal of that point
     *
     * @param r the ray
     * @param t the double
     * @return the normal of the hitpoint.
     */
    public Normal3 normalAt(final Ray r, final double t) {
        final Point3 hitpoint = r.at(t);
        final Vector3 hitvector = hitpoint.sub(this.c);
        return hitvector.asNormal();
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
