import java.awt.Color;

public class ColorUtility {
    // Method to calculate intermediate color between two colors
    public static Color calculateIntermediateColor(Color color1, Color color2, int currentIndex, int totalSegments) {
        float ratio = (float) (totalSegments - currentIndex) / totalSegments;
        int red = (int) (color1.getRed() * ratio + color2.getRed() * (1 - ratio));
        int green = (int) (color1.getGreen() * ratio + color2.getGreen() * (1 - ratio));
        int blue = (int) (color1.getBlue() * ratio + color2.getBlue() * (1 - ratio));
        return new Color(red, green, blue);
    }
}
