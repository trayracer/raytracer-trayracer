package raytracer.camera;

import raytracer.math.Point2;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;
import raytracer.sampling.SamplingPattern;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class represents an orthographic Camera.
 *
 * @author Oliver Kniejski
 */
public class OrthographicCamera extends Camera {
    /**
     * The scaling factor for the image-plane.
     */
    public final double s;

    /**
     * This constructor creates the camera coordinate system via superclass and sets the scaling factor for the image-plane.
     * @param e The eye position.
     * @param g The gaze direction.
     * @param t The up-vector.
     * @param s The scaling factor for the image plane. Must be larger than zero.
     */
    public OrthographicCamera(final Point3 e, final Vector3 g, final Vector3 t, final double s, final SamplingPattern pattern) {
        super(e, g, t, pattern);
        if (s <= 0) throw new IllegalArgumentException("The scaling factor must be larger than zero.");
        this.s = s;
    }

    @Override
    public Set<Ray> rayFor(final int w, final int h, final int x, final int y) {
        double a = (double) w / (double) h;
        Set<Ray> raySet = new LinkedHashSet<>();
        for(Point2 point : pattern.points){
            Point3 o = this.e.add(this.u.mul(a * this.s * (x + point.x - ((w - 1) / 2)) / (w - 1))).add(this.v.mul(this.s * (y + point.y - ((h - 1) / 2)) / (h - 1)));
            Vector3 d = this.w.invert();
            raySet.add(new Ray(o, d));
        }
        return raySet;
    }

    @Override
    public boolean equals(final Object o) {
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