package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;

/**
 * @author Oliver Kniejski
 *
 *  This class represents a cylinder.
 */
public class ZAxisAlignedCylinder extends Geometry{
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
     * This constructor creates a new Cylinder
     * @param m The basepoint in the center.
     * @param h The height. Must be larger than 0.
     * @param radius The radius.
     * @param material The material.
     */
    public ZAxisAlignedCylinder(final Point3 m, final double h, final double radius, final Material material) {
        super(material);
        if (m == null || material == null) throw new IllegalArgumentException("Parameters must not be null.");
        if (h <= 0) throw new IllegalArgumentException("Height must be larger than 0.");
        this.m = m;
        this.h = h;
        this.radius = radius;
    }

    //TODO: Optional covers for top and bottom ?!
    @Override
    public Hit hit(final Ray r ) {
        if (r == null) throw new IllegalArgumentException("Ray must not be null.");
        double a = Math.pow(r.d.x,2) + Math.pow(r.d.y,2);
        double b = 2 * (r.o.x - m.x) * r.d.x + 2 * (r.o.y - m.y) * r.d.y;
        double c = Math.pow(r.o.x - m.x, 2) + Math.pow(r.o.y - m.y, 2) - Math.pow(radius,2);
        double d = Math.pow(b,2) - 4*(a*c);

        if(d < 0) {
            return null;
        }
        if(d == 0 && m.z<r.at((-b)/(2 * a)).z && r.at((-b)/(2 * a)).z<(m.z + h)){
            double t = (-b)/(2 * a);
            if (t > 0) return new Hit(t,r,this, normalAt(r, t));
        }
        if(d > 0) {
            double t1 = ((-b) + Math.sqrt(d)) / (2 * a);
            double t2 = ((-b) - Math.sqrt(d)) / (2 * a);


            if (m.z<=r.at(t1).z && r.at(t1).z<=(m.z + h) && m.z<=r.at(t2).z && r.at(t2).z<=(m.z + h)) {
                if (t1 < t2 && t1 > 0) return new Hit(t1, r, this, normalAt(r, t1));
                else if (t1 < t2 && t1 <= 0){
                    if (t2 > 0) return new Hit(t2, r, this, normalAt(r, t2));
                    else return null;
                }
                else if (t2 < t1 && t2 > 0) return new Hit(t2, r, this, normalAt(r, t2));
                else if (t2 < t1 && t2 <= 0){
                    if (t1 > 0) return new Hit(t1, r, this, normalAt(r, t1));
                    else return null;
                }
            }
            if (t2>0 && (m.z>r.at(t1).z || r.at(t1).z>(m.z + h) ) && m.z<=r.at(t2).z && r.at(t2).z<=(m.z + h)) {
                return new Hit(t2, r, this, normalAt(r, t2));
            }
            if (t1>0 && m.z<=r.at(t1).z && r.at(t1).z<=(m.z + h) && (m.z>r.at(t2).z || r.at(t2).z>(m.z + h))) {
                return new Hit(t1, r, this, normalAt(r, t1));
            }
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
    public Normal3 normalAt(final Ray r, final double t){
        final Point3 hitpoint = r.at(t);
        final Vector3 hitvector = hitpoint.sub(new Point3(this.m.x, this.m.y, hitpoint.z));
        return hitvector.asNormal();
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ZAxisAlignedCylinder that = (ZAxisAlignedCylinder) o;

        if (Double.compare(that.h, h) != 0) return false;
        if (Double.compare(that.radius, radius) != 0) return false;
        return !(m != null ? !m.equals(that.m) : that.m != null);
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
