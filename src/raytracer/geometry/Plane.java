package raytracer.geometry;

import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.texture.Color;

/**
 * This class represents a plane.
 */
public class Plane extends Geometry{
    /**
     * A point of the plane.
     */
    public final Point3 a;
    /**
     * The normal of the plane.
     */
    public final Normal3 n;

    /**
     * This constructor creates a plane of a point and the normal.
     * @param a A Point.
     * @param n The normal.
     * @param color The color.
     */
    public Plane(final Point3 a, final Normal3 n, final Color color) {
        super(color);
        this.a = a;
        this.n = n;
    }

    /**
     * This method creates and returns the hit of this plane and a ray.
     * @param r The ray.
     * @return The hit of the ray and the geometry.
     */
    public Hit hit(final Ray r){
        double t = ((a.sub(r.o)).dot(n)) / ((r.d).dot(n));
        if (t>0) {
            return new Hit(t, r, this);
        }
        return null;
    }
}
