package raytracer.sampling;

import raytracer.math.Point2;

import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 * @author Oliver Kniejski & Steven Sobkowski
 */
public class SamplingPattern {
    /**
     *
     */
    public List<Point2> points = new LinkedList<>();

    public SamplingPattern(){
        points.add(new Point2(0, 0));
    }

    private SamplingPattern(List<Point2> points){
        if (points.size() == 0 || points == null) throw new IllegalArgumentException("Points must be valid");
        this.points = points;
    }

    public SamplingPattern regularPattern(final int x, final int y){
        if (x < 1 || y < 1) throw new IllegalArgumentException("Number of Points must be positive.");
        double distX;
        double distY;

        if (x > 1) distX = 1.0 / x;
        else distX = 1;

        if (y > 1) distY = 1.0 / y;
        else distY = 1;

        double posX = -distX / 2.0;
        double posY = -distY / 2.0;

        List<Point2> newPoints = new LinkedList<>();

        for (int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                newPoints.add(new Point2(posX, posY));
                posX += distX;
            }
            posX = -distX / 2.0;
            posY += distY;
        }


        return new SamplingPattern(newPoints);
    }

//    public SamplingPattern randomPattern(final int x, final int y){
//
//        return null;
//    }


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
