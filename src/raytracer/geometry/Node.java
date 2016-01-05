package raytracer.geometry;

import raytracer.material.Material;
import raytracer.material.NoMaterial;
import raytracer.math.Normal3;
import raytracer.math.Ray;
import raytracer.math.Transform;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a holder for one or more Geometries and a corresponding transformation.
 *
 * @author TrayRacer Team
 */
public class Node extends Geometry {
    /**
     * The Transformation.
     */
    public final Transform transform;
    /**
     * The List for the Geometries.
     */
    public final List<Geometry> geoList;

    /**
     * This constructor creates a Node.
     *
     * @param transform The Transformation.
     * @param geoList   The List for the Geometries.
     * @param material  The Material.
     */
    public Node(final Transform transform, final List<Geometry> geoList, final Material material) {
        super(material);
        if (geoList == null || transform == null) throw new IllegalArgumentException("Parameters must not be null.");
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

        Hit hitBacktrans;
        if (this.material instanceof NoMaterial) {
            hitBacktrans = new Hit(smallestHit.t, ray, smallestHit.material, nBacktrans, smallestHit.coord);
        } else {
            hitBacktrans = new Hit(smallestHit.t, ray, this.material, nBacktrans, smallestHit.coord);
        }
        return hitBacktrans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Node node = (Node) o;

        return !(transform != null ? !transform.equals(node.transform) : node.transform != null)
                && !(geoList != null ? !geoList.equals(node.geoList) : node.geoList != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (transform != null ? transform.hashCode() : 0);
        result = 31 * result + (geoList != null ? geoList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "transform=" + transform +
                ", geoList=" + geoList +
                "} " + super.toString();
    }
}
