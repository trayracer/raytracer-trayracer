package raytracer.math;

/**
 * This class represents a ray.
 *
 * @author Steven Sobkowski
 */
public class Ray {
    /**
     * The origin point of the ray.
     */
    public final Point3 o;

    /**
     * the vector of the ray.
     */
    public final Vector3 d;

    /**
     * This constructor creates a ray with the given point and vector.
     *
     * @param o the point
     * @param d the vector
     */
    public Ray(final Point3 o, final Vector3 d) {
        this.o = o;
        this.d = d;
    }

    /**
     * This method takes the factor t and calculates it's point on the ray.
     *
     * @param t the factor
     * @return the point on the ray
     */
    public final Point3 at(final double t) {
        return o.add(d.mul(t));
    }

    /**
     * This method takes a point and calculates its factor.
     *
     * @param p the point on the ray
     * @return the factor to reach the point on the ray
     */
    public final double tOf(final Point3 p) {
        return o.sub(p).magnitude / d.magnitude;
    }

    @Override
    public boolean equals(final Object o1) {
        if (this == o1) return true;
        if (o1 == null || getClass() != o1.getClass()) return false;

        Ray ray = (Ray) o1;

        return o.equals(ray.o) && d.equals(ray.d);

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
