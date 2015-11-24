package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.Normal3;
import raytracer.math.Solvers;
import raytracer.math.Ray;
import raytracer.texture.Color;

/**
 * @author Oliver Kniejski
 *
 * This class represents a Torus around the z-axis on the 0-Point.
 */
public class Torus extends Geometry {
    /**
     * The radius.
     */
    final public double radius;
    /**
     * The diameter.
     */
    final public double diameter;

    /**
     * This constructor creates a new Torus.
     * @param radius The radius.
     * @param diameter The diameter.
     * @param material The material.
     */
    public Torus(final double radius, final double diameter, final Material material) {
        super(material);
        this.radius = radius;
        this.diameter = diameter;
    }

    @Override
    public Hit hit(Ray r) {
        double a = 4*Math.pow(radius,2)*(Math.pow(r.d.x,2)+Math.pow(r.d.y,2));
        double b = 8*Math.pow(radius,2)*(r.o.x*r.d.x+r.o.y*r.d.y);
        double c = 4*Math.pow(radius,2)*(Math.pow(r.o.x,2)+Math.pow(r.o.y,2));
        double d = Math.pow(r.d.magnitude,2);
        double e = 2*(r.o.x*r.d.x+r.o.y*r.d.y+r.o.z*r.d.z);
        double f = Math.pow(r.o.x,2)+Math.pow(r.o.y,2)+Math.pow(r.o.z,2)+Math.pow(radius,2)-Math.pow(diameter,2);
        double[] roots = Solvers.solveQuartic(Math.pow(d,2), 2*d*e, 2*d*f+Math.pow(e,2)-a, 2*e*f-b, Math.pow(f,2)-c);

        double t = 0;

        if (roots != null) {
            for (int x = 0; x < roots.length; x++) {
                if (roots[x] > 0 && roots[x]< t) t = roots[x];
            }
            return new Hit(t, r, this, new Normal3(1,0,0)); //TODO: normal
        }
        return null;
    }


}
