package raytracer.geometry;

import raytracer.texture.Color;
import raytracer.math.Ray;

/**
 * Created by Steven Sobkowski on 10.11.2015.
 * an abstract class which represents a geometry
 */
public abstract class Geometry {
    /**
     * the color of the geometry
     */
    final public Color color;

    public Geometry(Color color){
        this.color = color;
    }

    /**
     * an abstract method to calculate a hit between a ray and a geometry
     * @param r the ray
     * @return the hit of the geometry and the ray
     */
    public abstract Hit hit(Ray r);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Geometry geometry = (Geometry) o;

        return !(color != null ? !color.equals(geometry.color) : geometry.color != null);

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "color=" + color +
                '}';
    }
}
