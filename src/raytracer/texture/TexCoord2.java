package raytracer.texture;

/**
 * This class represents a coordinate of the texture.
 *
 * @author Steven Sobkowski & Marie Hennings
 */
public class TexCoord2 {
    /**
     * u coordinate must be between 0 and 1.
     */
    public final double u;
    /**
     * v coordinate must be between 0 and 1.
     */
    public final double v;

    /**
     * The constructor creates a coordinate of two given doubles.
     * The given coordinates are transformed to coordinates saved between 0 and 1.
     *
     * @param u The u coordinate.
     * @param v The v coordinate.
     */
    public TexCoord2(final double u, final double v) {
        double uBuffer = u;
        double vBuffer = v;
        if (uBuffer > 1.0 || uBuffer < -1.0) {
            uBuffer = uBuffer % 1.0;
        }
        if (vBuffer > 1.0 || vBuffer < -1.0) {
            vBuffer = vBuffer % 1.0;
        }
        if (uBuffer < 0.0) {
            uBuffer = uBuffer + 1.0;
        }
        if (vBuffer < 0.0) {
            vBuffer = vBuffer + 1.0;
        }
        this.u = uBuffer;
        this.v = vBuffer;
    }
}
