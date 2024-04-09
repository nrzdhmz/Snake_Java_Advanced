import java.util.ArrayList;
import java.util.Random;

public class SpecialAppleManager {
    private static final int MAX_ATTEMPTS = 100; // Maximum attempts to find a valid position for the special apple
    private static final int APPLE_SPEED = 100; // Speed of the special apple

    // Method to calculate the new position of the special apple
    public static Tile calculateNewPosition(Tile snakeHead, ArrayList<Tile> snakeBody, int boardWidth, int boardHeight, int tileSize) {
        Random random = new Random();
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            // Generate random position for the new special apple
            int newX = random.nextInt(boardWidth / tileSize);
            int newY = random.nextInt(boardHeight / tileSize);

            // Check if the position is not occupied by the snake's head or body
            boolean positionOccupied = false;
            if (newX == snakeHead.x && newY == snakeHead.y) {
                positionOccupied = true;
            } else {
                for (Tile snakePart : snakeBody) {
                    if (snakePart.x == newX && snakePart.y == newY) {
                        positionOccupied = true;
                        break;
                    }
                }
            }

            if (!positionOccupied) {
                // Found a valid position for the special apple
                return new Tile(newX, newY);
            }

            attempts++;
        }

        // If maximum attempts reached without finding a valid position, return null
        return null;
    }

    // Method to move the special apple away from the snake's head
    public static Tile moveAwayFromSnake(Tile specialApple, Tile snakeHead) {
        int dx = specialApple.x - snakeHead.x;
        int dy = specialApple.y - snakeHead.y;

        if (Math.abs(dx) > Math.abs(dy)) {
            // Move horizontally
            if (dx > 0) {
                specialApple.x += APPLE_SPEED; // Move right
            } else {
                specialApple.x -= APPLE_SPEED; // Move left
            }
        } else {
            // Move vertically
            if (dy > 0) {
                specialApple.y += APPLE_SPEED; // Move down
            } else {
                specialApple.y -= APPLE_SPEED; // Move up
            }
        }

        return specialApple;
    }
}
