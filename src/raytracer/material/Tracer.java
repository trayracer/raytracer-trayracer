package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * This class
 *
 * @author Steven Sobkowski & Marie Hennings & Oliver Kniejski
 */
public class Tracer {
    private int counter;

    public Tracer(int counter){
        this.counter = counter;
    }

    public Color tracing(final Point3 origin, final Vector3 direction, final World world){
        if(counter < 0) return world.backgroundColor;
        counter--;

        Ray ray = new Ray(origin, direction.normalized());
        Hit hit = world.hit(ray);
        if (hit == null) return world.backgroundColor;
        Normal3 n = hit.normal;
        Vector3 rd = direction.add(n.mul(direction.invert().dot(n)).mul(2));
        Point3 hitpoint = hit.ray.at(hit.t);

        return hit.geo.material.colorFor(hit, world, this);
    }
}
