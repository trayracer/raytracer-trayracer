package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.*;
import raytracer.texture.Color;

import java.util.Vector;

/**
 * This class represents a triangle.
 *
 * @author Oliver Kniejski & Marie Hennings
 */
public class Triangle extends Geometry {
    /**
     * The point a.
     */
    public final Point3 a;
    /**
     * The point b.
     */
    public final Point3 b;
    /**
     * The point c.
     */
    public final Point3 c;
    /**
     * The normal at point a.
     */
    public final Normal3 na;
    /**
     * The normal at point b.
     */
    public final Normal3 nb;
    /**
     * The normal at point c.
     */
    public final Normal3 nc;

    /**
     * This constructor creates a triangle with 3 Points.
     *
     * @param a     The point a of the triangle.
     * @param b     The point b of the triangle.
     * @param c     The point c of the triangle.
     * @param material The material of the triangle.
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Material material) {
        super(material);
        if (a == null || b == null || c == null)
            throw new IllegalArgumentException("Triangle points must not be null.");
        if (a.equals(b) || a.equals(c) || b.equals(c))
            throw new IllegalArgumentException("The triangle needs three unique points.");
        this.a = a;
        this.b = b;
        this.c = c;
        this.na = (b.sub(a)).x(c.sub(a)).asNormal();
        this.nb = this.na;
        this.nc = this.na;
    }

    /**
     * This constructor creates a triangle with 3 Points.
     *
     * @param a     The point a of the triangle.
     * @param b     The point b of the triangle.
     * @param c     The point c of the triangle.
     * @param na    The normal at point a.
     * @param nb    The normal at point b.
     * @param nc    The normal at point c.
     * @param material The material of the triangle.
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Normal3 na, final Normal3 nb, final Normal3 nc, final Material material) {
        super(material);
        if (a == null || b == null || c == null || na == null || nb == null || nc == null)
            throw new IllegalArgumentException("Triangle points must not be null.");
        if (a.equals(b) || a.equals(c) || b.equals(c))
            throw new IllegalArgumentException("The triangle needs three unique points.");
        this.a = a;
        this.b = b;
        this.c = c;
        this.na = na;
        this.nb = nb;
        this.nc = nc;
    }

    /**
     * This method return the normal for a given point.
     *
     * @param point The point where the normal is calculated.
     * @return The normal at the point.
     */
    public Normal3 normalAt(final Point3 point) {
        if (point == null) throw new IllegalArgumentException("Point must not be null.");
        double area = (b.sub(a)).x((c.sub(a))).magnitude;
        double beta = (b.sub(a)).x((point.sub(a))).magnitude / area;
        double gamma = (c.sub(a)).x((point.sub(a))).magnitude / area;
        double alpha = 1.0 - beta - gamma;

        if (alpha < 0 || alpha > 1 || beta < 0 || beta > 1 || gamma < 0 || gamma > 1 || alpha + beta + gamma > 1) {
            throw new IllegalArgumentException("Point must be in triangle.");
        }

        return (na.mul(alpha)).add((nb.mul(beta)).add(nc.mul(gamma)));
    }

    /**
     * This method creates and returns the hit of this triangle and a ray.
     *
     * @param ray The ray.
     * @return The hit of the ray and the triangle or null if the triangle is not hit.
     */
    public Hit hit(final Ray ray) {
        if (ray == null) throw new IllegalArgumentException("Ray must not be null.");
        Mat3x3 matA = new Mat3x3(a.x - b.x, a.x - c.x, ray.d.x, a.y - b.y, a.y - c.y, ray.d.y, a.z - b.z, a.z - c.z, ray.d.z);
        Vector3 right = new Vector3(a.x - ray.o.x, a.y - ray.o.y, a.z - ray.o.z);
        double detA = matA.determinant;
        if (detA == 0) return null;
        double beta = matA.changeCol1(right).determinant / detA;
        double gamma = matA.changeCol2(right).determinant / detA;
        double t = matA.changeCol3(right).determinant / detA;
        if (0 < t && 0 <= beta && beta <= 1 && 0 <= gamma && gamma <= 1 && beta + gamma <= 1) {
            return new Hit(t, ray, this, normalAt(ray.at(t)));
        }
        return null;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Triangle triangle = (Triangle) o;

        if (a != null ? !a.equals(triangle.a) : triangle.a != null) return false;
        if (b != null ? !b.equals(triangle.b) : triangle.b != null) return false;
        return !(c != null ? !c.equals(triangle.c) : triangle.c != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (a != null ? a.hashCode() : 0);
        result = 31 * result + (b != null ? b.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                "} " + super.toString();
    }
}
