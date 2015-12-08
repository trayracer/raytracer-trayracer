package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.Constants;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Ray;

import java.util.Arrays;

/**
 * This class represents a Box that is aligned by the axes.
 *
 * @author Steven Sobkowski & Oliver Kniejski
 */
public class AxisAlignedBox extends Geometry {
    /**
     * The left-bottom-far Point of the Box.
     */
    public final Point3 lbf;
    /**
     * The right-up-near Point of the Box.
     */
    public final Point3 run;
    /**
     * Array for the 6 Planes describing the Box.
     */
    private final Plane[] planes = new Plane[6];

    /**
     * This constructor creates the 6 Planes describing the Box out of the 2 Points.
     * Each value of the Point lbf has to be smaller than the corresponding value of the Point run.
     *
     * @param lbf      The left-bottom-far Point of the Box.
     * @param run      The right-up-near Point of the Box.
     * @param material The material of the Box.
     */
    public AxisAlignedBox(final Point3 lbf, final Point3 run, final Material material) {
        super(material);
        if (lbf == null || run == null) throw new IllegalArgumentException("Parameters must not be null.");
        if (lbf.x >= run.x || lbf.y >= run.y || lbf.z >= run.z)
            throw new IllegalArgumentException("Each value of the Point lbf has to be smaller than the corresponding value of the Point run.");
        this.lbf = lbf;
        this.run = run;
        planes[0] = new Plane(lbf, new Normal3(0, 0, -1), material);
        planes[1] = new Plane(run, new Normal3(0, 0, 1), material);
        planes[2] = new Plane(lbf, new Normal3(-1, 0, 0), material);
        planes[3] = new Plane(run, new Normal3(1, 0, 0), material);
        planes[4] = new Plane(lbf, new Normal3(0, -1, 0), material);
        planes[5] = new Plane(run, new Normal3(0, 1, 0), material);
    }

    @Override
    public Hit hit(final Ray ray) {
        if (ray == null) throw new IllegalArgumentException("Ray must not be null.");
        for (Plane plane : planes) {
            if (Math.acos(plane.n.dot(ray.d) / ray.d.magnitude) >= Math.PI / 2) {
                double t = plane.a.sub(ray.o).dot(plane.n) / ray.d.dot(plane.n);
                Point3 hitPoint = ray.at(t);
                if (t > Constants.EPSILON && lbf.x - Constants.EPSILON <= hitPoint.x && hitPoint.x <= run.x + Constants.EPSILON && lbf.y - Constants.EPSILON <= hitPoint.y && hitPoint.y <= run.y + Constants.EPSILON && lbf.z - Constants.EPSILON <= hitPoint.z && hitPoint.z <= run.z + Constants.EPSILON) {
                    return new Hit(t, ray, plane, plane.n);
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AxisAlignedBox that = (AxisAlignedBox) o;

        return !(lbf != null ? !lbf.equals(that.lbf) : that.lbf != null)
                && !(run != null ? !run.equals(that.run) : that.run != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (lbf != null ? lbf.hashCode() : 0);
        result = 31 * result + (run != null ? run.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AxisAlignedBox{" +
                "lbf=" + lbf +
                ", run=" + run +
                ", planes=" + Arrays.toString(planes) +
                "} " + super.toString();
    }
}