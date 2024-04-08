import java.awt.*;
import java.util.ArrayList;

public class FoodDrawer {
    public static void drawFoods(Graphics g, ArrayList<Tile> foodTiles, int tileSize) {
        // Draw food (apple)
        int appleSize = tileSize * 2 / 3; // Adjust the size of the apple
        for (Tile foodTile : foodTiles) {
            g.setColor(Color.red); // Default color for regular apples
            if (foodTile.isYellowApple) {
                g.setColor(Color.yellow); // Yellow apple
            } else if (foodTile.isPurpleApple) {
                g.setColor(new Color(128, 0, 128)); // Purple apple
            }

            int appleX = foodTile.x * tileSize + (tileSize - appleSize) / 2; // Center the apple horizontally
            int appleY = foodTile.y * tileSize + (tileSize - appleSize) / 2; // Center the apple vertically
            g.fillOval(appleX, appleY, appleSize, appleSize); // Draw the rounded apple
            
            // Draw the '?' sign inside the purple apple
            if (foodTile.isPurpleApple) {
                g.setColor(Color.white); // Color for the question mark
                Font font = new Font("Arial", Font.BOLD, 20); // Define the font
                g.setFont(font); // Set the font
                String questionMark = "?"; // The question mark character
                // Calculate the position to center the question mark inside the apple
                FontMetrics fm = g.getFontMetrics(font);
                int x = appleX + (appleSize - fm.stringWidth(questionMark)) / 2;
                int y = appleY + (appleSize + fm.getAscent()) / 2;
                g.drawString(questionMark, x, y); // Draw the question mark
            }

            // Draw the green leaf (triangle) for both regular and fully yellow apples
            int leafWidth = appleSize / 2; // Width of the leaf
            int leafHeight = appleSize / 4; // Height of the leaf
            int leafX = appleX + appleSize / 4; // Position the leaf horizontally
            int leafY = appleY - leafHeight; // Position the leaf above the apple
            Polygon leaf = new Polygon();
            leaf.addPoint(leafX, leafY); // Top point of the leaf
            leaf.addPoint(leafX - leafWidth / 2, leafY + leafHeight); // Bottom left point of the leaf
            leaf.addPoint(leafX + leafWidth / 2, leafY + leafHeight); // Bottom right point of the leaf
            g.setColor(Color.green);
            g.fillPolygon(leaf); // Draw the leaf
        }
    }
}
