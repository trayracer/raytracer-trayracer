package raytracer.math;

/**
 * Created by Steve-O on 10.11.2015.
 */
public class Ray {
   public final Point3 o;
   public final Vector3 d;

    public Ray(final Point3 o, final Vector3 d) {
        this.o = o;
        this.d = d;
    }

    public final Point3 at(final double t){
        Point3 at = o.add(d.mul(t));
        return at;
    }

    public final double tOf(final Point3 p){
        final double t = (o.sub(p).magnitude / d.magnitude);
        return t;
    }

    @Override
    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (o1 == null || getClass() != o1.getClass()) return false;

        Ray ray = (Ray) o1;

        if (!o.equals(ray.o)) return false;
        return d.equals(ray.d);

    }

    @Override
    public int hashCode() {
        int result = o.hashCode();
        result = 31 * result + d.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "o=" + o +
                ", d=" + d +
                '}';
    }
}
