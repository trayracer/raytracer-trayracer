package raytracer.camera;

import raytracer.math.Point2;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;
import raytracer.sampling.SamplingPattern;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class represents a perspective camera.
 *
 * @author Steven Sobkowski
 */
public class PerspectiveCamera extends Camera {

    /**
     * The angle of the camera.
     */
    public final double angle;

    /**
     * This constructor creates a perspective camera with the given parameters.
     *
     * @param e       point for the position
     * @param g       for the gaze direction
     * @param t       for the up-vector
     * @param angle   for the angle. must be larger than zero.
     * @param pattern the SamplingPattern.
     */
    public PerspectiveCamera(final Point3 e, final Vector3 g, final Vector3 t, final double angle, final SamplingPattern pattern) {
        super(e, g, t, pattern);
        if (angle <= 0) throw new IllegalArgumentException("Angle must be greater than zero.");
        this.angle = angle;
    }

    /**
     * @param width  of the picture.
     * @param height of the picture.
     * @param x      x-position of a pixel.
     * @param y      y-position of a pixel.
     * @return a Ray for the pixel.
     */
    @Override
    public Set<Ray> rayFor(final int width, final int height, final int x, final int y) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width or height must be greater than zero.");
        }
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Parameters must greater than zero and smaller than width or height respectively");
        }

        Set<Ray> raySet = new LinkedHashSet<>();
        for(Point2 point : pattern.points) {
            Vector3 r = this.w.invert().mul((height / 2) / Math.tan(angle / 2)).add(u.mul(x + point.x - ((width - 1) / 2))).add(v.mul(y + point.y - ((height - 1) / 2)));
            Vector3 d = r.mul(1 / r.magnitude);
            raySet.add(new Ray(e, d));
        }

        return raySet;
    }

    @Override
    public boolean equals(final Object o) {
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
