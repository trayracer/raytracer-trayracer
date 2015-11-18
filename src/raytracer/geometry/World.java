package raytracer.geometry;

import raytracer.math.Ray;
import raytracer.texture.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a holder for multiple geometries.
 *
 * @author Oliver Kniejski
 */
public class World {
    /**
     * The background color.
     */
    public final Color backgroundColor;
    /**
     * The Geometries in this world.
     */
    private List<Geometry> geometries = new LinkedList<>();

    /**
     * This constructor creates an empty world with the given background Color.
     * @param backgroundColor The background Color.
     */
    public World(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * This method checks if a ray hits any geometry in this world and returns the Hit with the smallest positive t or null if no geometry is hit.
     * @param ray The ray.
     * @return The Hit with the smallest positive t or null if no geometry is hit.
     */
    public Hit hit(final Ray ray) {
        if (geometries.size() == 0) return null;
        List<Hit> hits = new LinkedList<>();
        //noinspection Convert2streamapi
        for (Geometry geo : geometries) {
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

    /**
     * This method adds a Geometry to this world.
     * @param geo The Geometry to add.
     */
    public void addGeometry(final Geometry geo) {
        this.geometries.add(geo);
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        World world = (World) o;

        if (backgroundColor != null ? !backgroundColor.equals(world.backgroundColor) : world.backgroundColor != null)
            return false;
        return !(geometries != null ? !geometries.equals(world.geometries) : world.geometries != null);
    }

    @Override
    public int hashCode() {
        int result = backgroundColor != null ? backgroundColor.hashCode() : 0;
        result = 31 * result + (geometries != null ? geometries.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "World{" +
                "backgroundColor=" + backgroundColor +
                ", geometries=" + geometries +
                '}';
    }
}
