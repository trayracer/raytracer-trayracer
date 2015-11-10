package raytracer.camera;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;

/**
 * This class represents
 *
 * @author Oliver Kniejski
 */
public class OrthographicCamera extends Camera {
    public final double s;

    public OrthographicCamera(Point3 e, Vector3 g, Vector3 t, double s) {
        super(e, g, t);
        this.s = s;
    }

    @Override
    public Ray rayFor(int width, int height, int x, int y) {
        double a = (double) width / (double) height;
        Point3 o = this.e.add(this.u.mul(a * this.s * (x - ((width - 1) / 2)) / (width - 1))).add(this.v.mul(this.s * (y - ((height - 1) / 2)) / (height - 1)));
        Vector3 d = this.w.mul(-1);
        return new Ray(o, d);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrthographicCamera that = (OrthographicCamera) o;
        return Double.compare(that.s, s) == 0 && super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = e != null ? e.hashCode() : 0;
        long temp = Double.doubleToLongBits(s);
        result = 31 * result + (g != null ? g.hashCode() : 0);
        result = 31 * result + (t != null ? t.hashCode() : 0);
        result = 31 * result + (u != null ? u.hashCode() : 0);
        result = 31 * result + (v != null ? v.hashCode() : 0);
        result = 31 * result + (w != null ? w.hashCode() : 0);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "e=" + e +
                ", g=" + g +
                ", t=" + t +
                ", u=" + u +
                ", v=" + v +
                ", w=" + w +
                ", s=" + s +
                '}';
    }


}
