package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.light.Light;
import raytracer.texture.Color;

import java.util.List;

/**
 * This class represents PhongMaterial
 *
 * @author Steven Sobkowski
 */
public class PhongMaterial extends Material{

    public final Color diffuse;
    public final Color specular;
    public final int exponent;

    public PhongMaterial(Color diffuse, Color specular, int exponent) {
        this.diffuse = diffuse;
        this.specular = specular;
        this.exponent = exponent;
    }
    @Override
    public Color colorFor(Hit hit, World world){
        if (hit == null || world == null) throw new IllegalArgumentException("Parameters must not be null.");
        List<Light> lights = world.getLights();
        Color c = diffuse.mul(world.ambientColor);
        for(Light l : lights) {
            if (l.illuminates(hit.ray.at(hit.t))) {
                c = c.add(diffuse.mul(l.color.mul(Math.max(0,hit.normal.dot(l.directionFrom(hit.ray.at(hit.t)).normalized()))))).add(specular.mul(l.color.mul(Math.pow((Math.max(0,hit.ray.d.invert().dot(l.directionFrom(hit.ray.at(hit.t)).reflectedOn(hit.normal)))),exponent))));
            }
        }
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhongMaterial that = (PhongMaterial) o;

        if (exponent != that.exponent) return false;
        if (!diffuse.equals(that.diffuse)) return false;
        return specular.equals(that.specular);

    }

    @Override
    public int hashCode() {
        int result = diffuse.hashCode();
        result = 31 * result + specular.hashCode();
        result = 31 * result + exponent;
        return result;
    }

    @Override
    public String toString() {
        return "PhongMaterial{" +
                "diffuse=" + diffuse +
                ", specular=" + specular +
                ", exponent=" + exponent +
                '}';
    }
}

