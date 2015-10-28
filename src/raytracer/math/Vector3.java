package raytracer.math;


/**
 * This class represents a three dimensional vector. Instances of this class are imutable.
 * Each method creates a new object containing the result.
 *
 * @author Oliver Kniejski
 */
public class Vector3 {
    /**
     * x-direction of the vector.
     */
    public final double x;
    /**
     * y-direction of the vector.
     */
    public final double y;
    /**
     * z-direction of the vector.
     */
    public final double z;
    /**
     * The magnitude of the vector.
     */
    public final double magnitude;

    /**
     * This constructor creates a new Vector with 3 components and computes its magnitude.
     *
     * @param x The x-direction.
     * @param y The y-direction.
     * @param z The z-direction.
     */
    public Vector3(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.magnitude = Math.sqrt((x * x) + (y * y) + (z * z));
    }

    /**
     * This method calculates the sum of this vector and another vector and returns the result as a vector.
     *
     * @param v The vector which is added.
     * @return The sum of the two vectors.
     */
    public Vector3 add(Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    /**
     * This method calculates the sum of this vector and a normal and returns the result as a vector.
     *
     * @param n The normal which is added.
     * @return The sum of vector and normal.
     */
    public Vector3 add(Normal3 n) {
        return new Vector3(x + n.x, y + n.y, z + n.z);
    }

    /**
     * This method calculates the difference of this vector and a normal and returns the result as a vector.
     *
     * @param n The normal which is subtracted.
     * @return The difference of vector and normal.
     */
    public Vector3 sub(Normal3 n) {
        return new Vector3(x - n.x, y - n.y, z - n.z);
    }

    /**
     * This method calculates the product of this vector and a scalar and returns the result as a vector.
     *
     * @param c The scalar.
     * @return The product of vector and scalar.
     */
    public Vector3 mul(double c) {
        return new Vector3(x * c, y * c, z * c);
    }

    /**
     * This method calculates the dot product of this vector and another vector and returns the result as a double.
     *
     * @param v The other vector.
     * @return The dot product of the two vectors.
     */
    public double dot(Vector3 v) {
        return (x * v.x) + (y * v.y) + (z * v.z);
    }

    /**
     * This method calculates the dot product of this vector and a normal and returns the result as a double.
     *
     * @param n The normal.
     * @return The dot product of vector and normal.
     */
    public double dot(Normal3 n) {
        return (x * n.x) + (y * n.y) + (z * n.z);
    }

    /**
     * This method creates a normalized vector that points in the same direction as the former vector.
     *
     * @return The normalized vector of this vector.
     */
    public Vector3 normalized() {
        return new Vector3(x / magnitude, y / magnitude, z / magnitude);
    }

    /**
     * This method creates a normalized vector that points in the same direction as the former vector and converts it into a normal.
     *
     * @return The normal of this vector.
     */
    public Normal3 asNormal() {
        Vector3 nv = this.normalized();
        return new Normal3(nv.x, nv.y, nv.z);
    }

    /**
     * This method reflects this vector on a normal. The normal and the resulting vector point away from the surface.
     * Mathematical formula: 2 * n * < n, v > − v
     *
     * @param n The normal.
     * @return The reflected vector of this vector.
     */
    public Vector3 reflectedOn(Normal3 n) {
        return this.invert().add(n.mul(this.dot(n) * 2));
    }

    /**
     * This method inverts this vector and returns it.
     *
     * @return The inverted vector.
     */
    private Vector3 invert() {
        return new Vector3(-x, -y, -z);
    }

    /**
     * This method calculates the cross product of this vector and another vector and returns it.
     *
     * @param v The other vector.
     * @return The cross product of the two vectors.
     */
    public Vector3 x(Vector3 v) {
        return new Vector3((y * v.z) - (z * v.y), (z * v.x) - (x * v.z), (y * v.z) - (z * v.y));
    }

    /**
     * This method checks if the given object is a vector and has the same properties as this one.
     *
     * @param o The object to check.
     * @return true if the given object is a vector and has the same properties as this one.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 vector3 = (Vector3) o;

        if (Double.compare(vector3.x, x) != 0) return false;
        if (Double.compare(vector3.y, y) != 0) return false;
        return Double.compare(vector3.z, z) == 0;

    }

    /**
     * This method returns a hash value for this vector.
     *
     * @return An int value hash code.
     */
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
