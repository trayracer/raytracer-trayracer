package raytracer.geometry;

import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.texture.Color;

import java.util.Arrays;

/**
 * @author Steven Sobkowski & Oliver Kniejski
 *
 * This class represents a Box that is aligned by the axes.
 */
public class AxisAlignedBox extends Geometry{
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
     * This constructor creates the 6 Planes describing the Box out of the 2 Points. Each value of the Point lbf has to be smaller than the corresponding value of the Point run.
     * @param lbf The left-bottom-far Point of the Box.
     * @param run The right-up-near Point of the Box.
     * @param color The color of the Box.
     */
    public AxisAlignedBox(final Point3 lbf, final Point3 run, final Color color) {
        super(color);
        if (lbf.x >= run.x || lbf.y >= run.y || lbf.z >= run.z) throw new IllegalArgumentException( "Each value of the Point lbf has to be smaller than the corresponding value of the Point run." );
        this.lbf = lbf;
        this.run = run;
        planes[0] = new Plane(lbf, new Normal3(0,0,-1), color);
        planes[1] = new Plane(run, new Normal3(0,0,1), color);
        planes[2] = new Plane(lbf, new Normal3(-1,0,0), color);
        planes[3] = new Plane(run, new Normal3(1,0,0), color);
        planes[4] = new Plane(lbf, new Normal3(0,-1,0), color);
        planes[5] = new Plane(run, new Normal3(0,1,0), color);
    }

    /**
     * This method returns a Hit for a Ray and this Box.
     * @param ray The ray.
     * @return Hit for ray and box or null if there is no intersection.
     */
    public Hit hit(final Ray ray){
        for (Plane plane : planes){
            if (Math.acos(plane.n.dot(ray.d)/ray.d.magnitude) >= Math.PI/2){
                double t = plane.a.sub(ray.o).dot(plane.n) / ray.d.dot(plane.n);
                Point3 hitPoint =ray.at(t);
                double small = 0.0000000000001; // smoothen the bounds for rounding errors
                if (lbf.x - small<= hitPoint.x && hitPoint.x <= run.x + small && lbf.y - small<= hitPoint.y && hitPoint.y <= run.y + small &&lbf.z - small <= hitPoint.z && hitPoint.z <= run.z + small){
                    return new Hit(t, ray, plane);
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

        if (lbf != null ? !lbf.equals(that.lbf) : that.lbf != null) return false;
        if (run != null ? !run.equals(that.run) : that.run != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(planes, that.planes);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (lbf != null ? lbf.hashCode() : 0);
        result = 31 * result + (run != null ? run.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(planes);
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