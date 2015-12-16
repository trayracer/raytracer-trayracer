package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.*;
import raytracer.texture.TexCoord2;

/**
 * @author Oliver Kniejski
 *         <p>
 *         This class represents a z-axis aligned cylinder.
 */
public class ZAxisAlignedCylinder extends Geometry {
    /**
     * The basepoint in the center.
     */
    private final Point3 m;
    /**
     * The height.
     */
    private final double h;
    /**
     * The radius.
     */
    private final double radius;

    /**
     * This constructor creates a new Cylinder.
     *
     * @param m        The basepoint in the center.
     * @param h        The height. Must be larger than 0.
     * @param radius   The radius.
     * @param material The material.
     */
    public ZAxisAlignedCylinder(final Point3 m, final double h, final double radius, final Material material) {
        super(material);
        if (m == null) throw new IllegalArgumentException("Basepoint m must not be null.");
        if (h <= 0) throw new IllegalArgumentException("Height must be larger than 0.");
        this.m = m;
        this.h = h;
        this.radius = radius;
    }

    @Override
    public Hit hit(final Ray r) {
        if (r == null) throw new IllegalArgumentException("Ray must not be null.");
        double a = Math.pow(r.d.x, 2) + Math.pow(r.d.y, 2);
        double b = 2 * (r.o.x - m.x) * r.d.x + 2 * (r.o.y - m.y) * r.d.y;
        double c = Math.pow(r.o.x - m.x, 2) + Math.pow(r.o.y - m.y, 2) - Math.pow(radius, 2);
        double d = Math.pow(b, 2) - 4 * (a * c);

        if (d == 0) {
            double t = (-b) / (2 * a);
            if (t > Constants.EPSILON && m.z < r.at(t).z && r.at(t).z < (m.z + h))
                return new Hit(t, r, this, normalAt(r, t), calcTexCoord(r, t));
        }
        if (d > 0) {
            double t1 = ((-b) + Math.sqrt(d)) / (2 * a);
            double t2 = ((-b) - Math.sqrt(d)) / (2 * a);
            boolean b1 = t1 > Constants.EPSILON && m.z <= r.at(t1).z && r.at(t1).z <= (m.z + h);
            boolean b2 = t2 > Constants.EPSILON && m.z <= r.at(t2).z && r.at(t2).z <= (m.z + h);
            if ((b1 && !b2) || (b1 && b2 && t1 < t2)) return new Hit(t1, r, this, normalAt(r, t1), calcTexCoord(r, t1));
            if ((!b1 && b2) || (b1 && b2 && t1 > t2)) return new Hit(t2, r, this, normalAt(r, t2), calcTexCoord(r, t2));
        }
        return null;
    }

    /**
     * This method calculates the coordinates of the texture.
     *
     * @param ray the ray
     * @param t   the t
     * @return The TexCoord2
     */
    public TexCoord2 calcTexCoord(final Ray ray, final double t) {
        Point3 hitpoint = ray.at(t);
        double phi = Math.atan2((hitpoint.x - m.x), (hitpoint.y - m.y));

        double u = phi / (2 * Math.PI);
        double v = (hitpoint.z - m.z) / h;

        return new TexCoord2(u, v);
    }

    /**
     * This method takes a ray and a double t and calculates the hitpoint and returns the normal of that point.
     *
     * @param r The ray.
     * @param t The double.
     * @return The normal of the hitpoint.
     */
    public Normal3 normalAt(final Ray r, final double t) {
        if (r == null) throw new IllegalArgumentException("Ray must not be null.");
        final Point3 p = r.at(t);
        final Vector3 v = p.sub(new Point3(this.m.x, this.m.y, p.z));
        return v.asNormal();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ZAxisAlignedCylinder that = (ZAxisAlignedCylinder) o;

        return Double.compare(that.h, h) == 0 && Double.compare(that.radius, radius) == 0
                && !(m != null ? !m.equals(that.m) : that.m != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (m != null ? m.hashCode() : 0);
        temp = Double.doubleToLongBits(h);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(radius);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ZAxisAlignedCylinder{" +
                "m=" + m +
                ", h=" + h +
                ", radius=" + radius +
                "} " + super.toString();
    }
}
