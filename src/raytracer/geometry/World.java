package raytracer.geometry;

import raytracer.light.Light;
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
     * The color of the ambient light.
     */
    public final Color ambientColor;
    /**
     * The Geometries in this world.
     */
    private List<Geometry> geometries = new LinkedList<>();
    /**
     * The Lights in this world.
     */
    private List<Light> lights = new LinkedList<>();

    /**
     * This constructor creates an empty world with the given background color and ambient light color.
     *
     * @param backgroundColor The background Color.
     * @param ambientColor The Color of the ambient Light.
     */
    public World(final Color backgroundColor,  final Color ambientColor) {
        if (backgroundColor == null || ambientColor == null) throw new IllegalArgumentException("Parameters must not be null.");
        this.backgroundColor = backgroundColor;
        this.ambientColor = ambientColor;
    }

    /**
     * This method checks if a ray hits any geometry in this world and returns the Hit with the
     * smallest positive t or null if no geometry is hit.
     *
     * @param ray The ray.
     * @return The Hit with the smallest positive t or null if no geometry is hit.
     */
    public Hit hit(final Ray ray) {
        if (ray == null) throw new IllegalArgumentException("Ray must not be null.");
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
        if (hits.size() > 1) {
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
     *
     * @param geo The Geometry to add.
     */
    public void addGeometry(final Geometry geo) {
        if (geo == null) throw new IllegalArgumentException("Geometrie must not be null.");
        this.geometries.add(geo);
    }

    /**
     * This method adds a Light to this world.
     * @param
     */
    public void addLight(final Light light){
        if (light == null) throw new IllegalArgumentException("Light must not be null.");
        this.lights.add(light);
    }

    /**
     * This method returns a LinkedList containing all Lights in this world.
     *
     * @return LinkedList containing all Lights in this world.
     */
    public List<Light> getLights(){
        return lights;
    }



    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(final Object o) {
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
