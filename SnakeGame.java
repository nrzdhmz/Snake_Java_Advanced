import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.util.Scanner;

// SnakeGame class
public class SnakeGame extends JPanel implements ActionListener, KeyListener {
// Tile inner class to represent a single tile in the game board
public class Tile {
    int x; // X coordinate of the tile
    int y; // Y coordinate of the tile
    boolean isYellowApple; // Flag to indicate if the tile represents a fully yellow apple
    boolean isPurpleApple; // Flag to indicate if the tile represents a purple apple
    LocalDateTime creationTime; // Timestamp indicating the creation time of the food

    // Tile constructor
    Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.creationTime = LocalDateTime.now(); // Record creation time
    }
}




    // Instance variables to store start and end times
    private LocalDateTime startTime;
    private LocalDateTime endTime;

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

    // SnakeGame constructor
    SnakeGame(int boardWidth, int boardHeight, int selectedFood) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight)); // Set preferred size of panel
        setBackground(Color.black); // Set background color
        addKeyListener(this); // Add key listener to handle user input
        setFocusable(true); // Set focusable to true to receive key events

        // Initialize start time
        loadStartTime(); // Load start time if available
        
        // Initialize obstacle grid
        obstacleGrid = new boolean[boardWidth / tileSize][boardHeight / tileSize];

        // Initialize snake
        snakeHead = new Tile(0, 6); // Initialize snake head with provided coordinates
        snakeBody = new ArrayList<Tile>(); // Initialize snake body

        // Initialize food
        foodTiles = new ArrayList<>(); // Initialize food tiles list
        random = new Random(); // Initialize random object
        placeFood(selectedFood); // Place food on the game board

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

        // Load sound clips
        loadSoundClips();

        // Initialize moves counter
        movesSinceLastFood = 0;

        // Initialize selectedFood
        this.selectedFood = selectedFood;

        // Initialize head and body colors
        headColor = new Color(0, 250, 0); // Default head color
        bodyColor = new Color(0, 150, 0); // Default body color
    }

    // Method to load the start time from a file
    private void loadStartTime() {
        File file = new File("start_time.txt");
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                String startTimeString = scanner.nextLine();
                startTime = LocalDateTime.parse(startTimeString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            recordStartTime(); // Record start time if file doesn't exist
        }
    }

    // Method to record the start time of the gameplay
    private void recordStartTime() {
        startTime = LocalDateTime.now();
        saveStartTime(startTime); // Save start time to file
    }

    // Method to save the start time into a text file
    private void saveStartTime(LocalDateTime startTime) {
        try (FileWriter writer = new FileWriter("start_time.txt")) {
            writer.write(startTime.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try (FileWriter writer = new FileWriter("gameplay_time.txt")) {
            writer.write("Working on this project for " + duration.toDays() + " days");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to return to the home page
    private void returnToHomePage() {
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        currentFrame.dispose();
        new App(); // Create a new instance of the home page
    }

    // Method to load sound clips
    private void loadSoundClips() {
        try {
            // Load eat food sound
            eatFoodClip = AudioSystem.getClip();
            AudioInputStream eatFoodInputStream = AudioSystem.getAudioInputStream(new File("05Newfood.wav"));
            eatFoodClip.open(eatFoodInputStream);

            // Load game over sound
            gameOverClip = AudioSystem.getClip();
            AudioInputStream gameOverInputStream = AudioSystem.getAudioInputStream(new File("02.Gameover.wav"));
            gameOverClip.open(gameOverInputStream);

            // Load collision sound
            collisionClip = AudioSystem.getClip();
            AudioInputStream collisionInputStream = AudioSystem.getAudioInputStream(new File("01Collide.wav"));
            collisionClip.open(collisionInputStream);
        } catch (Exception ex) {
            System.out.println("Error loading sound clips: " + ex.getMessage());
        }
    }

    // Method to play sound clip
    private void playSound(Clip clip) {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }

    // Method to draw game components
    public void draw(Graphics g) {
        // Draw grid lines
        for (int i = 0; i < boardWidth / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }
    
        g.setColor(Color.gray);
        // Draw obstacles
        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[0].length; j++) {
                if (obstacleGrid[i][j]) {
                    g.fill3DRect(i * tileSize, j * tileSize, tileSize, tileSize, true);
                }
            }
        }
    
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

    // Draw snake body
    for (int i = 0; i < snakeBody.size(); i++) {
        Tile snakePart = snakeBody.get(i);
        Color segmentColor = calculateIntermediateColor(headColor, bodyColor, i, snakeBody.size());
        g.setColor(segmentColor);
        g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
    }

        // Draw snake head
        g.setColor(headColor);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // Draw eyes
        g.setColor(Color.black);
        int eyeSize = tileSize / 5; // Size of the eyes
        int eyeDistance = tileSize / 6; // Distance of the eyes from the edges

        // Calculate eye positions based on the direction of movement
        if (velocityX > 0) { // Moving right
            // Draw eyes on the right side of the head
            int rightEyeX = snakeHead.x * tileSize + tileSize - eyeDistance - eyeSize;
            int leftEyeX = rightEyeX - eyeDistance - eyeSize; // Calculate position for the left eye
            int eyeCenterY = snakeHead.y * tileSize + tileSize / 2; // Y coordinate of the eye center
            g.fillOval(rightEyeX, eyeCenterY - eyeSize / 2, eyeSize, eyeSize);
            g.fillOval(leftEyeX, eyeCenterY - eyeSize / 2, eyeSize, eyeSize);
        } else if (velocityX < 0) { // Moving left
            // Draw eyes on the left side of the head
            int leftEyeX = snakeHead.x * tileSize + eyeDistance;
            int rightEyeX = leftEyeX + eyeDistance + eyeSize; // Calculate position for the right eye
            int eyeCenterY = snakeHead.y * tileSize + tileSize / 2; // Y coordinate of the eye center
            g.fillOval(leftEyeX, eyeCenterY - eyeSize / 2, eyeSize, eyeSize);
            g.fillOval(rightEyeX, eyeCenterY - eyeSize / 2, eyeSize, eyeSize);
        } else { // Moving up or down
            // Draw eyes at the center of the head vertically
            int eyeCenterX = snakeHead.x * tileSize + tileSize / 2; // X coordinate of the eye center
            int eyeCenterY = snakeHead.y * tileSize + tileSize / 2; // Y coordinate of the eye center
            int leftEyeX = eyeCenterX - eyeDistance - eyeSize / 2;
            int rightEyeX = eyeCenterX + eyeDistance - eyeSize / 2;
            g.fillOval(leftEyeX, eyeCenterY - eyeSize / 2, eyeSize, eyeSize);
            g.fillOval(rightEyeX, eyeCenterY - eyeSize / 2, eyeSize, eyeSize);
        }

        // Game over message
        if (!gameLoop.isRunning() && !gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.red);
            FontMetrics fm = g.getFontMetrics();
            int xPaused = (boardWidth - fm.stringWidth("Game Paused")) / 2;
            int yPaused = boardHeight / 2 - 30;
            g.drawString("Game Paused", xPaused, yPaused);

            int xPressSpace = (boardWidth - fm.stringWidth("Press Space")) / 2;
            int yPressSpace = yPaused + 40;
            g.drawString("Press Space", xPressSpace, yPressSpace);
        }

        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            // Game over panel
            int panelWidth = 300;
            int panelHeight = 200;
            int panelX = (boardWidth - panelWidth) / 2;
            int panelY = (boardHeight - panelHeight) / 2;

            // Draw light gray panel
            g.setColor(Color.gray);
            g.fillRect(panelX, panelY, panelWidth, panelHeight);

            Font gameoverFont = new Font("Arial", Font.BOLD, 40);
            g.setFont(gameoverFont);
            g.setColor(Color.red);
            // Calculate the position to center the text
            FontMetrics fm = g.getFontMetrics();
            int xGameOver = (boardWidth - fm.stringWidth("Press Space")) / 2;
            int yGameOver = (boardHeight / 2) - 30; // Position above the center
            g.drawString("Game Over", xGameOver, yGameOver);

            // Best Score
            g.setColor(Color.green);
            String bestScoreString = "Best Score: " + bestScore;
            int xBestScore = (boardWidth - fm.stringWidth(bestScoreString)) / 2;
            int yBestScore = boardHeight / 2 + 10; // Center of the frame
            g.drawString(bestScoreString, xBestScore, yBestScore);

            // Score
            String scoreString = "Score: " + snakeBody.size();
            int xScore = (boardWidth - fm.stringWidth(scoreString)) / 2;
            int yScore = (boardHeight / 2) + 50; // Position below the center
            g.drawString(scoreString, xScore, yScore);
        } else {
            // Score
            g.setColor(Color.green);
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 26, tileSize);
            // Best Score
            g.setColor(Color.green);
            g.drawString("Best Score: " + bestScore, tileSize - 26, tileSize - 20);
        }
    }

    

    // Override paintComponent method to draw components
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Method to calculate intermediate color between head and body
    private Color calculateIntermediateColor(Color headColor, Color bodyColor, int bodyIndex, int totalSegments) {
        float ratio = (float) (totalSegments - bodyIndex) / totalSegments;
        int red = (int) (headColor.getRed() * ratio + bodyColor.getRed() * (1 - ratio));
        int green = (int) (headColor.getGreen() * ratio + bodyColor.getGreen() * (1 - ratio));
        int blue = (int) (headColor.getBlue() * ratio + bodyColor.getBlue() * (1 - ratio));
        return new Color(red, green, blue);
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
                    int randomNum = random.nextInt(20); // Random number between 0 and 29
                    if (randomNum == 0) {
                        // 1 in 30 chance for a purple apple
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
                    } else if (randomNum < 2) {
                        // 1 in 20 chance for a yellow apple (excluding the 1 in 30 for purple apple)
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
                    }
                }

                // Regular apple will be generated if neither purple nor yellow
                foodTiles.add(foodTile);
                break;
            } while (true);
        }
    }





// Method to move the snake
public void move() {
    // Eat food
    for (Tile foodTile : foodTiles) {
        if (collision(snakeHead, foodTile)) {
            if (foodTile.isYellowApple) {
                // Eat yellow apple
                for (int i = 0; i < 5; i++) {
                    snakeBody.add(new Tile(snakeBody.get(snakeBody.size() - 1).x, snakeBody.get(snakeBody.size() - 1).y)); // Add new body segment 5 times
                }
            } else if (foodTile.isPurpleApple) {
                // Determine luck for purple apple
             boolean gainPoints = Math.random() < 0.40; // 40% chance to gain points
            if (gainPoints) {
                // Add 10 points for eating a purple apple
                for (int i = 0; i < 10; i++) {
                    snakeBody.add(new Tile(snakeBody.get(snakeBody.size() - 1).x, snakeBody.get(snakeBody.size() - 1).y)); // Add new body segment 10 times
                }
            } else {
                if (snakeBody.size() > 10) {
                    // Remove 10 points if body size is greater than 10
                    for (int i = 0; i < 10; i++) {
                        snakeBody.remove(snakeBody.size() - 1); // Remove last body segment 10 times
                    }
                } else {
                    // If body size is less than or equal to 10, reset points to 1
                    snakeBody.subList(1, snakeBody.size()).clear();
                }
            }
            } else {
                // Eat regular apple
                snakeBody.add(new Tile(foodTile.x, foodTile.y)); // Add new body segment
            }
            foodTiles.remove(foodTile); // Remove food tile
            placeFood(selectedFood); // Generate new food if one is consumed
            playSound(eatFoodClip); // Play sound when snake eats food
            break; // Exit the loop after eating one food item
        }   
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
            playSound(eatFoodClip);
        } else {
            playSound(collisionClip);
            playSound(gameOverClip);
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
            playSound(collisionClip);
            playSound(gameOverClip);
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


    // Method to check collision between two tiles
    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // Move the snake
        repaint(); // Repaint the panel
        if (gameOver) {
            gameLoop.stop(); // Stop the game loop
            updateBestScore(); // Update the best score
            recordEndTime(); // Record end time
            Duration gameplayDuration = calculateGameplayDuration(); // Calculate gameplay duration
            saveGameplayTime(gameplayDuration); // Save gameplay time
            // Handle game over
        }
    }
    

    // Method to determine if the snake can move based on a cooldown
    private long lastKeyPressTime = 0;
    private static final long MOVEMENT_COOLDOWN = 130;

    private boolean canMove() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastKeyPressTime) >= MOVEMENT_COOLDOWN;
    }

    // Key pressed event handler
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
            if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
                velocityX = 0;
                velocityY = -1;
                lastKeyPressTime = System.currentTimeMillis();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
                velocityX = 0;
                velocityY = 1;
                lastKeyPressTime = System.currentTimeMillis();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
                velocityX = -1;
                velocityY = 0;
                lastKeyPressTime = System.currentTimeMillis();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
                velocityX = 1;
                velocityY = 0;
                lastKeyPressTime = System.currentTimeMillis();
            }
        }
    }

    // Method to reset the game
    private void resetGame() {
        snakeHead = new Tile(0, 6); // Reset snake head position
        snakeBody.clear(); // Clear snake body
        foodTiles.clear(); // Clear food tiles
        placeFood(selectedFood); // Place food on the board
        velocityX = 1; // Reset horizontal velocity
        velocityY = 0; // Reset vertical velocity
        gameOver = false; // Set game over flag to false
        gameLoop.restart(); // Restart the game loop
        movesSinceLastFood = 0; // Reset moves counter
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