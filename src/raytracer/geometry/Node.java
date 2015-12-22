package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.Ray;
import raytracer.math.Transform;

import java.util.List;

/**
 *
 *
 * @author
 */
public class Node extends Geometry {
    public final Transform transform;
    public final List<Geometry> geoList;

    public Node(Transform transform, List<Geometry> geoList, Material material){
        super(material);
        this.transform = transform;
        this.geoList = geoList;
    }

    @Override
    public Hit hit(Ray r) {
        return null;
    }
}
