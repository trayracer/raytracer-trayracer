package raytracer.light;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.math.Constants;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * This class represents a spot light.
 *
 * @author Oliver Kniejski
 */
public class SpotLight extends Light {
    /**
     * The Position of the light.
     */
    public final Point3 position;
    /**
     * The direction of the light.
     */
    public final Vector3 direction;
    /**
     * Half of the opening angel of the light.
     */
    public final double halfAngle;

    /**
     * This constructor creates a spotlight on a given position in a given direction with a given color.
     * The opening of the spot light is calculated from the halfAngle.
     *
     * @param color       The color of the light.
     * @param position    The position.
     * @param direction   The direction.
     * @param halfAngle   Half of the opening Angle.
     * @param castsShadow
     */
    public SpotLight(final Color color, final Point3 position, final Vector3 direction, final double halfAngle, final boolean castsShadow) {
        super(color, castsShadow);
        if (position == null || direction == null) throw new IllegalArgumentException("Parameters must not be null.");
        if (halfAngle <= 0 || halfAngle > Math.PI) {
            throw new IllegalArgumentException("halfAngle must be in the range ]0,PI]");
        }
        this.position = position;
        this.direction = direction;
        this.halfAngle = halfAngle;
    }

    @Override
    public boolean illuminates(final Point3 point, final World world) {
        if (point == null) throw new IllegalArgumentException("Point must not be null.");

        boolean inLightCone = directionFrom(point).invert().normalized().dot(direction.normalized()) >= Math.cos(halfAngle);
        if (castsShadow) {
            double tl = position.sub(point).magnitude / directionFrom(point).magnitude;

            Ray shadowray = new Ray(point, directionFrom(point));
            Hit hitpoint = world.hit(shadowray);
            if (hitpoint == null) return inLightCone;
            if (Constants.EPSILON < hitpoint.t && hitpoint.t < tl) return false;
            else return inLightCone;
        }
        return inLightCone;
    }

    @Override
    public Vector3 directionFrom(final Point3 point) {
        if (point == null) throw new IllegalArgumentException("Point must not be null.");
        return position.sub(point);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpotLight spotLight = (SpotLight) o;

        return Double.compare(spotLight.halfAngle, halfAngle) == 0 && !(position != null ? !position.equals(spotLight.position) : spotLight.position != null)
                && !(direction != null ? !direction.equals(spotLight.direction) : spotLight.direction != null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = position != null ? position.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        temp = Double.doubleToLongBits(halfAngle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "SpotLight{" +
                "position=" + position +
                ", direction=" + direction +
                ", halfAngle=" + halfAngle +
                "} " + super.toString();
    }
}
