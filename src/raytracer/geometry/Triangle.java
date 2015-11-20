package raytracer.geometry;

import raytracer.math.Mat3x3;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;
import raytracer.texture.Color;

import java.util.Vector;

/**
 * This class represents a triangle.
 *
 * @author Oliver Kniejski
 */
public class Triangle extends Geometry{

    public final Point3 a;
    public final Point3 b;
    public final Point3 c;

    /**
     * This constructor creates a triangle with 3 Points.
     *
     * @param a The point a of the triangle.
     * @param b The point b of the triangle.
     * @param c The point c of the triangle.
     * @param color The color of the triangle.
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Color color) {
        super(color);
        if (a.equals(b) || a.equals(c) || b.equals(c)) throw new IllegalArgumentException("The triangle needs three unique points.");
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * This method creates and returns the hit of this triangle and a ray.
     *
     * @param ray The ray.
     * @return The hit of the ray and the triangle or null if the triangle is not hit.
     */
    public Hit hit(final Ray ray){
        Mat3x3 matA = new Mat3x3(a.x - b.x, a.x - c.x, ray.d.x, a.y - b.y, a.y - c.y, ray.d.y, a.z - b.z, a.z - c.z, ray.d.z);
        Vector3 right = new Vector3(a.x - ray.o.x, a.y - ray.o.y, a.z - ray.o.z);
        double beta = matA.changeCol1(right).determinant / matA.determinant;
        double gamma = matA.changeCol2(right).determinant / matA.determinant;
        double t = matA.changeCol3(right).determinant / matA.determinant;
        if (0 < t && 0 <= beta && beta <= 1 && 0 <= gamma && gamma <= 1 && beta+gamma<=1){
            return new Hit(t, ray, this);
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
