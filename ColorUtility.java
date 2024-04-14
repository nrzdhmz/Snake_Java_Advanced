import java.awt.Color;

/**
 * Utility class for working with colors.
 */
public class ColorUtility {
    /**
     * Calculates an intermediate color between two colors based on a given index and total segments.
     *
     * @param color1        The first color.
     * @param color2        The second color.
     * @param currentIndex  The current index.
     * @param totalSegments The total number of segments.
     * @return The intermediate color.
     */
    public static Color calculateIntermediateColor(Color color1, Color color2, int currentIndex, int totalSegments) {
        float ratio = (float) (totalSegments - currentIndex) / totalSegments;
        int red = (int) (color1.getRed() * ratio + color2.getRed() * (1 - ratio));
        int green = (int) (color1.getGreen() * ratio + color2.getGreen() * (1 - ratio));
        int blue = (int) (color1.getBlue() * ratio + color2.getBlue() * (1 - ratio));
        return new Color(red, green, blue);
    }
}
