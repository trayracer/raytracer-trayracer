package raytracer.math;

/**
 * this class represents a three dimensional vector. Instances of this class are immutable.
 * Each method creates a new object containing the result.
 *
 * @author Steven Sobkowski
 */
public class Point3 {
    /**
     * x-value of the point
     */
    public final double x;
    /**
     * y-value of the point
     */
    public final double y;
    /**
     * z-value of the point
     */
    public final double z;

    /**
     *This constructor creates a new point.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    public Point3(final double x,final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * This method calculates the difference of this point and another point and returns the result as a vector.
     *
     * @param p the other point
     * @return the difference of the two points as a vector
     */
    public Vector3 sub(final Point3 p) {
        return new Vector3(x-p.x,y-p.y,z-p.z);
    }
    /**
     * This method calculates the difference of this point and a vector and returns the result as a point.
     *
     * @param v the vector
     * @return the difference as a point
     */
    public Point3 sub(final Vector3 v) {
        return new Point3(x-v.x,y-v.y,z-v.z);
    }
    /**
     * This method calculates the sum of this point and a vector and returns the result as a point.
     *
     * @param v the vector
     * @return the sum as a point
     */
    public Point3 add(final Vector3 v){
        return new Point3(x+v.x,y+v.y,z+v.z);
    }
    /**
     * This method returns a string representation of the point.
     *
     * @return The string representation of the point.
     */
    public String toString() {
        return "Point3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    /**
     * This method checks if the given object is a point and has the same properties as this one.
     *
     * @param o The object to check.
     * @return True if the given object is a point and has the same properties as this one.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point3 point3 = (Point3) o;

        if (Double.compare(point3.x, x) != 0) return false;
        if (Double.compare(point3.y, y) != 0) return false;
        return Double.compare(point3.z, z) == 0;
    }

    /**
     * This method returns a hash value for this point.
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
