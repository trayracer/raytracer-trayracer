package raytracer.light;

import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * This class represents a directional light.
 *
 * @author Marie Hennings
 */
public class DirectionalLight extends Light {
    /**
     * The direction of the light.
     */
    public final Vector3 direction;

    /**
     * The constructor.
     *
     * @param color     The color of the light.
     * @param direction The direction of the light.
     */
    public DirectionalLight(final Color color, final Vector3 direction) {
        super(color);
        if (color == null || direction == null) throw new IllegalArgumentException("Parameters must not be null.");
        this.direction = direction;
    }

    @Override
    public boolean illuminates(final Point3 point) {
        if (point == null) throw new IllegalArgumentException("Point must not ne null.");
        return true;
    }

    @Override
    public Vector3 directionFrom(final Point3 point) {
        if (point == null) throw new IllegalArgumentException("Point must not be null.");
        return direction.invert();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirectionalLight that = (DirectionalLight) o;

        return direction.equals(that.direction);

    }

    @Override
    public int hashCode() {
        return direction.hashCode();
    }

    @Override
    public String toString() {
        return "DirectionalLight{" +
                "direction=" + direction +
                '}';
    }
}
