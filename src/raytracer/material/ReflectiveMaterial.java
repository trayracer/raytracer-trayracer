package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.light.Light;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;
import raytracer.texture.Color;
import raytracer.texture.Texture;

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
    public final Texture diffuseTexture;
    /**
     * The specular color for the phong component.
     */
    public final Texture specularTexture;
    /**
     * The phong exponent.
     */
    public final int exponent;
    /**
     * The color for the reflection.
     */
    public final Texture reflectionTexture;

    /**
     * This constructor creates a  reflective material.
     *
     * @param diffuse    The diffuse color for the lambert component.
     * @param specular   The specular color for the phong component.
     * @param exponent   The phong exponent.
     * @param reflection The color for the reflection.
     */
    public ReflectiveMaterial(final Texture diffuse, final Texture specular, final int exponent, final Texture reflection) {
        if (diffuse == null || specular == null || reflection == null)
            throw new IllegalArgumentException("Colors must not be null.");
        this.diffuseTexture = diffuse;
        this.specularTexture = specular;
        this.exponent = exponent;
        this.reflectionTexture = reflection;
    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
        if (hit == null || world == null || tracer == null)
            throw new IllegalArgumentException("Parameters must not be null");

        Color diffuse = diffuseTexture.getColor(hit.coord);
        Color specular = specularTexture.getColor(hit.coord);
        Color reflection = reflectionTexture.getColor(hit.coord);

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
    public String toString() {
        return "ReflectiveMaterial{" +
                "diffuseTexture=" + diffuseTexture +
                ", specularTexture=" + specularTexture +
                ", exponent=" + exponent +
                ", reflectionTexture=" + reflectionTexture +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReflectiveMaterial that = (ReflectiveMaterial) o;

        if (exponent != that.exponent) return false;
        if (!diffuseTexture.equals(that.diffuseTexture)) return false;
        if (!specularTexture.equals(that.specularTexture)) return false;
        return reflectionTexture.equals(that.reflectionTexture);

    }

    @Override
    public int hashCode() {
        int result = diffuseTexture.hashCode();
        result = 31 * result + specularTexture.hashCode();
        result = 31 * result + exponent;
        result = 31 * result + reflectionTexture.hashCode();
        return result;
    }
}
