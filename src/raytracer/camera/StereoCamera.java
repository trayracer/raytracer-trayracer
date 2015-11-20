package raytracer.camera;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;

/**
 * @author Oliver Kniejski
 *
 * This class represents a stereoscopic perspective Camera.
 */
public class StereoCamera extends Camera{
    /**
     * The separation between the two eye points.
     */
    public final int cameraSepatation;
    /**
     * The angle for the cameras.
     */
    public final double angle;
    /**
     * The display mode for the stereo output. TRUE sets transverse viewing, FALSE sets parallel viewing.
     */
    public final boolean transverse;
    /**
     * The left camera.
     */
    private final Camera camL;
    /**
     * The right camera.
     */
    private final Camera camR;

    /**
     * This constructor calculates and creates the 2 shifted perspective cameras.
     * @param e The eye position.
     * @param g The gaze direction.
     * @param t The up-vector.
     * @param cameraSeparation The separation between the two eye points.
     * @param angle The angle for the two perspective cameras. Must be larger than zero.
     */
    public StereoCamera(final Point3 e, final Vector3 g, final Vector3 t, final double angle, final int cameraSeparation, final boolean transverse) {
        super(e, g, t);
        if (angle <= 0) throw new IllegalArgumentException("Angle must be larger than zero.");
        if (e == null || g == null || t == null) throw new NullPointerException("Parameters must not be null.");
        this.cameraSepatation = cameraSeparation;
        this.angle = angle;
        this.transverse= transverse;
        this.camL = new ShiftedPerspectiveCamera(e.add(u.normalized().mul(-cameraSeparation/2)),g,t,angle, -cameraSeparation/2);
        this.camR = new ShiftedPerspectiveCamera(e.add(u.normalized().mul(cameraSeparation/2)),g,t,angle, cameraSeparation/2);
    }

    /**
     * This method directs the call for rays to the corresponding cameras.
     * @param w The width of the image-plane in pixels.
     * @param h The height of the image-plane in pixels.
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @return The Ray for the given Parameters.
     */
    @Override
    public Ray rayFor(final int w, final int h, final int x, final int y) {
        if (x<w/2){
            if (transverse) return camR.rayFor(w/2,h,x,y);
            else return camL.rayFor(w/2,h,x,y);
        }
        else {
            if (transverse) return camL.rayFor(w/2,h,x-w/2,y);
            else return camR.rayFor(w/2,h,x-w/2,y);
        }
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        StereoCamera that = (StereoCamera) o;

        if (cameraSepatation != that.cameraSepatation) return false;
        if (Double.compare(that.angle, angle) != 0) return false;
        if (transverse != that.transverse) return false;
        if (camL != null ? !camL.equals(that.camL) : that.camL != null) return false;
        return !(camR != null ? !camR.equals(that.camR) : that.camR != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + cameraSepatation;
        temp = Double.doubleToLongBits(angle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (transverse ? 1 : 0);
        result = 31 * result + (camL != null ? camL.hashCode() : 0);
        result = 31 * result + (camR != null ? camR.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StereoCamera{" +
                "cameraSepatation=" + cameraSepatation +
                ", angle=" + angle +
                ", transverse=" + transverse +
                ", camL=" + camL +
                ", camR=" + camR +
                "} " + super.toString();
    }
}