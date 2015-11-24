package raytracer.light;

import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * @author Steven Sobkowski
 */
public abstract class Light {
    final public Color color;

    public Light(Color color) {
        this.color = color;
    }

    public abstract boolean illuminates(Point3 point);

    public abstract Vector3 directionFrom(Point3 point);
}
