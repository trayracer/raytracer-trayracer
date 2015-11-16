package raytracer.geometry;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * Created by ok on 12.11.15.
 *
 *  Zylinder ohne Deckel
 */
public class ZAxisAlignedCylinder extends Geometry{
    private final Point3 m;
    private final double h;
    private final double radius;


    public ZAxisAlignedCylinder(Point3 m, double h, double radius, Color color) {
        super(color);
        this.m = m;
        this.h = h;
        this.radius = radius;
    }

    @Override
    public Hit hit(Ray r ) {
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
            return new Hit(t,r,this);
        }
        if(d > 0) {
            double t1 = ((-b) + Math.sqrt(d)) / (2 * a);
            double t2 = ((-b) - Math.sqrt(d)) / (2 * a);
            if (zMin<=r.at(t1).z && r.at(t1).z<=zMax && zMin<=r.at(t2).z && r.at(t2).z<=zMax) {
                if (t1 < t2) {
                    return new Hit(t1, r, this);
                }
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
}
