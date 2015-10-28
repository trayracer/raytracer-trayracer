package raytracer.math;

/**
 * Created by ok on 27.10.15.
 *
 * @author Oliver Kniejski
 */
public class Mat3x3 {
    public final double m11;
    public final double m12;
    public final double m13;
    public final double m21;
    public final double m22;
    public final double m23;
    public final double m31;
    public final double m32;
    public final double m33;
    public final double determinant;

    public Mat3x3(double m11, double m12, double m13, double m21, double m22, double m23, double m31, double m32, double m33) {
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
        this.determinant = (m11 * m22 * m33) + (m12 * m23 * m31) + (m13 * m21 * m32) - (m13 * m22 * m31) - (m12 * m21 * m33) - (m11 * m23 * m32);
    }

    public Mat3x3 mul( Mat3x3 m ) {
        return new Mat3x3(m11*m.m11 + m12*m.m21 + m13*m.m31, )
    }

    public Vector3 mul(Vector3 v){

    }

    public Point3 mul(Point3 p){

    }

    public Mat3x3 changeCol1( Vector3 v ) {

    }
}
