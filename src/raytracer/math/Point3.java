package raytracer.math;

/**
 * Created by ok on 27.10.15.
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point3 point3 = (Point3) o;

        if (Double.compare(point3.x, x) != 0) return false;
        if (Double.compare(point3.y, y) != 0) return false;
        return Double.compare(point3.z, z) == 0;

    }

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

    public Vector3 sub(Point3 p) {
        return new Vector3(x-p.x,y-p.y,z-p.z);
    }
}
