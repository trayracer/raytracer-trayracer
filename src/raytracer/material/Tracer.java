package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * This tracer is a helper for the reflections.
 *
 * @author Steven Sobkowski & Marie Hennings & Oliver Kniejski
 */
public class Tracer {
    /**
     * The counter for the depth (steps) of the reflection.
     */
    private int counter;

    /**
     * This constructor sets the reflection depth.
     *
     * @param counter The reflection steps.
     */
    public Tracer(final int counter) {
        this.counter = counter;
    }

    /**
     * This method returns the color for the reflecting material recursively.
     *
     * @param origin    The current point on the reflecting surface.
     * @param direction The direction of the reflected ray.
     * @param world     The world.
     * @return Color for the reflection.
     */
    public Color tracing(final Point3 origin, final Vector3 direction, final World world) {
        if (origin == null || direction == null || world == null)
            throw new IllegalArgumentException("Parameters must not be null.");
        if (counter < 0) return world.backgroundColor;
        counter--;

        Hit hit = world.hit(new Ray(origin, direction.normalized()));

        if (hit == null) return world.backgroundColor;
        return hit.geo.material.colorFor(hit, world, this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tracer tracer = (Tracer) o;

        return counter == tracer.counter;
    }

    @Override
    public int hashCode() {
        return counter;
    }

    @Override
    public String toString() {
        return "Tracer{" +
                "counter=" + counter +
                '}';
    }
}
