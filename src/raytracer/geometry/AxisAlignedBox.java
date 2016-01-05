package raytracer.geometry;

import raytracer.material.Material;
import raytracer.material.NoMaterial;
import raytracer.math.*;
import raytracer.texture.TexCoord2;
import raytracer.texture.TextureUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a Box that is aligned by the axes.
 *
 * @author Steven Sobkowski & Oliver Kniejski & Marie Hennings
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
    private final Geometry[] planes = new Geometry[6];
    /**
     * The scalar for the texture of the planes describing the Box.
     */
    public final int textureScalar;

    /**
     * This constructor creates the 6 Planes describing the Box out of the 2 Points with a default texture scalar.
     * Each value of the Point lbf has to be smaller than the corresponding value of the Point run.
     *
     * @param material The material of the Box.
     */
    public AxisAlignedBox(final Material material) {

        this(new Point3(-0.5, -0.5, -0.5), new Point3(0.5, 0.5, 0.5), material, 1);
    }

    /**
     * This constructor creates the 6 Planes describing the Box out of the 2 Points with a default texture scalar.
     * Each value of the Point lbf has to be smaller than the corresponding value of the Point run.
     *
     * @param lbf      The left-bottom-far Point of the Box.
     * @param run      The right-up-near Point of the Box.
     * @param material The material of the Box.
     */
    public AxisAlignedBox(final Point3 lbf, final Point3 run, final Material material) {
        this(lbf, run, material, 1);
    }

    /**
     * This constructor creates the 6 Planes describing the Box out of the 2 Points.
     * Each value of the Point lbf has to be smaller than the corresponding value of the Point run.
     *
     * @param lbf           The left-bottom-far Point of the Box.
     * @param run           The right-up-near Point of the Box.
     * @param material      The material of the Box.
     * @param textureScalar The scalar of the texture.
     */
    public AxisAlignedBox(final Point3 lbf, final Point3 run, final Material material, final int textureScalar) {
        super(material);
        if (lbf == null || run == null) throw new IllegalArgumentException("Parameters must not be null.");
        if (lbf.x >= run.x || lbf.y >= run.y || lbf.z >= run.z)
            throw new IllegalArgumentException("Each value of the Point lbf has to be smaller than the corresponding value of the Point run.");
        this.lbf = lbf;
        this.run = run;
        this.textureScalar = textureScalar;

        List<Geometry> onePlane = new LinkedList<Geometry>(Arrays.asList(new Plane(material, textureScalar)));

        planes[0] = new Node(new Transform().translation(lbf.x, lbf.y, lbf.z).rotateX(-Math.PI/2), onePlane, new NoMaterial()); //bottom
        planes[1] = new Node(new Transform().translation(run.x, run.y, run.z).rotateX(Math.PI/2), onePlane, new NoMaterial()); //top
        planes[2] = new Node(new Transform().translation(lbf.x, lbf.y, lbf.z).rotateZ(Math.PI/2), onePlane, new NoMaterial()); //left
        planes[3] = new Node(new Transform().translation(run.x, run.y, run.z).rotateZ(-Math.PI/2), onePlane, new NoMaterial()); //right
        planes[4] = new Node(new Transform().translation(lbf.x, lbf.y, lbf.z).rotateX(Math.PI), onePlane, new NoMaterial()); //front
        planes[5] = new Node(new Transform().translation(run.x, run.y, run.z), onePlane, new NoMaterial()); //back
    }

    @Override
    public Hit hit(final Ray ray) {
        if (ray == null) throw new IllegalArgumentException("Ray must not be null.");
        for (Geometry plane : planes) {
            Hit hit = plane.hit(ray);
            if (hit != null && hit.normal.dot(ray.d) / ray.d.magnitude <= Math.cos(Math.PI / 2)) {
                Point3 hitPoint = ray.at(hit.t);
                if (hit.t > Constants.EPSILON && lbf.x - Constants.EPSILON <= hitPoint.x && hitPoint.x <= run.x + Constants.EPSILON && lbf.y - Constants.EPSILON <= hitPoint.y && hitPoint.y <= run.y + Constants.EPSILON && lbf.z - Constants.EPSILON <= hitPoint.z && hitPoint.z <= run.z + Constants.EPSILON) {
                    return hit;
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