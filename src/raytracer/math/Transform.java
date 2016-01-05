package raytracer.math;

/**
 * This class represents the transformation of an object.
 *
 * @author Steven Sobkowski & Marie Hennings on 22.12.2015.
 */
public class Transform {

    /**
     * The transformation-matrix.
     */
    final public Mat4x4 m;

    /**
     * The inverted Matrix
     */
    final public Mat4x4 i;

    /**
     * The public constructor. Initializes the transformation-matrix as the identity matrix
     */
    public Transform() {
        m = new Mat4x4(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
        i = new Mat4x4(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }

    /**
     * The private constructor
     *
     * @param m the transformation-matrix
     * @param i the inverted-matrix
     */
    private Transform(final Mat4x4 m, final Mat4x4 i) {
        this.m = m;
        this.i = i;
    }

    /**
     * This method translates an object and returns a new transform-object
     *
     * @param x the x-value of an object
     * @param y the y-value of an object
     * @param z the z-value of an object
     * @return the corresponding transform-object
     */
    public Transform translation(final double x, final double y, final double z) {
        Mat4x4 tm = new Mat4x4(1, 0, 0, x, 0, 1, 0, y, 0, 0, 1, z, 0, 0, 0, 1);
        Mat4x4 ti = new Mat4x4(1, 0, 0, -x, 0, 1, 0, -y, 0, 0, 1, -z, 0, 0, 0, 1);
        return new Transform(m.mul(tm), ti.mul(i));
    }

    /**
     * This method scales an object and returns a new transform-object
     *
     * @param x the x-value of an object
     * @param y the y-value of an object
     * @param z the z-value of an object
     * @return the corresponding transform-object
     */
    public Transform scale(final double x, final double y, final double z) {
        Mat4x4 sm = new Mat4x4(x, 0, 0, 0, 0, y, 0, 0, 0, 0, z, 0, 0, 0, 0, 1);
        Mat4x4 si = new Mat4x4(1 / x, 0, 0, 0, 0, 1 / y, 0, 0, 0, 0, 1 / z, 0, 0, 0, 0, 1);
        return new Transform(m.mul(sm), si.mul(i));
    }

    /**
     * This method rotates an object around the x-axis and returns a new transform-object
     *
     * @param alpha the value needed for the angle
     * @return the corresponding transform-object
     */
    public Transform rotateX(final double alpha) {
        Mat4x4 rxm = new Mat4x4(1, 0, 0, 0, 0, Math.cos(alpha), -Math.sin(alpha), 0, 0, Math.sin(alpha), Math.cos(alpha), 0, 0, 0, 0, 1);
        Mat4x4 rxi = new Mat4x4(1, 0, 0, 0, 0, Math.cos(alpha), Math.sin(alpha), 0, 0, -Math.sin(alpha), Math.cos(alpha), 0, 0, 0, 0, 1);
        return new Transform(m.mul(rxm), rxi.mul(i));
    }

    /**
     * This method rotates an object around the y-axis and returns a new transform-object
     *
     * @param alpha the value needed for the angle
     * @return the corresponding transform-object
     */
    public Transform rotateY(final double alpha) {
        Mat4x4 rym = new Mat4x4(Math.cos(alpha), 0, Math.sin(alpha), 0, 0, 1, 0, 0, -Math.sin(alpha), 0, Math.cos(alpha), 0, 0, 0, 0, 1);
        Mat4x4 ryi = new Mat4x4(Math.cos(alpha), 0, -Math.sin(alpha), 0, 0, 1, 0, 0, Math.sin(alpha), 0, Math.cos(alpha), 0, 0, 0, 0, 1);
        return new Transform(m.mul(rym), ryi.mul(i));
    }

    /**
     * This method rotates an object around the z-axis and returns a new transform-object
     *
     * @param alpha the value needed for the angle
     * @return the corresponding transform-object
     */
    public Transform rotateZ(final double alpha) {
        Mat4x4 rzm = new Mat4x4(Math.cos(alpha), -Math.sin(alpha), 0, 0, Math.sin(alpha), Math.cos(alpha), 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
        Mat4x4 rzi = new Mat4x4(Math.cos(alpha), Math.sin(alpha), 0, 0, -Math.sin(alpha), Math.cos(alpha), 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
        return new Transform(m.mul(rzm), rzi.mul(i));
    }

    /**
     * This method takes the normal of the object and returns the new transformed normal
     *
     * @param n the normal
     * @return the transformed normal
     */
    public Normal3 mul(final Normal3 n) {
        Vector3 v = i.transposed().mul(new Vector3(n.x, n.y, n.z));
        return v.asNormal();
    }

    /**
     * This method takes the ray of the object and returns the new transformed ray.
     *
     * @param ray the ray
     * @return the transformed ray
     */
    public Ray mul(final Ray ray) {

        return new Ray(i.mul(ray.o), i.mul(ray.d));
    }

    @Override
    public String toString() {
        return "Transform{" +
                "m=" + m +
                ", i=" + i +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transform transform = (Transform) o;

        if (!m.equals(transform.m)) return false;
        return i.equals(transform.i);

    }

    @Override
    public int hashCode() {
        int result = m.hashCode();
        result = 31 * result + i.hashCode();
        return result;
    }
}
