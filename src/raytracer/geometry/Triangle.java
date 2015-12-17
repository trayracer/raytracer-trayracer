package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.*;
import raytracer.texture.TexCoord2;

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
     * The texture coordinate of point a.
     */
    public final TexCoord2 ta;
    /**
     * The texture coordinate of point b.
     */
    public final TexCoord2 tb;
    /**
     * The texture coordinate of point c.
     */
    public final TexCoord2 tc;

    /**
     * This constructor creates a triangle with 3 Points and the normal.
     *
     * @param a        The point a of the triangle.
     * @param b        The point b of the triangle.
     * @param c        The point c of the triangle.
     * @param material The material of the triangle.
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Material material) {
        this(a, b, c, (b.sub(a)).x(c.sub(a)).asNormal(), (b.sub(a)).x(c.sub(a)).asNormal(), (b.sub(a)).x(c.sub(a)).asNormal(), material);
    }

    /**
     * This constructor creates a triangle with 3 Points and 3 specified normals and default texture coordinates.
     *
     * @param a        The point a of the triangle.
     * @param b        The point b of the triangle.
     * @param c        The point c of the triangle.
     * @param na       The normal at point a.
     * @param nb       The normal at point b.
     * @param nc       The normal at point c.
     * @param material The material of the triangle.
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Normal3 na, final Normal3 nb, final Normal3 nc, final Material material) {
        this(a, b, c, na, nb, nc, new TexCoord2(0, 0), new TexCoord2(0, 1), new TexCoord2(1, 1), material);
    }

    /**
     * This constructor creates a triangle with 3 Points, 3 specified normals and 3 specified texture coordinates.
     *
     * @param a        The point a of the triangle.
     * @param b        The point b of the triangle.
     * @param c        The point c of the triangle.
     * @param na       The normal at point a.
     * @param nb       The normal at point b.
     * @param nc       The normal at point c.
     * @param ta       The texture coordinate at point a.
     * @param tb       The texture coordinate at point b.
     * @param tc       The texture coordinate at point c.
     * @param material The material of the triangle.
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Normal3 na, final Normal3 nb, final Normal3 nc, final TexCoord2 ta, final TexCoord2 tb, final TexCoord2 tc, final Material material) {
        super(material);
        if (a == null || b == null || c == null || na == null || nb == null || nc == null || ta == null || tb == null || tc == null)
            throw new IllegalArgumentException("Parameters must not be null.");
        if (a.equals(b) || a.equals(c) || b.equals(c))
            throw new IllegalArgumentException("The triangle needs three unique points.");
        this.a = a;
        this.b = b;
        this.c = c;
        this.na = na;
        this.nb = nb;
        this.nc = nc;
        this.ta = ta;
        this.tb = tb;
        this.tc = tc;
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
        if (Constants.EPSILON < t && 0 <= beta && beta <= 1 && 0 <= gamma && gamma <= 1 && beta + gamma <= 1) {
            //create normal and normalize
            double alpha = 1.0 - beta - gamma;
            Normal3 normal = (na.mul(alpha)).add((nb.mul(beta)).add(nc.mul(gamma)));
            normal = normal.mul(1 / (Math.sqrt(normal.x * normal.x + normal.y * normal.y + normal.z * normal.z)));
            //TODO
            TexCoord2 coord2 = new TexCoord2(ta.u * alpha + tb.u * beta + tc.u * gamma, ta.v * alpha + tb.v * beta + tc.v * gamma);
            return new Hit(t, ray, this, normal, coord2);
        }
        return null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Triangle triangle = (Triangle) o;

        return !(a != null ? !a.equals(triangle.a) : triangle.a != null)
                && !(b != null ? !b.equals(triangle.b) : triangle.b != null)
                && !(c != null ? !c.equals(triangle.c) : triangle.c != null);
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
