package raytracer.texture;

/**
 * Created by Steve-O on 16.12.2015.
 */
public class SingleColorTexture implements Texture {

    public final Color color;


    public SingleColorTexture( final Color color){
        this.color = color;
    }


    @Override
    public Color getColor(final double u, final double v) {

        return color;
    }
}
