package raytracer.texture;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

/**
 * This class represents an image texture.
 *
 * @author Steven Sobkowski & Marie Hennings
 */
public class ImageTexture implements Texture {
    /**
     * The image for the texture.
     */
    public final Image image;

    /**
     * The constructor creates an image from a given path.
     *
     * @param path The path of the image.
     */
    public ImageTexture(final String path) {
        if (path == null) throw new IllegalArgumentException("Parameters must not be null!");

        image = new Image("file:" + path);

        if (image.isError()) {
            throw new IllegalArgumentException("Path must be an image!");
        }
    }

    @Override
    public Color getColor(final TexCoord2 coord2) {
        if (coord2 == null) throw new IllegalArgumentException("TexCoord2 must not be null!");
        int x = (int) ((image.getWidth() - 1) - (image.getWidth() - 1) * coord2.u);
        int y = (int) ((image.getHeight() - 1) - (image.getHeight() - 1) * coord2.v);

        javafx.scene.paint.Color colorFX = image.getPixelReader().getColor(x, y);
        Color color = new Color(colorFX.getRed(), colorFX.getGreen(), colorFX.getBlue());

        return color;
    }
}
