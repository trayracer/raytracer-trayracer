package raytracer.camera;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;

/**
 * Created by ok on 16.11.15.
 */
public class ShiftedPerspectiveCamera extends PerspectiveCamera {
    public final int shift;


    public ShiftedPerspectiveCamera(Point3 e, Vector3 g, Vector3 t, double angle, int shift) {
        super(e, g, t, angle);
        this.shift = shift;
    }
    @Override
    public Ray rayFor(int width, int height, int x, int y) {
        Point3 o = e;
        Vector3 r = this.w.invert().mul((height/2)/Math.tan(angle/2)).add(u.mul(x-((width-1)/2)-shift)).add(v.mul(y-((height-1)/2)));
        Vector3 d = r.mul(1/r.magnitude);
        return new Ray(o,d);
    }
}
