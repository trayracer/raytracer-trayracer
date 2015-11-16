package raytracer.geometry;

import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.texture.Color;

/**
 * @author Steven Sobkowski & Oliver Kniejski
 *
 *
 */
public class AxisAlignedBox extends Geometry{
    public final Point3 lbf;
    public final Point3 run;
    private Plane[] planes = new Plane[6];

    public AxisAlignedBox(Point3 lbf, Point3 run, Color color) {
        super(color);
        this.lbf = lbf;
        this.run = run;
        planes[0] = new Plane(lbf, new Normal3(0,0,-1), color);
        planes[1] = new Plane(run, new Normal3(0,0,1), color);
        planes[2] = new Plane(lbf, new Normal3(-1,0,0), color);
        planes[3] = new Plane(run, new Normal3(1,0,0), color);
        planes[4] = new Plane(lbf, new Normal3(0,-1,0), color);
        planes[5] = new Plane(run, new Normal3(0,1,0), color);
    }

    public Hit hit(Ray ray){
        for (Plane plane : planes){
            if (Math.acos(plane.n.dot(ray.d)/ray.d.magnitude) >= Math.PI/2){
                double t = plane.a.sub(ray.o).dot(plane.n) / ray.d.dot(plane.n);
                Point3 hitPoint =ray.at(t);
                double small = 0.0000000000001;
                if (lbf.x - small<= hitPoint.x && hitPoint.x <= run.x + small && lbf.y - small<= hitPoint.y && hitPoint.y <= run.y + small &&lbf.z - small <= hitPoint.z && hitPoint.z <= run.z + small){
                    return new Hit(t, ray, plane);
                }
            }
        }
        return null;
    }
}