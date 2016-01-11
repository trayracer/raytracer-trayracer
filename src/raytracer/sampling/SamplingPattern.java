package raytracer.sampling;

import raytracer.math.Point2;
import raytracer.math.Point3;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Oliver Kniejski & Steven Sobkowski
 */
public class SamplingPattern {
    /**
     *
     */
    private List<Point2> points = new LinkedList<>();

    public boolean isRandom = false;

    public int numberOfPoints;

    public SamplingPattern() {
        points.add(new Point2(0, 0));
    }

    private SamplingPattern(List<Point2> points) {
        if (points == null || points.size() == 0) throw new IllegalArgumentException("Points must be valid");
        this.points = points;
    }

    public SamplingPattern regularPattern(final int x, final int y) {
        if (x < 1 || y < 1) throw new IllegalArgumentException("Number of Points must be positive.");
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
        return new SamplingPattern(newPoints);
    }

    public SamplingPattern randomPattern(final int i) {
        isRandom = true;
        numberOfPoints = i;
        return this;
    }

    public List<Point2> getPoints() {
        if (!isRandom) return points;
        return randomPoints();
    }

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

        return !(points != null ? !points.equals(that.points) : that.points != null);
    }

    @Override
    public int hashCode() {
        return points != null ? points.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SamplingPattern{" + points.size() +
                " points=" + points +
                '}';
    }
}
