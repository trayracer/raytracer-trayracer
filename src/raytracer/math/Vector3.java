package raytracer.math;


/**
 * Created by ok on 27.10.15.
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
        this.magnitude = Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * This
     * @param v
     * @return
     */
    public Vector3 add(Vector3 v){

        return ;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 vector3 = (Vector3) o;

        if (Double.compare(vector3.x, x) != 0) return false;
        if (Double.compare(vector3.y, y) != 0) return false;
        return Double.compare(vector3.z, z) == 0;

    }

    /**
     *
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
