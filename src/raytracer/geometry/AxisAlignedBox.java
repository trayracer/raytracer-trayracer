package raytracer.geometry;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.texture.Color;

/**
 * Created by ok on 08.11.15.
 */
public class AxisAlignedBox extends Geometry{
    public final Point3 lbf;
    public final Point3 run;

    public AxisAlignedBox(Point3 lbf, Point3 run, Color color) {
        super(color);
        this.lbf = lbf;
        this.run = run;
    }

    public Hit hit(Ray ray){
        return null;
    }

}
