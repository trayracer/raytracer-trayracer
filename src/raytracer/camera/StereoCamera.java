package raytracer.camera;

import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;

/**
 * Created by ok on 16.11.15.
 */
public class StereoCamera extends Camera{
    public final int cameraSepatation;
    public final double planeDistance;
    public final double angle;
    private Camera camL;
    private Camera camR;


    public StereoCamera(Point3 e, Vector3 g, Vector3 t, int cameraSepatation, double planeDistance, double angle) {
        super(e, g, t);
        this.cameraSepatation = cameraSepatation;
        this.planeDistance = planeDistance;
        this.angle = angle;
        this.camL = new ShiftedPerspectiveCamera(e.add(u.normalized().mul(-cameraSepatation/2)),g,t,angle, -cameraSepatation/4);
        this.camR = new ShiftedPerspectiveCamera(e.add(u.normalized().mul(cameraSepatation/2)),g,t,angle, cameraSepatation/4);
    }

    @Override
    public Ray rayFor(int width, int height, int x, int y) {
        if (x<width/2){
            return camR.rayFor(width/2,height,x,y);
        }
        else {
            return camL.rayFor(width/2,height,x-width/2,y);
        }

    }
}
