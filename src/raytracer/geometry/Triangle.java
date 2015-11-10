package raytracer.geometry;

import raytracer.math.Mat3x3;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;
import raytracer.texture.Color;

import java.util.Vector;

/**
 * This class
 *
 * @author Oliver Kniejski
 */
public class Triangle extends Geometry{

    public final Point3 a;
    public final Point3 b;
    public final Point3 c;

    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Color color) {
        super(color);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Hit hit(Ray ray){
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
}
