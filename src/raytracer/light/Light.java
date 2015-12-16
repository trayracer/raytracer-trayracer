package raytracer.light;

import raytracer.geometry.World;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * This abstract class represents the Light.
 *
 * @author Steven Sobkowski
 */
public abstract class Light {
    /**
     * The color of the light.
     */
    public final Color color;

    /**
     * A boolean which determines if the light casts a shadow.
     */
    public final boolean castsShadow;

    /**
     * This constructor creates a light with the given parameters.
     *
     * @param color       the given color
     * @param castsShadow the given boolean
     */
    public Light(final Color color, final boolean castsShadow) {
        if (color == null) throw new IllegalArgumentException("Parameters must not be null!");
        this.color = color;
        this.castsShadow = castsShadow;
    }

    /**
     * This method checks if a point is illuminated by the light.
     *
     * @param point the point, which has to be checked.
     * @param world the given world
     * @return returns true, if the point is illuminated by the light or false, if not.
     */
    public abstract boolean illuminates(final Point3 point, final World world);

    /**
     * This method returns a vector, which points to the direction, where the light is coming from with the given point as its origin.
     *
     * @param point The origin point, which has to be checked.
     * @return the vector, which points to the direction of the light.
     */
    public abstract Vector3 directionFrom(final Point3 point);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Light light = (Light) o;

        if (castsShadow != light.castsShadow) return false;
        return color.equals(light.color);

    }

    @Override
    public String toString() {
        return "Light{" +
                "color=" + color +
                ", castsShadow=" + castsShadow +
                '}';
    }

    @Override
    public int hashCode() {
        int result = color.hashCode();
        result = 31 * result + (castsShadow ? 1 : 0);
        return result;
    }


}
