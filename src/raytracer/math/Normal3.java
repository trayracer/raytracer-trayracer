package raytracer.math;

/**
 * Created by ok on 27.10.15.
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
     * This method multiplies the normal with a given double.
     *
     * @param n
     * @return
     */
    public Normal3 mul(final double n) {
        return new Normal3(this.x * n, this.y * n, this.z * n);
    }

    /**
     * @param o
     * @return
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Normal3 normal3 = (Normal3) o;

        if (Double.compare(normal3.x, x) != 0) return false;
        if (Double.compare(normal3.y, y) != 0) return false;
        return Double.compare(normal3.z, z) == 0;

    }

    /**
     * @return
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
