package raytracer.math;

/**
 * This class represents a two dimensional Point.
 *
 * @author Oliver Kniejski
 */
public class Point2 {
    /**
     * x-value of the point
     */
    public final double x;
    /**
     * y-value of the point
     */
    public final double y;

    /**
     *This constructor creates a new point.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Point2(final double x,final double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point2 point2 = (Point2) o;

        if (Double.compare(point2.x, x) != 0) return false;
        return Double.compare(point2.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Point2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
