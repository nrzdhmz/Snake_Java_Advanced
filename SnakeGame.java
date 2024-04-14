import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.sound.sampled.*;

/**
 * SnakeGame class represents the game logic and user interface for the Snake game.
 */
public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    // Instance variables to store start and end times
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private SoundManager soundManager;

    // Instance variables
    int boardWidth; // Width of the game board
    int boardHeight; // Height of the game board
    int tileSize = 40; // Size of each tile

    // Snake variables
    Tile snakeHead; // Reference to the snake's head tile
    ArrayList<Tile> snakeBody; // List to store the snake's body segments

    // Food variables
    ArrayList<Tile> foodTiles; // List to store food tiles
    Random random; // Random object for generating random numbers

    // Game logic variables
    int velocityX; // Horizontal velocity of the snake
    int velocityY; // Vertical velocity of the snake
    Timer gameLoop; // Timer for the game loop

    boolean gameOver = true; // Flag to indicate if the game is over

    JButton homeButton; // Button to return to home page

    boolean[][] obstacleGrid; // Grid to track obstacles

    int bestScore = 0; // Best score achieved in the game

    // Sound Clips
    Clip eatFoodClip; // Sound clip for eating food
    Clip gameOverClip; // Sound clip for game over
    Clip collisionClip; // Sound clip for collision

    int movesSinceLastFood; // Counter to track moves since last food consumption

    int selectedFood; // Number of food items to be placed on the screen

    // Color variables for head and body
    public Color headColor; // Color of the snake's head
    public Color bodyColor; // Color of the snake's body

    /**
     * Constructor for the SnakeGame class.
     * @param boardWidth The width of the game board.
     * @param boardHeight The height of the game board.
     * @param selectedFood Number of food items to be placed on the screen.
     */
    SnakeGame(int boardWidth, int boardHeight, int selectedFood) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight)); // Set preferred size of panel
        addKeyListener(this); // Add key listener to handle user input
        setFocusable(true); // Set focusable to true to receive key events

        // Initialize sound manager
        soundManager = new SoundManager();

        // In your SnakeGame class constructor
        startTime = TimeUtility.loadStartTime();

        // In your SnakeGame class methods where you need to record or save start time
        TimeUtility.recordStartTime(startTime);
        
        // Initialize obstacle grid
        obstacleGrid = new boolean[boardWidth / tileSize][boardHeight / tileSize];

        // Initialize snake
        snakeHead = new Tile(0, 6); // Initialize snake head with provided coordinates
        snakeBody = new ArrayList<Tile>(); // Initialize snake body

        // Initialize food
        foodTiles = new ArrayList<>(); // Initialize food tiles list
        random = new Random(); // Initialize random object
        // placeFood(selectedFood); // Place food on the game board

        // Initialize game logic variables
        velocityX = 1; // Initial horizontal velocity
        velocityY = 0; // Initial vertical velocity

        // Initialize game loop timer
        gameLoop = new Timer(130, this); // Set timer delay
        gameLoop.start(); // Start the game loop timer

        // Initialize home button
        homeButton = new JButton("Home");
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToHomePage();
            }
        });
        add(homeButton);


        // Initialize moves counter
        movesSinceLastFood = 0;

        // Initialize selectedFood
        this.selectedFood = selectedFood;

        // Initialize head and body colors
        headColor = new Color(0, 250, 0); // Default head color
        bodyColor = new Color(0, 150, 0); // Default body color
    }

    /**
     * Method to return to the home page.
     */
    private void returnToHomePage() {
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        currentFrame.dispose();
        new App(); // Create a new instance of the home page
    }

    // Use sound methods where needed
    private void playEatFoodSound() {
        soundManager.playEatFoodClip();
    }

    private void playGameOverSound() {
        soundManager.playGameOverClip();
    }

    private void playCollisionSound() {
        soundManager.playCollisionClip();
    }
    // Method to record the end time of the gameplay
    private void recordEndTime() {
        endTime = LocalDateTime.now();
    }

    // Method to calculate gameplay duration
    private Duration calculateGameplayDuration() {
        return Duration.between(startTime, endTime);
    }

    // Method to save gameplay time into a text file
    private void saveGameplayTime(Duration duration) {
        TimeUtility.saveGameplayTime(duration);
    }

    /**
     * Draw method to draw components on the panel.
     * @param g Graphics object to draw on.
     */
    public void draw(Graphics g) {
        // Define RGB colors for white and black squares
        Image whiteSquareImg = Toolkit.getDefaultToolkit().getImage("plankDark.png");
        Image blackSquareImg = Toolkit.getDefaultToolkit().getImage("plankGray.png");
    
    // Draw chessboard-like background with images
    for (int row = 0; row < boardHeight / tileSize; row++) {
        for (int col = 0; col < boardWidth / tileSize; col++) {
            if ((row + col) % 2 == 0) {
                // Draw white square
                g.drawImage(whiteSquareImg, col * tileSize, row * tileSize, tileSize, tileSize, null);
            } else {
                // Draw black square
                g.drawImage(blackSquareImg, col * tileSize, row * tileSize, tileSize, tileSize, null);
            }
        }
    }
    
        // Draw grid lines
        for (int i = 0; i < boardWidth / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }
    
        // Draw obstacles
        ObstacleDrawer.drawObstacles(g, tileSize, obstacleGrid);
        // Draw foods using FoodDrawer class
        FoodDrawer.drawFoods(g, foodTiles, tileSize);
        // Draw the snake
        SnakeDrawer.drawSnake(g, tileSize, snakeHead, snakeBody, headColor, bodyColor, velocityX, velocityY);
        // Draw GameOver and Pause
        GameRenderer.drawGameOverMessage(g, boardWidth, boardHeight, !gameLoop.isRunning() && !gameOver);
        GameRenderer.drawScore(g, boardWidth, boardHeight, tileSize, snakeBody.size(), bestScore, gameOver);
    }
    
    
    // Override paintComponent method to draw components
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

// Method to place food on the game board
public void placeFood(int selectedFood) {
    LocalDateTime currentTime = LocalDateTime.now(); // Get current time

    // Iterate through existing food tiles
    for (Tile foodTile : foodTiles) {
        // Check if it's a yellow apple and its visibility duration has passed
        if (foodTile.isYellowApple && Duration.between(foodTile.creationTime, currentTime).getSeconds() >= 6) {
            // Change color to red and set points to 1
            foodTile.isYellowApple = false;
            foodTile.creationTime = currentTime; // Update creation time
        }

        // Check if it's a purple apple and its visibility duration has passed
        if (foodTile.isPurpleApple && Duration.between(foodTile.creationTime, currentTime).getSeconds() >= 6) {
            // Change color to red and set points to 1
            foodTile.isPurpleApple = false;
            foodTile.creationTime = currentTime; // Update creation time
        }
    }

    // Generate new food tiles
    while (foodTiles.size() < selectedFood) { // Ensure maximum of selectedFood food items on the screen
        do {
            // Generate random position for the new food tile
            int foodX = random.nextInt(boardWidth / tileSize);
            int foodY = random.nextInt(boardHeight / tileSize);

            // Check if the position is not occupied by the snake or inside an obstacle
            boolean foodOccupied = false;
            for (Tile snakePart : snakeBody) {
                if (snakePart.x == foodX && snakePart.y == foodY) {
                    foodOccupied = true;
                    break;
                }
            }

            if (foodOccupied || (snakeHead.x == foodX && snakeHead.y == foodY) || obstacleGrid[foodX][foodY]) {
                // Food position is occupied, generate new position
                continue;
            }

            // Food position is valid, add the food tile to the list
            Tile foodTile = new Tile(foodX, foodY);

            // Check if the snake's body has at least one segment
            if (snakeBody.size() >= 1) {
                int randomNum = random.nextInt(20); // Random number between 0 and 19
                if (randomNum < 2) {
                    // 1 in 20 chance for a special apple
                    // Add properties for the special apple as needed
                    foodTile.isSpecialApple = true; // Mark the food tile as a special apple
                    foodTile.creationTime = currentTime; // Record creation time
                } else if (randomNum < 4) {
                    // 1 in 20 chance for a yellow apple (excluding the 1 in 20 for special apple)
                    foodTile.isYellowApple = true; // Mark the food tile as a fully yellow apple
                    foodTile.creationTime = currentTime; // Record creation time

                    // Set up a timer to toggle the color of the yellow apple between red and yellow
                    Timer yellowAppleTimer = new Timer(500, new ActionListener() {
                        boolean isRed = true;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (isRed) {
                                foodTile.isYellowApple = false; // Change color to red
                            } else {
                                foodTile.isYellowApple = true; // Change color to yellow
                            }
                            isRed = !isRed; // Toggle color
                        }
                    });
                    yellowAppleTimer.setInitialDelay(3000); // Start toggling after 3 seconds
                    yellowAppleTimer.setRepeats(true); // Repeat the toggle
                    yellowAppleTimer.start(); // Start the timer
                } else if (randomNum < 5) {
                    // 1 in 20 chance for a purple apple (excluding the 1 in 20 for special and yellow apples)
                    foodTile.isPurpleApple = true; // Mark the food tile as a purple apple
                    foodTile.creationTime = currentTime; // Record creation time

                    // Set up a timer to toggle the color of the purple apple between red and purple
                    Timer purpleAppleTimer = new Timer(500, new ActionListener() {
                        boolean isRed = true;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (isRed) {
                                foodTile.isPurpleApple = false; // Change color to red
                            } else {
                                foodTile.isPurpleApple = true; // Change color to purple
                            }
                            isRed = !isRed; // Toggle color
                        }
                    });
                    purpleAppleTimer.setInitialDelay(3000); // Start toggling after 3 seconds
                    purpleAppleTimer.setRepeats(true); // Repeat the toggle
                    purpleAppleTimer.start(); // Start the timer
                }
            }

            // Regular apple will be generated if neither special, purple, nor yellow
            foodTiles.add(foodTile);    
            break;
        } while (true);
    }
}


// Method to move the snake
public void move() {
    // Eat food
    boolean foodEaten = FoodManager.eatFood(snakeHead, foodTiles, snakeBody);
    
    // Play eat food sound if food is eaten
    if (foodEaten) {
        playEatFoodSound();
    }
    
    // Move snake body
    for (int i = snakeBody.size() - 1; i >= 0; i--) {
        Tile snakePart = snakeBody.get(i);
        if (i == 0) { // right before the head
            snakePart.x = snakeHead.x;
            snakePart.y = snakeHead.y;
        } else {
            Tile prevSnakePart = snakeBody.get(i - 1);
            snakePart.x = prevSnakePart.x;
            snakePart.y = prevSnakePart.y;
        }
    }

    // Calculate new head position
    int newHeadX = snakeHead.x + velocityX;
    int newHeadY = snakeHead.y + velocityY;

    // Check if the new head position is out of bounds
    if (newHeadX < 0)
        newHeadX = boardWidth / tileSize - 1;
    else if (newHeadX >= boardWidth / tileSize)
        newHeadX = 0;
    if (newHeadY < 0)
        newHeadY = boardHeight / tileSize - 1;
    else if (newHeadY >= boardHeight / tileSize)
        newHeadY = 0;

    // Check if the new head position is hitting an obstacle
    if (obstacleGrid[newHeadX][newHeadY]) {
        // Game over if hitting an obstacle
        gameOver = true;
        if (collision(snakeHead, foodTiles.get(0))) {
            playEatFoodSound(); // Call playEatFoodSound() if the snake hits an obstacle but also eats food
        } else {
            playCollisionSound(); // Call playCollisionSound() if the snake hits an obstacle
            playGameOverSound(); // Call playGameOverSound() if the snake hits an obstacle
        }
        return;
    }

    // Move the snake head
    snakeHead.x = newHeadX;
    snakeHead.y = newHeadY;

    // Game over conditions
    for (int i = 0; i < snakeBody.size(); i++) {
        Tile snakePart = snakeBody.get(i);

        // Collide with snake head
        if (collision(snakeHead, snakePart)) {
            gameOver = true;
            playCollisionSound(); // Call playCollisionSound() if the snake collides with itself
            playGameOverSound(); // Call playGameOverSound() if the snake collides with itself
        }
    }

    // Increment moves counter
    movesSinceLastFood++;

    // Check if it's time to generate new food items
    if (movesSinceLastFood >= 5) {
        placeFood(selectedFood); // Generate new food items
        movesSinceLastFood = 0; // Reset moves counter
    }
}

private int moveSpecialAppleCounter = 0;

public void moveSpecialApple() {
    moveSpecialAppleCounter++; // Increment the movement counter

    // Check if the counter reaches 2 (indicating 2 game ticks)
    if (moveSpecialAppleCounter >= 2) {
        // Reset the counter
        moveSpecialAppleCounter = 0;

        // Iterate through each special apple in the foodTiles list
        for (Tile specialApple : foodTiles) {
            if (specialApple.isSpecialApple) {
                // Calculate the direction for the special apple to move away from the snake's head
                int dx = specialApple.x - snakeHead.x;
                int dy = specialApple.y - snakeHead.y;

                // Determine the new position for the special apple
                int newX = specialApple.x;
                int newY = specialApple.y;

                // If not stuck, prioritize moving towards an open direction
                if (dx != 0) {
                    int newDx = (dx > 0) ? 1 : -1;
                    if (isValidPosition(newX + newDx, newY)) {
                        newX += newDx;
                    }
                }

                if (dy != 0) {
                    int newDy = (dy > 0) ? 1 : -1;
                    if (isValidPosition(newX, newY + newDy)) {
                        newY += newDy;
                    }
                }

                // If both directions are blocked, randomly choose a valid direction to move
                if (newX == specialApple.x && newY == specialApple.y) {
                    ArrayList<Point> validDirections = new ArrayList<>();
                    if (isValidPosition(newX + 1, newY)) {
                        validDirections.add(new Point(1, 0));
                    }
                    if (isValidPosition(newX - 1, newY)) {
                        validDirections.add(new Point(-1, 0));
                    }
                    if (isValidPosition(newX, newY + 1)) {
                        validDirections.add(new Point(0, 1));
                    }
                    if (isValidPosition(newX, newY - 1)) {
                        validDirections.add(new Point(0, -1));
                    }
                    
                    if (!validDirections.isEmpty()) {
                        Point randomDirection = validDirections.get(random.nextInt(validDirections.size()));
                        newX += randomDirection.x;
                        newY += randomDirection.y;
                    }
                }

                // Update the position of the special apple
                specialApple.x = newX;
                specialApple.y = newY;
            }
        }
    }
}



// Method to check if a position is valid (not colliding with snake body, hitting game boundaries, containing an apple, or hitting obstacles)
private boolean isValidPosition(int x, int y) {
    // Check if the position is within the game boundaries
    if (x < 0 || x >= boardWidth / tileSize || y < 0 || y >= boardHeight / tileSize) {
        return false; // Out of bounds
    }
    
    // Check if the position collides with the snake body
    for (Tile snakePart : snakeBody) {
        if (snakePart.x == x && snakePart.y == y) {
            return false; // Colliding with snake body
        }
    }
    
    // Check if the position contains an apple
    for (Tile foodTile : foodTiles) {
        if ((foodTile.isYellowApple || foodTile.isPurpleApple || foodTile.isSpecialApple) && foodTile.x == x && foodTile.y == y) {
            return false; // An apple is found at the specified position
        }
    }
    
    // Check if the position hits an obstacle
    if (obstacleGrid[x][y]) {
        return false; // Hitting an obstacle
    }
    
    return true; // The position is valid
}




    // Method to check collision between two tiles
    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        moveSpecialApple();
        repaint();
        if (gameOver) {
            playGameOverSound(); // Call playGameOverSound() when the game is over
            gameLoop.stop();
            updateBestScore();
            recordEndTime();
            Duration gameplayDuration = calculateGameplayDuration();
            saveGameplayTime(gameplayDuration);
        }
    }
    
    

    // Method to determine if the snake can move based on a cooldown
    private long lastKeyPressTime = 0;
    private static final long MOVEMENT_COOLDOWN = 130;

    private boolean canMove() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastKeyPressTime) >= MOVEMENT_COOLDOWN;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameOver) {
                resetGame(); // Reset the game
            } else {
                if (gameLoop.isRunning()) {
                    gameLoop.stop(); // Pause the game loop
                } else {
                    gameLoop.start(); // Resume the game loop
                }
                repaint(); // Repaint the panel
            }
        } else if (!gameOver && canMove()) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                if (velocityY != 1) {
                    velocityX = 0;
                    velocityY = -1;
                    lastKeyPressTime = System.currentTimeMillis();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                if (velocityY != -1) {
                    velocityX = 0;
                    velocityY = 1;
                    lastKeyPressTime = System.currentTimeMillis();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                if (velocityX != 1) {
                    velocityX = -1;
                    velocityY = 0;
                    lastKeyPressTime = System.currentTimeMillis();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                if (velocityX != -1) {
                    velocityX = 1;
                    velocityY = 0;
                    lastKeyPressTime = System.currentTimeMillis();
                }
            }
        }
    }
    

    // Method to reset the game
    private void resetGame() {
        ResetGameHandler.resetGame(this);
    }


    // Method to update the best score
    protected void updateBestScore() {
        if (snakeBody.size() > bestScore) {
            bestScore = snakeBody.size(); // Update best score if current score is higher
        }
        // Record end time and save gameplay time
        recordEndTime();
        Duration gameplayDuration = calculateGameplayDuration();
        saveGameplayTime(gameplayDuration);
    }

    // Method to set new head color
    public void setHeadColor(Color color) {
        headColor = color;
    }

    // Method to set new body color
    public void setBodyColor(Color color) {
        bodyColor = color;
    }

    // Unused key event methods
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
