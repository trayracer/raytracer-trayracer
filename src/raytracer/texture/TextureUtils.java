package raytracer.texture;

import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Ray;

/**
 * This class holds methods for calculation texture coordinates.
 *
 * @author Marie Hennings
 */
public class TextureUtils {

    /**
     * This method calculates the texture coordinates of a plane.
     *
     * @param ray    The ray.
     * @param t      The t.
     * @param normal The normal of the plane.
     * @return The texture coordinates.
     */
    public static TexCoord2 getPlaneTexCoord(final Ray ray, final double t, final Normal3 normal) {
        //TODO TexCoord for non axis aligned plane
        Point3 hitpoint = ray.at(t);
        TexCoord2 coord2;
        if (normal.x == 0 && normal.y == 0) {
            coord2 = new TexCoord2(hitpoint.x, hitpoint.y);
        } else if (normal.x == 0 && normal.z == 0) {
            coord2 = new TexCoord2(hitpoint.x, hitpoint.z);
        } else {
            coord2 = new TexCoord2(hitpoint.y, hitpoint.z);
        }
        return coord2;
    }

    /**
     * This method calculates the texture coordinates of a cone and a cylinder.
     *
     * @param ray    The ray.
     * @param t      The t.
     * @param center The center baseline point.
     * @param height The height of the geometry.
     * @return
     */
    public static TexCoord2 getConeTexCoord(final Ray ray, final double t, Point3 center, double height) {
        Point3 hitpoint = ray.at(t);
        double phi = Math.atan2((hitpoint.x - center.x), (hitpoint.y - center.y));

        double u = phi / (2 * Math.PI);
        double v = (hitpoint.z - center.z) / height;

        return new TexCoord2(u, v);
    }
}
