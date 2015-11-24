package raytracer.light;

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
    final public Color color;

    /**
     * This constructor creates a light with the given parameters.
     *
     * @param color the given color
     */
    public Light(final Color color) {
        this.color = color;
    }

    /**
     * This method checks if a point is illuminated by the light.
     *
     * @param point the point, which has to be checked.
     * @return returns true, if the point is illuminated by the light or false, if not.
     */
    public abstract boolean illuminates(final Point3 point);

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

        return color.equals(light.color);

    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

    @Override
    public String toString() {
        return "Light{" +
                "color=" + color +
                '}';
    }
}
