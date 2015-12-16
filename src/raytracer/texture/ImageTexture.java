package raytracer.texture;

import javafx.scene.image.Image;

import java.nio.file.Path;

/**
 * Created by Steve-O on 16.12.2015.
 */
public class ImageTexture implements Texture {

    public final Image image;

    public ImageTexture(final String path){
        if(path == null) throw new IllegalArgumentException("Parameters must not be null!");
        image = new Image(path);
    }

    @Override
    public Color getColor(final double u, final double v) {

        return null;
    }
}
