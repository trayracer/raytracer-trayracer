package raytracer.texture;

/**
 * This class represents a color in RGB color model.
 *
 * @author Marie Hennings
 */
public class Color {
    /**
     * The red component of the color.
     */
    public final double r;
    /**
     * The green component of the color.
     */
    public final double g;
    /**
     * The blue component of the color.
     */
    public final double b;

    /**
     * This constructor creates a new rgb Color.
     *
     * @param r The red component. Should be between 0 and 1. Must not be below 0.
     * @param g The green component. Should be between 0 and 1. Must not be below 0.
     * @param b The blue component. Should be between 0 and 1. Must not be below 0.
     */
    public Color(final double r, final double g, final double b) {
        if (r < 0) {
            throw new IllegalArgumentException("The parameter 'r' should be between 0 and 1 and must not be below 0.");
        }
        if (g < 0) {
            throw new IllegalArgumentException("The parameter 'g' should be between 0 and 1 and must not be below 0.");
        }
        if (b < 0) {
            throw new IllegalArgumentException("The parameter 'b' should be between 0 and 1 and must not be below 0.");
        }

        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * This method calculates the sum of this color and a color and returns the result as a color.
     *
     * @param c The color which is added.
     * @return The sum of the two colors.
     */
    public Color add(final Color c) {
        if (c == null) throw new IllegalArgumentException("Color must not be null.");
        return new Color(r + c.r, g + c.g, b + c.b);
    }

    /**
     * This method calculates the difference of this color and a color and returns the result as a color.
     *
     * @param c The color which is subtracted.
     * @return The result of the difference.
     */
    public Color sub(final Color c) {
        if (c == null) throw new IllegalArgumentException("Color must not be null.");
        return new Color(r - c.r, g - c.g, b - c.b);
    }

    /**
     * This method calculates the product of this color and a color and returns the result as a color.
     *
     * @param c The color which is multiplied.
     * @return The result of the multiplication.
     */
    public Color mul(final Color c) {
        if (c == null) throw new IllegalArgumentException("Color must not be null.");
        return new Color(r * c.r, g * c.g, b * c.b);
    }

    /**
     * This method calculates the product of a scalar and each channel of this color and returns the result as a color.
     *
     * @param v The scalar which is multiplied.
     * @return The result of the multiplication.
     */
    public Color mul(final double v) {
        if (v < 0) throw new IllegalArgumentException("Scalar must be greater than zero.");
        return new Color(r * v, g * b, b * v);
    }

    /**
     * This method returns the RGB value representing this color.
     *
     * @return The int representing the RGB color.
     */
    public int getRGB() {
        return new java.awt.Color((int) (r * 255), (int) (g * 255), (int) (b * 255)).getRGB();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        if (Double.compare(color.r, r) != 0) return false;
        if (Double.compare(color.g, g) != 0) return false;
        return Double.compare(color.b, b) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(r);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(g);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(b);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
