package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.light.Light;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;

import java.util.List;

/**
 * This class represents a a perfectly reflective material.
 *
 * @author Steven Sobkowski & Marie Hennings & Oliver Kniejski
 */
public class ReflectiveMaterial extends Material {
    /**
     * The diffuse color for the lambert component.
     */
    public final Color diffuse;
    /**
     * The specular color for the phong component.
     */
    public final Color specular;
    /**
     * The phong exponent.
     */
    public final int exponent;
    /**
     * The color for the reflection.
     */
    public final Color reflection;

    /**
     * This constructor creates a  reflective material.
     *
     * @param diffuse    The diffuse color for the lambert component.
     * @param specular   The specular color for the phong component.
     * @param exponent   The phong exponent.
     * @param reflection The color for the reflection.
     */
    public ReflectiveMaterial(final Color diffuse, final Color specular, final int exponent, final Color reflection) {
        if (diffuse == null || specular == null || reflection == null)
            throw new IllegalArgumentException("Colors must not be null.");
        this.diffuse = diffuse;
        this.specular = specular;
        this.exponent = exponent;
        this.reflection = reflection;
    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
        if (hit == null || world == null || tracer == null)
            throw new IllegalArgumentException("Parameters must not be null");

        Normal3 n = hit.normal;
        Vector3 d = hit.ray.d;
        Vector3 rd = d.add(n.mul(d.invert().dot(n)).mul(2));
        Point3 hitPoint = hit.ray.at(hit.t);

        Color c = diffuse.mul(world.ambientColor).add(reflection.mul(tracer.tracing(hitPoint, rd, world)));

        List<Light> lights = world.getLights();
        for (Light light : lights) {

            if (light.illuminates(hitPoint, world)) {
                Vector3 l = light.directionFrom(hitPoint).normalized();
                Vector3 e = d.invert().normalized();
                Vector3 rl = l.reflectedOn(n);

                Color lambert = diffuse.mul(light.color).mul(Math.max(0, l.dot(n)));
                Color phong = specular.mul(light.color).mul(Math.pow((Math.max(0, e.dot(rl))), exponent));

                c = c.add(lambert).add(phong);
            }
        }
        return c;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReflectiveMaterial that = (ReflectiveMaterial) o;

        return exponent == that.exponent && diffuse.equals(that.diffuse)
                && specular.equals(that.specular)
                && reflection.equals(that.reflection);
    }

    @Override
    public int hashCode() {
        int result = diffuse.hashCode();
        result = 31 * result + (specular.hashCode());
        result = 31 * result + exponent;
        result = 31 * result + (reflection.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ReflectiveMaterial{" +
                "diffuse=" + diffuse +
                ", specular=" + specular +
                ", exponent=" + exponent +
                ", reflection=" + reflection +
                "} " + super.toString();
    }
}
