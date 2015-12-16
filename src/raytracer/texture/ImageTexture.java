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

        if (u > 1.0){
            u = u % 1.0;
        }
        if (v > 1.0){
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
//        if (image.getPixelReader() == null) {
//            return new Color(0, 1, 0);
//        }
//        if (y > image.getHeight() || y < 0 || x > image.getWidth() || x < 0){
//            System.out.println("h: " + image.getHeight() + " w: " + image.getWidth() + " u: " + u + ", v: " + v + " x: " + x + ", y: " + y ); // " color: " + image.getPixelReader().getArgb(x, y));
//        }
//        if (image.getPixelReader().getColor(x, y) != null) {
//            javafx.scene.paint.Color colorFX = image.getPixelReader().getColor(x, y);
//            color = new Color(colorFX.getRed() / 255, colorFX.getGreen() / 255, colorFX.getBlue() / 255);
//        } else {
//            System.out.println("Color is null");
//            color = new Color(1, 0, 0);
//        }
        int argb = image.getPixelReader().getArgb(x, y);
        int r = (argb & 0xff0000) >> 16;
        int g = (argb & 0xff00) >> 8;
        int b = (argb & 0xff);
        color = new Color(r / 255.0, g / 255.0, b / 255.0);

        return color;
    }
}
