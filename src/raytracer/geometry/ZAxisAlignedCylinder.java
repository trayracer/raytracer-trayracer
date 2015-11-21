package raytracer.geometry;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.texture.Color;

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
     * @param h The height.
     * @param radius The radius.
     * @param color The color.
     */
    public ZAxisAlignedCylinder(final Point3 m, final double h, final double radius, final Color color) {
        super(color);
        if (m == null) throw new IllegalArgumentException("Basepoint must not be null.");
        this.m = m;
        this.h = h;
        this.radius = radius;
    }

    //TODO: Covers for top and bottom
    @Override
    public Hit hit(final Ray r ) {
        if (r == null) throw new IllegalArgumentException("Ray must not be null.");
        double a = Math.pow(r.d.x,2) + Math.pow(r.d.y,2);
        double b = 2 * (r.o.x - m.x) * r.d.x + 2 * (r.o.y - m.y) * r.d.y;
        double c = Math.pow(r.o.x - m.x, 2) + Math.pow(r.o.y - m.y, 2) - Math.pow(radius,2);
        double d = Math.pow(b,2) - 4*(a*c);

        double zMin = Math.min(m.z, m.z + h);
        double zMax = Math.max(m.z, m.z + h);

        if(d < 0) {
            return null;
        }
        if(d == 0 && zMin<r.at((-b)/(2 * a)).z && r.at((-b)/(2 * a)).z<zMax){
            double t = (-b)/(2 * a);
            if (t > 0) return new Hit(t,r,this);
        }
        if(d > 0) {
            //TODO: t has to be positive
            double t1 = ((-b) + Math.sqrt(d)) / (2 * a);
            double t2 = ((-b) - Math.sqrt(d)) / (2 * a);
            if (zMin<=r.at(t1).z && r.at(t1).z<=zMax && zMin<=r.at(t2).z && r.at(t2).z<=zMax) {
                if (t1 < t2) return new Hit(t1, r, this);
                else return new Hit(t2, r, this);
            }
            if ((zMin>r.at(t1).z ||r.at(t1).z>zMax ) && zMin<=r.at(t2).z && r.at(t2).z<=zMax) {
                return new Hit(t2, r, this);
            }
            if (zMin<=r.at(t1).z && r.at(t1).z<=zMax && ( zMin>r.at(t2).z || r.at(t2).z>zMax)) {
                return new Hit(t1, r, this);
            }
        }
        return null;
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
