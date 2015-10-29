package raytracer.math;

/**
 * This class represents a matrix with the dimensions 3 * 3. Instances of this class are imutable.
 * Each method creates a new object containing the result.
 *
 * @author Oliver Kniejski
 */
public final class Mat3x3 {
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
     * This is the determinant of the matrix.
     */
    public final double determinant;

    /**
     * The constructor creates a new Matrix with 9 components and computes its determinant.
     *
     * @param m11 Element in first row, first column
     * @param m12 Element in first row, second column
     * @param m13 Element in first row, third column
     * @param m21 Element in second row, first column
     * @param m22 Element in second row, second column
     * @param m23 Element in second row, third column
     * @param m31 Element in third row, first column
     * @param m32 Element in third row, second column
     * @param m33 Element in third row, third column
     */
    public Mat3x3(final double m11, final double m12, final double m13, final double m21, final double m22, final double m23, final double m31, final double m32, final double m33) {
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

    /**
     * This method multiplicates this matrix with the given matrix and returns the resulting matrix.
     *
     * @param m The matrix to multiplicate with.
     * @return The resulting matrix of the matrix multiplication.
     */
    public Mat3x3 mul( final Mat3x3 m ) {
        return new Mat3x3(m11*m.m11 + m12*m.m21 + m13*m.m31, m11*m.m12 + m12*m.m22 + m13*m.m32, m11*m.m13 + m12*m.m23 + m13*m.m33,
                          m21*m.m11 + m22*m.m21 + m23*m.m31, m21*m.m12 + m22*m.m22 + m23*m.m32, m21*m.m13 + m22*m.m23 + m23*m.m33,
                          m31*m.m11 + m32*m.m21 + m33*m.m31, m31*m.m12 + m32*m.m22 + m33*m.m32, m31*m.m13 + m32*m.m23 + m33*m.m33);
    }

    /**
     * This method multiplicates this matrix with the given vector and returns the resulting vector.
     *
     * @param v The vector to multiply with.
     * @return The resulting vector of the matrix multiplication.
     */
    public Vector3 mul( final Vector3 v ) {
        return new Vector3(m11*v.x + m12*v.y + m13*v.z, m21*v.x + m22*v.y + m23*v.z, m31*v.x + m32*v.y + m33*v.z);
    }

    /**
     * This method multiplicates this matrix with the given point and returns the resulting point.
     *
     * @param p The point to multiply with.
     * @return The resulting point of the matrix multiplication.
     */
    public Point3 mul( final Point3 p) {
        return new Point3(m11*p.x + m12*p.y + m13*p.z, m21*p.x + m22*p.y + m23*p.z, m31*p.x + m32*p.y + m33*p.z);
    }

    /**
     * This method exchanges the first column of this matrix with the given vector.
     *
     * @param v The vector to insert.
     * @return The matrix with new column one.
     */
    public Mat3x3 changeCol1( final Vector3 v ) {
        return new Mat3x3(v.x, m12, m13,
                          v.y, m22, m23,
                          v.z, m32, m33);
    }

    /**
     * This method exchanges the second column of this matrix with the given vector.
     *
     * @param v The vector to insert.
     * @return The matrix with new column two.
     */
    public Mat3x3 changeCol2( final Vector3 v ) {
        return new Mat3x3(m11, v.x, m13,
                          m21, v.y, m23,
                          m31, v.z, m33);
    }

    /**
     * This method exchanges the third column of this matrix with the given vector.
     *
     * @param v The vector to insert.
     * @return The matrix with new column three.
     */
    public Mat3x3 changeCol3( final Vector3 v ) {
        return new Mat3x3(m11, m12, v.x,
                          m21, m22, v.y,
                          m31, m32, v.z);
    }

    /**
     * This method returns a string representation of this matrix.
     *
     * @return The string representation.
     */
    public String toString() {
        return "Mat3x3{" +
                "m11=" + m11 +
                ", m12=" + m12 +
                ", m13=" + m13 +
                ", m21=" + m21 +
                ", m22=" + m22 +
                ", m23=" + m23 +
                ", m31=" + m31 +
                ", m32=" + m32 +
                ", m33=" + m33 +
                ", determinant=" + determinant +
                '}';
    }

    /**
     * This method checks if the given object is a Mat3x3 and has the same properties as this one.
     *
     * @param o The object to check.
     * @return true if the given object is a Mat3x3 and has the same properties as this one.
     */
    public boolean equals( final Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mat3x3 mat3x3 = (Mat3x3) o;

        if (Double.compare(mat3x3.m11, m11) != 0) return false;
        if (Double.compare(mat3x3.m12, m12) != 0) return false;
        if (Double.compare(mat3x3.m13, m13) != 0) return false;
        if (Double.compare(mat3x3.m21, m21) != 0) return false;
        if (Double.compare(mat3x3.m22, m22) != 0) return false;
        if (Double.compare(mat3x3.m23, m23) != 0) return false;
        if (Double.compare(mat3x3.m31, m31) != 0) return false;
        if (Double.compare(mat3x3.m32, m32) != 0) return false;
        if (Double.compare(mat3x3.m33, m33) != 0) return false;
        return Double.compare(mat3x3.determinant, determinant) == 0;

    }

    /**
     * This method returns a hash value for this matrix.
     *
     * @return An int value hash code.
     */
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(m11);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m12);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m13);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m21);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m22);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m23);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m31);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m32);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m33);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(determinant);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
