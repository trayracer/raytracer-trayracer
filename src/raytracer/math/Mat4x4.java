package raytracer.math;

/**
 * This class represents a matrix with the dimensions 4 * 4. Instances of this class are imutable.
 * Each method creates a new object containing the result.
 *
 * @author Marie Hennings
 */
public class Mat4x4 {
    /**
     * This is the element in the first row and the first column.
     */
    public final double m11;
    /**
     * This is the element in the first row and the second column.
     */
    public final double m12;
    /**
     * This is the element in the first row and the third column.
     */
    public final double m13;
    /**
     * This is the element in the first row and the fourth column.
     */
    public final double m14;
    /**
     * This is the element in the second row and the first column.
     */
    public final double m21;
    /**
     * This is the element in the second row and the second column.
     */
    public final double m22;
    /**
     * This is the element in the second row and the third column.
     */
    public final double m23;
    /**
     * This is the element in the second row and the fourth column.
     */
    public final double m24;
    /**
     * This is the element in the third row and the first column.
     */
    public final double m31;
    /**
     * This is the element in the third row and the second column.
     */
    public final double m32;
    /**
     * This is the element in the third row and the third column.
     */
    public final double m33;
    /**
     * This is the element in the third row and the fourth column.
     */
    public final double m34;
    /**
     * This is the element in the fourth row and the first column.
     */
    public final double m41;
    /**
     * This is the element in the fourth row and the second column.
     */
    public final double m42;
    /**
     * This is the element in the fourth row and the third column.
     */
    public final double m43;
    /**
     * This is the element in the fourth row and the fourth column.
     */
    public final double m44;

    /**
     * The constructor creates a new Matrix with 16 components.
     *
     * @param m11 Element in first row, first column
     * @param m12 Element in first row, second column
     * @param m13 Element in first row, third column
     * @param m14 Element in first row, fourth column
     * @param m21 Element in second row, first column
     * @param m22 Element in second row, second column
     * @param m23 Element in second row, third column
     * @param m24 Element in second row, fourth column
     * @param m31 Element in third row, first column
     * @param m32 Element in third row, second column
     * @param m33 Element in third row, third column
     * @param m34 Element in third row, fourth column
     * @param m41 Element in fourth row, first column
     * @param m42 Element in fourth row, second column
     * @param m43 Element in fourth row, third column
     * @param m44 Element in fourth row, fourth column
     */
    public Mat4x4(final double m11, final double m12, final double m13, final double m14, final double m21, final double m22, final double m23, final double m24, final double m31, final double m32, final double m33, final double m34, final double m41, final double m42, final double m43, final double m44) {
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m14 = m14;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m24 = m24;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
        this.m34 = m34;
        this.m41 = m41;
        this.m42 = m42;
        this.m43 = m43;
        this.m44 = m44;
    }

    /**
     * This method multiplicates this matrix with the given matrix and returns the resulting matrix.
     *
     * @param m The matrix to multiplicate with.
     * @return The resulting matrix of the matrix multiplication.
     */
    public Mat4x4 mul(final Mat4x4 m) {
        if (m == null) {
            throw new IllegalArgumentException("Matrix m must not be null.");
        }
        return new Mat4x4(m11 * m.m11 + m12 * m.m21 + m13 * m.m31 + m14 * m.m41, m11 * m.m12 + m12 * m.m22 + m13 * m.m32 + m14 * m.m42, m11 * m.m13 + m12 * m.m23 + m13 * m.m33 + m14 * m.m43, m11 * m.m14 + m12 * m.m24 + m13 * m.m34 + m14 * m.m44,
                m21 * m.m11 + m22 * m.m21 + m23 * m.m31 + m24 * m.m41, m21 * m.m12 + m22 * m.m22 + m23 * m.m32 + m24 * m.m42, m21 * m.m13 + m22 * m.m23 + m23 * m.m33 + m24 * m.m43, m21 * m.m14 + m22 * m.m24 + m23 * m.m34 + m24 * m.m44,
                m31 * m.m11 + m32 * m.m21 + m33 * m.m31 + m34 * m.m41, m31 * m.m12 + m32 * m.m22 + m33 * m.m32 + m34 * m.m42, m31 * m.m13 + m32 * m.m23 + m33 * m.m33 + m34 * m.m43, m31 * m.m14 + m32 * m.m24 + m33 * m.m34 + m34 * m.m44,
                m41 * m.m11 + m42 * m.m21 + m43 * m.m31 + m44 * m.m41, m41 * m.m12 + m42 * m.m22 + m43 * m.m32 + m44 * m.m42, m41 * m.m13 + m42 * m.m23 + m43 * m.m33 + m44 * m.m43, m41 * m.m14 + m42 * m.m24 + m43 * m.m34 + m44 * m.m44);
    }

    /**
     * This method multiplicates this matrix with the given vector and returns the resulting vector.
     *
     * @param v The vector to multiply with.
     * @return The resulting vector of the matrix multiplication.
     */
    public Vector3 mul(final Vector3 v) {
        if (v == null) {
            throw new IllegalArgumentException("Vector v must not be null.");
        }
        return new Vector3(m11 * v.x + m12 * v.y + m13 * v.z,
                m21 * v.x + m22 * v.y + m23 * v.z,
                m31 * v.x + m32 * v.y + m33 * v.z);
    }

    /**
     * This method multiplicates this matrix with the given point and returns the resulting point.
     *
     * @param p The point to multiply with.
     * @return The resulting point of the matrix multiplication.
     */
    public Point3 mul(final Point3 p) {
        if (p == null) {
            throw new IllegalArgumentException("Point p must not be null.");
        }
        return new Point3(m11 * p.x + m12 * p.y + m13 * p.z + m14,
                m21 * p.x + m22 * p.y + m23 * p.z + m24,
                m31 * p.x + m32 * p.y + m33 * p.z + m34);
    }

    /**
     * This method transposed this matrix.
     *
     * @return The transposed matrix.
     */
    public Mat4x4 transposed() {
        return new Mat4x4(m11, m21, m31, m41, m12, m22, m32, m42, m13, m23, m33, m43, m14, m24, m34, m44);
    }
}
