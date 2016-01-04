package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.Normal3;
import raytracer.math.Ray;
import raytracer.math.Transform;

import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 * @author
 */
public class Node extends Geometry {
    public final Transform transform;
    public final List<Geometry> geoList;

    public Node(final Transform transform, final List<Geometry> geoList, final Material material){
        super(material);
        this.transform = transform;
        this.geoList = geoList;
    }

    @Override
    public Hit hit(final Ray ray) {
        if (ray == null) throw new IllegalArgumentException("Ray must not be null.");

        Ray rayTrans = transform.mul(ray);

        if (geoList.size() == 0) return null;
        List<Hit> hits = new LinkedList<>();

        for (Geometry geo : geoList) {
            if (geo.hit(rayTrans) != null) {
                hits.add(geo.hit(rayTrans));
            }
        }
        if (hits.size() == 0) return null;
        Hit smallestHit = hits.get(0);
        if (hits.size() > 1) {
            for (int i = 1; i < hits.size(); i++) {
                if (hits.get(i).t < smallestHit.t) {
                    smallestHit = hits.get(i);
                }
            }
        }
        Normal3 nBacktrans = transform.mul(smallestHit.normal);

        //TODO: use this Material, if not NoMaterial
        Hit hitBacktrans = new Hit(smallestHit.t, ray, smallestHit.geo, nBacktrans, smallestHit.coord);

        return hitBacktrans;
    }
}
