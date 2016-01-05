package raytracer.camera;

import raytracer.math.Point2;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;
import raytracer.sampling.SamplingPattern;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Oliver Kniejski
 *         <p>
 *         This class represents a perspective camera with a view plane shifted on the x axis of the camera. Made for use with stereoscopic cameras.
 */
public class ShiftedPerspectiveCamera extends PerspectiveCamera {
    /**
     * The nuber of pixels to shift the center horizontal. Positive values shift the center to the right.
     */
    public final int shift;

    /**
     * This constructor creates a shifted perspective camera.
     *
     * @param e       The eye position.
     * @param g       The gaze direction.
     * @param t       The up-vector.
     * @param angle   The angle for the cameras.
     * @param shift   The nuber of pixels to shift the center horizontal. Positive values shift the center to the right.
     * @param pattern The SamplingPattern.
     */
    public ShiftedPerspectiveCamera(final Point3 e, final Vector3 g, final Vector3 t, final double angle, final int shift, final SamplingPattern pattern) {
        super(e, g, t, angle, pattern);
        this.shift = shift;
    }

    @Override
    public Set<Ray> rayFor(final int width, final int height, final int x, final int y) {

        Set<Ray> raySet = new LinkedHashSet<>();
        for(Point2 point : pattern.points) {
            Vector3 r = this.w.invert().mul((height / 2) / Math.tan(angle / 2)).add(u.mul(x - ((width - 1) / 2) - (shift * 10))).add(v.mul(y - ((height - 1) / 2)));
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

        ShiftedPerspectiveCamera that = (ShiftedPerspectiveCamera) o;

        return shift == that.shift;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + shift;
        return result;
    }

    @Override
    public String toString() {
        return "ShiftedPerspectiveCamera{" +
                "shift=" + shift +
                "} " + super.toString();
    }
}
