package raytracer.geometry;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.texture.Color;

/**
 * Created by Steve-O on 10.11.2015.
 */
public class Sphere extends Geometry {

    public final Point3 c;
    public final double radius;

    public Sphere(final Point3 c, final double radius, final Color color){
        super(color);
        this.c = c;
        this.radius = radius;

    }

    public final Hit hit(Ray r){
        double a = r.d.dot(r.d);
        double b = r.d.dot((r.o.sub(c)).mul(2));
        double c = ((r.o.sub(this.c)).dot(r.o.sub(this.c)))- Math.pow(radius,2);
        double d = Math.pow(b,2) - 4*(a*c);

        if(d < 0) {
            return null;
        }
        if(d == 0){
            double t = (-b)/(2 * a);
            return new Hit(t,r,this);
        }
        if(d > 0) {
            double t1 = ((-b) + Math.sqrt(d)) / (2 * a);
            double t2 = ((-b) - Math.sqrt(d)) / (2 * a);
            if (t1 < t2) {
                return new Hit(t1, r, this);
            }
            return new Hit(t2, r, this);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Sphere sphere = (Sphere) o;

        if (Double.compare(sphere.radius, radius) != 0) return false;
        return c.equals(sphere.c);

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
