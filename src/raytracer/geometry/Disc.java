package raytracer.geometry;

import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.texture.Color;

/**
 * Created by ok on 12.11.15.
 *
 * Scheibe ohne Dicke
 */
public class Disc extends Geometry {
    private final double radius;
    private final Point3 c;
    private final Normal3 n;

    public Disc(Point3 c, Normal3 n, double radius, Color color) {
        super(color);
        this.radius = radius;
        this.c = c;
        this.n = n;
    }

    @Override
    public Hit hit(Ray ray) {
        double t = ((c.sub(ray.o)).dot(n)) / ((ray.d).dot(n));
        if (t>0 && ray.at(t).sub(c).dot(ray.at(t).sub(c))<= radius*radius) {
            return new Hit(t, ray, this);
        }
        return null;
    }
}
