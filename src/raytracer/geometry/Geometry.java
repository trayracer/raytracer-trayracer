package raytracer.geometry;

import raytracer.texture.Color;
import raytracer.math.Ray;

/**
 * Created by Steve-O on 10.11.2015.
 */
public abstract class Geometry {

    final Color color;

    public Geometry(Color color){
        this.color = color;
    }

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
