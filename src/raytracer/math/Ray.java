package raytracer.math;

/**
 * Created by Steve-O on 10.11.2015.
 */
public class Ray {
   final Point3 o;
   final Vector3 d;

    public Ray(Point3 o, Vector3 d) {
        this.o = o;
        this.d = d;
    }

    public Point3 at(double t){

        return new Point3(t*o.x,t*o.y,t*o.z);
    }

    public double tOf(Point3 p){

        return new double((p.sub(o).magnitude/d.magnitude));
    }
}
