package raytracer.math;

/**
 * This class represents a three dimensional normal. Instances of this class are imutable.
 * Each method creates a new object containing the result.
 *
 * @author Marie Hennings
 */
public class Normal3 {

    /**
     * x-direction of normal.
     */
    public final double x;

    /**
     * y-direction of normal.
     */
    public final double y;

    /**
     * z-direction of normal.
     */
    public final double z;

    /**
     * This constructor creates a new normal.
     *
     * @param x The x-direction.
     * @param y The y-direction.
     * @param z The z-direction.
     */
    public Normal3(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * This method calculates the product of this normal and a scalar and returns the result as a normal.
     *
     * @param n The scalar.
     * @return The product of vector and scalar.
     */
    public Normal3 mul(final double n) {
        return new Normal3(x * n, y * n, z * n);
    }

    /**
     * This method calculates the sum of this normal and a normal and returns the result as a normal.
     *
     * @param n The normal which is added.
     * @return The sum of the two normals.
     */
    public Normal3 add(final Normal3 n) {
        return new Normal3(x + n.x, y + n.y, z + n.z);
    }

    /**
     * This method calculates the dot product of this normal and a vector and returns the result as a double.
     *
     * @param v The vector.
     * @return The dot product of normal and vector.
     */
    public double dot(final Vector3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * This method returns a string representation of this normal.
     *
     * @return The string representation of this normal.
     */
    public String toString() {
        return "Normal3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    /**
     * This method checks if the given object is a normal and has the same properties as this one.
     *
     * @param o The object to check.
     * @return True if the given object is a normal and has the same properties as this one.
     */
    @SuppressWarnings("SimplifiableIfStatement")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Normal3 normal3 = (Normal3) o;

        if (Double.compare(normal3.x, x) != 0) return false;
        if (Double.compare(normal3.y, y) != 0) return false;
        return Double.compare(normal3.z, z) == 0;

    }

    /**
     * This method returns a hash value for this normal.
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
