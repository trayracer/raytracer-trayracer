package raytracer.texture;

/**
 * a single color texture.
 *
 * @author Steven Sobkowski
 */
public class SingleColorTexture implements Texture {

    /**
     * The color attribute.
     */
    public final Color color;

    /**
     * The constructor for the Texture.
     * @param color the given color.
     */
    public SingleColorTexture( final Color color){
        this.color = color;
    }


    @Override
    public Color getColor(final TexCoord2 coord2) {
        return color;
    }
}
