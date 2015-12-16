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
 * This class represents PhongMaterial
 *
 * @author Steven Sobkowski
 */
public class PhongMaterial extends Material {
    /**
     * The diffuse texture property of the material.
     */
    public final Texture diffuseTexture;
    /**
     * The specular texture property of the material
     */
    public final Texture specularTexture;
    /**
     * The exponent needed for the colorFor - method.
     */
    public final int exponent;

    /**
     * The constructor of the PhongMaterial.
     *
     * @param diffuse  the given diffuse color
     * @param specular the given specular color
     * @param exponent the given exponent
     */
    public PhongMaterial(final Texture diffuse, final Texture specular, final int exponent) {
        this.diffuseTexture = diffuse;
        this.specularTexture = specular;
        this.exponent = exponent;
    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
        if (hit == null || world == null) throw new IllegalArgumentException("Parameters must not be null.");
        List<Light> lights = world.getLights();
        Color diffuse = diffuseTexture.getColor(hit.coord);
        Color specular = specularTexture.getColor(hit.coord);
        Color c = diffuse.mul(world.ambientColor);

        for (Light light : lights) {
            Point3 hitpoint = hit.ray.at(hit.t);

            if (light.illuminates(hitpoint, world)) {
                Normal3 n = hit.normal;
                Vector3 l = light.directionFrom(hitpoint).normalized();
                Vector3 e = hit.ray.d.invert().normalized();
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
        return "PhongMaterial{" +
                "diffuseTexture=" + diffuseTexture +
                ", specularTexture=" + specularTexture +
                ", exponent=" + exponent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhongMaterial that = (PhongMaterial) o;

        if (exponent != that.exponent) return false;
        if (!diffuseTexture.equals(that.diffuseTexture)) return false;
        return specularTexture.equals(that.specularTexture);

    }

    @Override
    public int hashCode() {
        int result = diffuseTexture.hashCode();
        result = 31 * result + specularTexture.hashCode();
        result = 31 * result + exponent;
        return result;
    }
}

