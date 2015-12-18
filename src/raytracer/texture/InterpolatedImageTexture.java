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
     *
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
        double x = (image.getWidth() - 1) - (image.getWidth() - 1) * coord2.u;
        double y = (image.getHeight() - 1) - (image.getHeight() - 1) * coord2.v;

        //get the color of the 4 neighbour coordinates
        Color p00color = new Color(image.getPixelReader().getColor((int) Math.floor(x), (int) Math.floor(y)));
        Color p10color = new Color(image.getPixelReader().getColor((int) Math.ceil(x), (int) Math.floor(y)));
        Color p01color = new Color(image.getPixelReader().getColor((int) Math.floor(x), (int) Math.ceil(y)));
        Color p11color = new Color(image.getPixelReader().getColor((int) Math.ceil(x), (int) Math.ceil(y)));

        double dx = x - Math.floor(x);
        double dy = y - Math.floor(y);
        Color q0color = p00color.mul(1 - dx).add(p10color.mul(dx));
        Color q1color = p01color.mul(1 - dx).add(p11color.mul(dx));

        return q0color.mul(1 - dy).add(q1color.mul(dy));
    }
}
