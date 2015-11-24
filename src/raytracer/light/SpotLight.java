package raytracer.light;

import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * This class represents a spot light.
 *
 * @author Oliver Kniejski
 */
public class SpotLight extends Light {
    public final Point3 position;
    public final Vector3 direction;
    public final double halfAngle;

    /**
     * This constructor creates a spotlight on a given position in a given direction with a given color.
     * The opening of the spot light is set with the halfAngle.
     *
     * @param color The color of the light.
     * @param position The position.
     * @param direction The direction.
     * @param halfAngle half of the opening Angle.
     */
    public SpotLight(final Color color, final Point3 position, final Vector3 direction, final double halfAngle) {
        super(color);
        if (position == null || direction == null) throw new IllegalArgumentException("Parameters must not be null.");
        if (halfAngle <= 0 || halfAngle > Math.PI){
            throw new IllegalArgumentException("halfAngle must be in the range ]0,PI]");
        }
        this.position = position;
        this.direction = direction;
        this.halfAngle = halfAngle;
    }

    @Override
    public boolean illuminates(Point3 point) {
        return Math.acos(directionFrom(point).invert().dot(direction) / (directionFrom(point).invert().magnitude * direction.magnitude)) <= halfAngle;
    }

    @Override
    public Vector3 directionFrom(Point3 point) {
        return position.sub(point);
    }
}
