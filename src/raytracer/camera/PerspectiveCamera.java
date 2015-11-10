package raytracer.camera;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;

/**
 * Created by Steven Sobkowski on 08.11.15.
 */
public class PerspectiveCamera extends Camera {

    public final double angle;

    public PerspectiveCamera(Point3 e, Vector3 g, Vector3 t, double angle) {
        super(e,g,t);
        this.angle = angle;

    }


    @Override
    public Ray rayFor(int width, int height, int x, int y) {
        Point3 o = e;
        Vector3 r = this.w.invert().mul((height/2)/Math.tan(angle)).add(u.mul(x-((width-1)/2))).add(v.mul(y-((height-1)/2)));
        Vector3 d = r.mul(1/r.magnitude);
        return new Ray(o,d);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PerspectiveCamera that = (PerspectiveCamera) o;

        return Double.compare(that.angle, angle) == 0;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(angle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PerspectiveCamera{" +
                "angle=" + angle +
                "e=" + e +
                ", g=" + g +
                ", t=" + t +
                ", u=" + u +
                ", v=" + v +
                ", w=" + w +
                '}';
    }
}
