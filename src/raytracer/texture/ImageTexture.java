package raytracer.texture;

import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.nio.file.Path;

/**
 * Created by Steve-O on 16.12.2015.
 */
public class ImageTexture implements Texture {

    public final Image image;

    public ImageTexture(final String path) {
        if (path == null) throw new IllegalArgumentException("Parameters must not be null!");
        //TODO prüfen ob path ein bild ist

        image = new Image("file:" + path);
        if (image.isError()) {
            throw new IllegalArgumentException("Path must be an image!");
        }
    }

    @Override
    public Color getColor(final TexCoord2 coord2) {
        double u = coord2.u;
        double v = coord2.v;

        if (u > 1.0) {
            u = u % 1.0;
        }
        if (v > 1.0) {
            v = v % 1.0;
        }
        if (u < 0.0) {
            u = u + 1.0;
        }
        if (v < 0.0) {
            v = v + 1.0;
        }

        int x = (int) ((image.getWidth() - 1) - (image.getWidth() - 1) * u);
        int y = (int) ((image.getHeight() - 1) - (image.getHeight() - 1) * v);

        Color color;

        javafx.scene.paint.Color colorFX = image.getPixelReader().getColor(x, y);
        color = new Color(colorFX.getRed(), colorFX.getGreen(), colorFX.getBlue());
        
        return color;
    }
}
