package raytracer.texture;

/**
 * The interface for the texture.
 *
 * @author Steven Sobkowski
 */
public interface Texture {

    /**
     * This method returns the color for the Texture.
     *
     * @param coord2 the TexCoords.
     * @return the color of the texture at the given coords.
     */
    public Color getColor(final TexCoord2 coord2);
}
