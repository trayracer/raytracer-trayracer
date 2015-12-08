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
 * Created by ok on 08.12.15.
 */
public class ReflectiveMaterial extends Material{

    public final Color diffuse;
    public final Color specular;
    public final int exponent;
    public final Color reflection;

    public ReflectiveMaterial(final Color diffuse, final Color specular, final int exponent, final Color reflection){
        this.diffuse = diffuse;
        this.specular = specular;
        this.exponent = exponent;
        this.reflection = reflection;
    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
        if(hit == null || world == null || tracer == null) throw new IllegalArgumentException("Parameters must not be null");
        List<Light> lights = world.getLights();
        Normal3 n = hit.normal;
        Vector3 d = hit.ray.d;
        Point3 hitpoint = hit.ray.at(hit.t);

        Color c = diffuse.mul(world.ambientColor).add(reflection.mul(tracer.tracing(hit.ray,world)));

        for (Light light : lights) {

            if (light.illuminates(hitpoint, world)) {
                Vector3 l = light.directionFrom(hitpoint).normalized();
                Vector3 e = d.invert().normalized();
                Vector3 rl = l.reflectedOn(n);


                Color lambert = diffuse.mul(light.color).mul(Math.max(0, l.dot(n)));
                Color phong = specular.mul(light.color).mul(Math.pow((Math.max(0, e.dot(rl))), exponent));

                c = c.add(lambert).add(phong);
            }
        }
        return c;
    }
}
