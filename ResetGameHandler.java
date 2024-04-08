public class ResetGameHandler {
  // Method to reset the game
  public static void resetGame(SnakeGame snakeGame) {
      snakeGame.snakeHead = new Tile(0, 6); // Reset snake head position
      snakeGame.snakeBody.clear(); // Clear snake body
      snakeGame.foodTiles.clear(); // Clear food tiles
      snakeGame.placeFood(snakeGame.selectedFood); // Place food on the board
      snakeGame.velocityX = 1; // Reset horizontal velocity
      snakeGame.velocityY = 0; // Reset vertical velocity
      snakeGame.gameOver = false; // Set game over flag to false
      snakeGame.gameLoop.restart(); // Restart the game loop
      snakeGame.movesSinceLastFood = 0; // Reset moves counter
  }
}
