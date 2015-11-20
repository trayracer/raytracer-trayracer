package raytracer.camera;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;

/**
 * @author Oliver Kniejski
 *
 * This abstract class represents a Camera.
 */
public abstract class Camera {
    /**
     * The eye position.
     */
    public final Point3 e;
    /**
     * The gaze direction.
     */
    public final Vector3 g;
    /**
     * The up-vector.
     */
    public final Vector3 t;
    /**
     * Vector u for the camera coordinate system.
     */
    public final Vector3 u;
    /**
     * Vector v for the camera coordinate system.
     */
    public final Vector3 v;
    /**
     * Vector w for the camera coordinate system.
     */
    public final Vector3 w;

    /**
     * This constructor computes the vectors for the camera coordinate system and creates a new camera.
     * @param e The eye position.
     * @param g The gaze direction.
     * @param t The up-vector.
     */
    public Camera(final Point3 e, final Vector3 g, final Vector3 t) {
        this.e = e;
        this.g = g;
        this.t = t;
        this.w = g.mul( -1 / g.magnitude );
        this.u = t.x( w ).mul( 1 / t.x(w).magnitude );
        this.v = w.x(u);
    }

    /**
     * This method calculates a Ray for this camera with the given dimensions of the image-plane and the given pixel.
     * @param w The width of the image-plane in pixels.
     * @param h The height of the image-plane in pixels.
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @return The Ray for the given Parameters.
     */
    public abstract Ray rayFor(final int w, final int h, final int x, final int y);

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Camera camera = (Camera) o;

        if (e != null ? !e.equals(camera.e) : camera.e != null) return false;
        if (g != null ? !g.equals(camera.g) : camera.g != null) return false;
        if (t != null ? !t.equals(camera.t) : camera.t != null) return false;
        if (u != null ? !u.equals(camera.u) : camera.u != null) return false;
        if (v != null ? !v.equals(camera.v) : camera.v != null) return false;
        return !(w != null ? !w.equals(camera.w) : camera.w != null);
    }

    @Override
    public int hashCode() {
        int result = e != null ? e.hashCode() : 0;
        result = 31 * result + (g != null ? g.hashCode() : 0);
        result = 31 * result + (t != null ? t.hashCode() : 0);
        result = 31 * result + (u != null ? u.hashCode() : 0);
        result = 31 * result + (v != null ? v.hashCode() : 0);
        result = 31 * result + (w != null ? w.hashCode() : 0);
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
                '}';
    }
}