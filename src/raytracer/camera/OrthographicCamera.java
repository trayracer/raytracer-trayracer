package raytracer.camera;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;

/**
 * @author Oliver Kniejski
 */
public class OrthographicCamera extends Camera {
    public final double s;

    public OrthographicCamera(Point3 e, Vector3 g, Vector3 t, double s) {
        super(e, g, t);
        this.s = s;
    }

    @Override
    public Ray rayFor(int w, int h, int x, int y) {
        double a = w / h;
        Point3 o = this.e.add(this.u.mul(a * this.s * (x - ((w - 1) / 2)) / (w - 1))).add(this.v.mul(this.s * (y - ((h - 1) / 2)) / (h - 1)));
        Vector3 d = this.w.mul(-1);
        return new Ray(o, d);
    }

    @Override
    public String toString() {
        return "OrthographicCamera{" +
                "s=" + s +
                "} " + super.toString();
    }
}
