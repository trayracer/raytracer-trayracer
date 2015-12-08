package raytracer.material;

import raytracer.geometry.Hit;
import raytracer.geometry.World;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.math.Vector3;
import raytracer.texture.Color;

/**
 * Created by ok on 08.12.15.
 */
public class Tracer {

    int counter;

    public Tracer(int counter){
        this.counter = counter;
    }
    public Color tracing(final Ray ray, final World world){
        if(counter < 0) return world.backgroundColor;
        else {
            Hit hit = world.hit(ray);
            if(hit != null){
                Normal3 n = hit.normal;
                Vector3 d = hit.ray.d;
                Vector3 rd = d.add(n.mul(d.invert().dot(n)).mul(2));
                Point3 hitpoint = hit.ray.at(hit.t);

                Hit newHit = world.hit(new Ray(hitpoint,rd));
                if (newHit != null){
                    return  hit.geo.material.colorFor(newHit, world, new Tracer(counter-1));
                } else {
                    return world.backgroundColor;
                }
            }
            else {
                return world.backgroundColor;
            }
        }

    }
}
