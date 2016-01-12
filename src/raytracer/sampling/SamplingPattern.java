package raytracer.sampling;

import raytracer.math.Point2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a sampling pattern for anti-aliasing and depth of field camera effect.
 *
 * @author Oliver Kniejski & Steven Sobkowski
 */
public class SamplingPattern {
    /**
     * The list of point2's for non-random sampling patterns.
     */
    private final List<Point2> points;
    /**
     * Sets the randomization of Points on every getPoints().
     */
    public final boolean isRandom;
    /**
     * The number of Points in this sampling pattern.
     */
    public final int numberOfPoints;

    /**
     * This standard constructor creates a sampling pattern with a single point in the center.
     */
    public SamplingPattern() {
        points = new LinkedList<>();
        points.add(new Point2(0, 0));
        isRandom = false;
        this.numberOfPoints = 1;
    }

    /**
     * This constructor creates a sampling pattern with a List of given, non-random points.
     *
     * @param points The points. Should be in the range of -0.5 to 0.5.
     */
    private SamplingPattern(final List<Point2> points) {
        if (points == null || points.size() == 0) throw new IllegalArgumentException("Points must be valid");
        this.points = points;
        isRandom = false;
        this.numberOfPoints = points.size();
    }

    /**
     * This constructor is intended for random sampling patterns with a fixed number of points.
     *
     * @param random         Sets the randomization of Points on every getPoints().
     * @param numberOfPoints The number of points.
     */
    private SamplingPattern(final boolean random, final int numberOfPoints) {
        if (numberOfPoints < 2) throw new IllegalArgumentException("numberOfPoints must be larger than one.");
        this.points = new LinkedList<>();
        this.isRandom = random;
        this.numberOfPoints = numberOfPoints;
    }

    /**
     * This method creates a new regular sampling pattern.
     *
     * @param x The columns of points.
     * @param y The rows of points.
     * @return The regular samplingPattern.
     */
    public SamplingPattern regularPattern(final int x, final int y) {
        if (x < 1 || y < 1) throw new IllegalArgumentException("Number of Points must be positive.");
        return new SamplingPattern(regularPoints(x, y));
    }

    /**
     * This method creates a new random sampling pattern.
     *
     * @param i The number of points.
     * @return The random samplingPattern.
     */
    public SamplingPattern randomPattern(final int i) {
        return new SamplingPattern(true, i);
    }

    /**
     * This method returns a new circular sampling pattern.
     *
     * @param i The number of points.
     * @return The circular samplingPattern.
     */
    public SamplingPattern regularDisc(final int i) {
        int x = (int) Math.floor(Math.sqrt(i));
        int y = (int) Math.ceil(Math.sqrt(i));
        List<Point2> initialPoints = regularPoints(x, y);
        List<Point2> discPoints = new LinkedList<>();
        for (Point2 p : initialPoints) {
            discPoints.add(new Point2(p.x * 2 * Math.sqrt(1 - Math.pow(p.y * 2, 2) / 2) / 2, p.y * 2 * Math.sqrt(1 - Math.pow(p.x * 2, 2) / 2) / 2));
        }
        return new SamplingPattern(discPoints);
    }

    /**
     * The getter for the Point list.
     *
     * @return The point list.
     */
    public List<Point2> getPoints() {
        if (!isRandom) return points;
        return randomPoints();
    }

    /**
     * This method creates the regular point list.
     *
     * @param x Number of columns.
     * @param y Number of rows.
     * @return The point list.
     */
    private List<Point2> regularPoints(final int x, final int y) {
        double distX;
        double distY;

        if (x > 1) distX = 1.0 / x;
        else distX = 1;

        if (y > 1) distY = 1.0 / y;
        else distY = 1;

        double posX = distX / 2.0 - 0.5;
        double posY = distY / 2.0 - 0.5;

        List<Point2> newPoints = new LinkedList<>();

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                newPoints.add(new Point2(posX, posY));
                posX += distX;
            }
            posX = distX / 2.0 - 0.5;
            posY += distY;
        }
        return newPoints;
    }

    /**
     * This method creates random points for the getter of randomPattern.
     *
     * @return
     */
    private List<Point2> randomPoints() {
        double distance = 1.0 / numberOfPoints;
        LinkedList<Double> xInit = new LinkedList<>();
        LinkedList<Double> yInit = new LinkedList<>();

        double pos = distance / 2.0 - 0.5;

        for (int i = 0; i < numberOfPoints; i++) {
            xInit.add(pos);
            yInit.add(pos);
            pos += distance;
        }

        //shuffle columns
        Collections.shuffle(xInit);

        List<Point2> randomPoints = new LinkedList<>();

        for (int i = 0; i < numberOfPoints; i++) {
            double randomX = Math.random() * distance - distance / 2.0;
            double randomY = Math.random() * distance - distance / 2.0;
            randomPoints.add(new Point2(xInit.get(i) + randomX, yInit.get(i) + randomY));
        }
        return randomPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SamplingPattern that = (SamplingPattern) o;
        return isRandom == that.isRandom && numberOfPoints == that.numberOfPoints
                && !(points != null ? !points.equals(that.points) : that.points != null);
    }

    @Override
    public int hashCode() {
        int result = points != null ? points.hashCode() : 0;
        result = 31 * result + (isRandom ? 1 : 0);
        result = 31 * result + numberOfPoints;
        return result;
    }

    @Override
    public String toString() {
        return "SamplingPattern{" +
                "points=" + points +
                ", isRandom=" + isRandom +
                ", numberOfPoints=" + numberOfPoints +
                '}';
    }
}
