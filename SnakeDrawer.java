import java.awt.*;
import java.util.ArrayList;

/**
 * Utility class for drawing the snake on the game board.
 */
public class SnakeDrawer {

    /**
     * Draws the snake on the game board.
     *
     * @param g          The Graphics object to draw on.
     * @param tileSize   The size of each tile on the game board.
     * @param snakeHead  The position of the snake's head.
     * @param snakeBody  The list of positions of the snake's body segments.
     * @param headColor  The color of the snake's head.
     * @param bodyColor  The color of the snake's body segments.
     * @param velocityX  The horizontal velocity of the snake.
     * @param velocityY  The vertical velocity of the snake.
     */
    public static void drawSnake(Graphics g, int tileSize, Tile snakeHead, ArrayList<Tile> snakeBody, Color headColor, Color bodyColor, int velocityX, int velocityY) {
        // Draw snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            Color segmentColor = ColorUtility.calculateIntermediateColor(headColor, bodyColor, i, snakeBody.size());
            g.setColor(segmentColor);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        // Draw snake head
        g.setColor(headColor);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // Draw snake eyes
        SnakeEyesDrawer.drawEyes(g, tileSize, snakeHead.x, snakeHead.y, velocityX, velocityY);
    }
}
