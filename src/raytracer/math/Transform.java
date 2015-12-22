package raytracer.math;

/**
 * Created by Steve-O on 22.12.2015.
 */
public class Transform {


    final public Mat4x4 m;

    final public Mat4x4 i;

    public Transform() {
        m = new Mat4x4(1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1);
        i = m;
    }

    private Transform(final Mat4x4 m, final Mat4x4 i){
        this.m = m;
        this.i = i;
    }

    public Transform translation(final double x, final double y, final double z){
       Mat4x4 tm = new Mat4x4(1,0,0,x,0,1,0,y,0,0,1,z,0,0,0,1);
       Mat4x4 ti = new Mat4x4(1,0,0,-x,0,1,0,-y,0,0,1,-z,0,0,0,1);
        return new Transform(tm,ti);
    }

    public Transform scale(final double x, final double y, final double z){
       Mat4x4 sm = new Mat4x4(x,0,0,0,0,y,0,0,0,0,z,0,0,0,0,1);
       Mat4x4 si = new Mat4x4(1/x,0,0,0,0,1/y,0,0,0,0,1/z,0,0,0,0,1);
        return new Transform(sm,si);
    }

    public Transform rotateX(final double alpha){
        Mat4x4 rxm = new Mat4x4(Math.cos(alpha),-Math.sin(alpha),0,0,Math.sin(alpha),Math.cos(alpha),0,0,0,0,1,0,0,0,0,1);
        Mat4x4 rxi = new Mat4x4(Math.cos(alpha),Math.sin(alpha),0,0,-Math.sin(alpha),Math.cos(alpha),0,0,0,0,1,0,0,0,0,1);
        return new Transform(rxm,rxi);
    }

    public Transform rotateY(final double alpha){
        Mat4x4 rym = new Mat4x4(1,0,0,0,0,Math.cos(alpha),-Math.sin(alpha),0,0,Math.sin(alpha),Math.cos(alpha),0,0,0,0,1);
        Mat4x4 ryi = new Mat4x4(1,0,0,0,0,Math.cos(alpha),Math.sin(alpha),0,0,-Math.sin(alpha),Math.cos(alpha),0,0,0,0,1);
        return new Transform(rym,ryi);
    }

    public Transform rotateZ(final double alpha){
        Mat4x4 rzm = new Mat4x4(Math.cos(alpha),0,Math.sin(alpha),0,0,1,0,0,-Math.sin(alpha),0,Math.cos(alpha),0,0,0,0,1);
        Mat4x4 rzi = new Mat4x4(Math.cos(alpha),0,-Math.sin(alpha),0,0,1,0,0,Math.sin(alpha),0,Math.cos(alpha),0,0,0,0,1);
        return new Transform(rzm,rzi);
    }

    public Normal3 mul(final Normal3 n){
        return null;
    }

    public Ray mul(final Ray ray){
        return null;
    }



}
