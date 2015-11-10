package raytracer.geometry;

import raytracer.math.Ray;
import raytracer.texture.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * This class
 *
 * @author Oliver Kniejski
 */
public class World {

    public final Color backgroundColor;
    private List<Geometry> objects = new LinkedList<>();

    public World(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Hit hit(Ray ray) {
        if (objects.size() == 0) return null;
        List<Hit> hits = new LinkedList<>();

        for (Geometry geo : objects) {
            if (geo.hit(ray) != null) {
                hits.add(geo.hit(ray));
            }
        }
        if (hits.size() == 0) return null;

        Hit smallestHit = hits.get(0);

        if (hits.size()>1) {
            for (int i = 1; i < hits.size(); i++) {
                if (hits.get(i).t < smallestHit.t) {
                    smallestHit = hits.get(i);
                }
            }
        }
        return smallestHit;
    }

    public void addGeometry(Geometry geo) {
        this.objects.add(geo);
    }
}
