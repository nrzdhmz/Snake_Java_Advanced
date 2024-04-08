import java.awt.*;
import java.util.ArrayList;

public class SnakeDrawer {

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
