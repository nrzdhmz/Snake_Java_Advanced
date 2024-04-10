import java.util.ArrayList;

public class FoodManager {
    public static boolean eatFood(Tile snakeHead, ArrayList<Tile> foodTiles, ArrayList<Tile> snakeBody) {
        boolean ateFood = false; // Variable to track if food was eaten
        for (int i = 0; i < foodTiles.size(); i++) {
            Tile foodTile = foodTiles.get(i);
            if (collision(snakeHead, foodTile)) {
                // Handle food consumption logic here
                if (foodTile.isYellowApple) {
                    // Increase snake's length by 5 for yellow apple
                    for (int j = 0; j < 10; j++) {
                        snakeBody.add(new Tile(snakeBody.get(snakeBody.size() - 1).x, snakeBody.get(snakeBody.size() - 1).y));
                    }
                } else if (foodTile.isPurpleApple) {
                    // Determine the effect of purple apple
                    boolean gainPoints = Math.random() < 0.65; // 50% chance to gain points
                    if (gainPoints) {
                        // Increase snake's length by 10 for gaining points
                        for (int j = 0; j < 10; j++) {
                            snakeBody.add(new Tile(snakeBody.get(snakeBody.size() - 1).x, snakeBody.get(snakeBody.size() - 1).y));
                        }
                    } else {
                        // Decrease snake's length by 10 or reset length to 1
                        if (snakeBody.size() > 10) {
                            for (int j = 0; j < 10; j++) {
                                snakeBody.remove(snakeBody.size() - 1);
                            }
                        } else {
                            snakeBody.subList(1, snakeBody.size()).clear();
                        }
                    }
                } else if (foodTile.isSpecialApple) {
                    // Award 3 points for eating the special apple
                    for (int j = 0; j < 3; j++) {
                        snakeBody.add(new Tile(snakeBody.get(snakeBody.size() - 1).x, snakeBody.get(snakeBody.size() - 1).y));
                    }
                } else {
                    // Regular apple, increase snake's length by 1
                    snakeBody.add(new Tile(foodTile.x, foodTile.y));
                }

                // Remove the eaten food tile
                foodTiles.remove(i);
                ateFood = true; // Set ateFood to true since food was eaten
                break;
            }
        }
        return ateFood; // Return whether food was eaten or not
    }

    private static boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
}
