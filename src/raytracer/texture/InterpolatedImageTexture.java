package raytracer.texture;

import javafx.scene.image.Image;

/**
 * This class represents an interpolated image texture.
 *
 * @author Steven Sobkowski & Marie Hennings
 */
public class InterpolatedImageTexture implements Texture {
    /**
     * The image for the texture.
     */
    public final Image image;

    /**
     * The constructor creates an image from a given path.
     * @param path The path of the image.
     */
    public InterpolatedImageTexture(final String path) {
        if (path == null) throw new IllegalArgumentException("Parameters must not be null!");

        image = new Image("file:" + path);

        if (image.isError()) {
            throw new IllegalArgumentException("Path must be an image!");
        }
    }

    @Override
    public Color getColor(TexCoord2 coord2) {
        double x = (image.getWidth() - 1) * coord2.u;
        double y = (image.getHeight() - 1) - (image.getHeight() - 1) * coord2.v;
        return null;
    }
}
