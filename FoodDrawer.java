import java.awt.*;
import java.util.ArrayList;

/**
 * Utility class for drawing food items on the game board.
 */
public class FoodDrawer {
    /**
     * Draws food items on the game board.
     *
     * @param g          The Graphics object for drawing.
     * @param foodTiles  The list of food tiles to be drawn.
     * @param tileSize   The size of each tile on the game board.
     */
    public static void drawFoods(Graphics g, ArrayList<Tile> foodTiles, int tileSize) {
        // Draw food (apple)
        int appleSize = tileSize * 2 / 3; // Adjust the size of the apple
        int outlineThickness = 2; // Thickness of the black outline

        for (Tile foodTile : foodTiles) {
            // Calculate the position of the apple on the game board
            int appleX = foodTile.x * tileSize + (tileSize - appleSize) / 2; // Center the apple horizontally
            int appleY = foodTile.y * tileSize + (tileSize - appleSize) / 2; // Center the apple vertically
            
            // Draw black outline for the apple
            g.setColor(Color.black); // Set color to black for the outline
            g.fillOval(appleX - outlineThickness, appleY - outlineThickness, appleSize + 2 * outlineThickness, appleSize + 2 * outlineThickness); // Draw the outline
            
            // Determine the color of the apple based on its type
            if (foodTile.isSpecialApple) {
                // Special apple
                g.setColor(Color.white); // Set color to white for the body
            } else {
                // Regular apple, yellow apple, or purple apple
                g.setColor(Color.red); // Default color for regular apples
                if (foodTile.isYellowApple) {
                    g.setColor(Color.yellow); // Yellow apple
                } else if (foodTile.isPurpleApple) {
                    g.setColor(new Color(128, 0, 128)); // Purple apple
                }
            }

            // Draw the filled apple
            g.fillOval(appleX, appleY, appleSize, appleSize); 

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
                
                // Draw the question mark
                g.drawString(questionMark, x, y); 
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
