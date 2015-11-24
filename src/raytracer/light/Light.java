package raytracer.light;

import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * This abstract class represents the Light
 *
 * @author Steven Sobkowski
 */
public abstract class Light {

    final public Color color;

    public Light(final Color color) {
        this.color = color;
    }

    public abstract boolean illuminates(final Point3 point);

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
