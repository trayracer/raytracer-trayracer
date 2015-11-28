package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;

/**
 * This class represents a cone aligned on the z-axis with the pointy piece on the point m with height h and cap cap.
 *
 * @author Oliver Kniejski
 */
public class ZAxisAlignedCone extends Geometry {
    /**
     * The pointy end of the cone.
     */
    public final Point3 m;
    /**
     * The distance to the base.
     */
    public final double height;
    /**
     * The distance to the cap.
     */
    public final double cap;
    /**
     * The scaling factor of the opening angle. 1 opens the cone 90 degree. A smaller value widens the angle.
     */
    public final double s;
    /**
     * Maximum value on the z-axis.
     */
    private double zmax;
    /**
     * Minimum value on the z-axis.
     */
    private double zmin;

    /**
     * This constructor creates a cone aligned on the z-axis with the pointy piece on the point m with height h and cap cap.
     *
     * @param m        The pointy end of the cone.
     * @param height   The distance to the base.
     * @param cap      The distance to the cap. If height is negative, cap must not be lower then or equal height.
     *                 If height is positive, cap musst not be larger then or equal height.
     * @param scale    The scaling factor of the opening angle. 1 opens the cone 90 degree. A smaller value widens the angle.
     * @param material The material.
     */
    public ZAxisAlignedCone(final Point3 m, final double height, final double cap, final double scale, final Material material) {
        super(material);
        if (m == null) throw new IllegalArgumentException("m must not be null.");
        if (height == 0) throw new IllegalArgumentException("Height must not be zero.");
        if ((height > 0 && height <= cap) || (height < 0 && height >= cap))
            throw new IllegalArgumentException("Value for cap is not allowed.");
        this.m = m;
        this.height = height;
        this.cap = cap;
        this.s = scale;
        this.zmax = Math.max(m.z + cap, m.z + height);
        this.zmin = Math.min(m.z + cap, m.z + height);
        //TODO: Optional covers for cap and bottom ?!
    }

    @Override
    public Hit hit(final Ray r) {
        if (r == null) throw new IllegalArgumentException("Ray must not be null.");
        double a = Math.pow(r.d.x, 2) + Math.pow(r.d.y, 2) - Math.pow(r.d.z, 2) / s;
        double b = 2 * (r.o.x - m.x) * r.d.x + 2 * (r.o.y - m.y) * r.d.y - 2 * (r.o.z - m.z) * r.d.z / s;
        double c = Math.pow((r.o.x - m.x), 2) + Math.pow((r.o.y - m.y), 2) - Math.pow((r.o.z - m.z), 2) / s;
        double d = Math.pow(b, 2) - 4 * (a * c);

        if (d == 0) {
            double t = (-b) / (2 * a);
            if (t > 0 && zmin <= r.at(t).z && r.at(t).z <= zmax) return new Hit(t, r, this, normalAt(r, t));
        }
        if (d > 0) {
            double t1 = ((-b) + Math.sqrt(d)) / (2 * a);
            double t2 = ((-b) - Math.sqrt(d)) / (2 * a);
            boolean b1 = t1 > 0 && zmin <= r.at(t1).z && r.at(t1).z <= zmax;
            boolean b2 = t2 > 0 && zmin <= r.at(t2).z && r.at(t2).z <= zmax;
            if ((b1 && !b2) || (b1 && b2 && t1 < t2)) return new Hit(t1, r, this, normalAt(r, t1));
            if ((!b1 && b2) || (b1 && b2 && t1 > t2)) return new Hit(t2, r, this, normalAt(r, t2));
        }
        return null;
    }

    /**
     * This method takes a ray and a double t and calculates the hitpoint and returns the normal of that point
     *
     * @param r the ray
     * @param t the double
     * @return the normal of the hitpoint.
     */
    public Normal3 normalAt(final Ray r, final double t) {
        if (r == null) throw new IllegalArgumentException("Ray must not be null");
        final Point3 p = r.at(t);
        final Point3 c = new Point3(m.x, m.y, p.y);
        final Vector3 cp = p.sub(c);
        return cp.normalized().add(new Vector3(0, 0, 1 / s)).asNormal();
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ZAxisAlignedCone that = (ZAxisAlignedCone) o;

        if (Double.compare(that.height, height) != 0) return false;
        if (Double.compare(that.cap, cap) != 0) return false;
        if (Double.compare(that.s, s) != 0) return false;
        if (Double.compare(that.zmax, zmax) != 0) return false;
        if (Double.compare(that.zmin, zmin) != 0) return false;
        return !(m != null ? !m.equals(that.m) : that.m != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (m != null ? m.hashCode() : 0);
        temp = Double.doubleToLongBits(height);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(cap);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(s);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(zmax);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(zmin);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ZAxisAlignedCone{" +
                "m=" + m +
                ", height=" + height +
                ", cap=" + cap +
                ", s=" + s +
                ", zmax=" + zmax +
                ", zmin=" + zmin +
                "} " + super.toString();
    }
}
