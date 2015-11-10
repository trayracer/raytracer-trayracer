package raytracer.camera;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;

/**
 * @author Oliver Kniejski
 */
public abstract class Camera {
    public final Point3 e;
    public final Vector3 g;
    public final Vector3 t;
    public final Vector3 u;
    public final Vector3 v;
    public final Vector3 w;

    public Camera(Point3 e, Vector3 g, Vector3 t) {
        this.e = e;
        this.g = g;
        this.t = t;
        this.w = g.mul( -1 / g.magnitude );
        this.u = t.x( w ).mul( 1 / t.x(w).magnitude );
        this.v = w.x(u);
    }

    public abstract Ray rayFor(int w, int h, int x, int y);

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
