import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;

// SnakeGame class
public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    // Tile inner class to represent a single tile in the game board
    public class Tile {
        int x; // X coordinate of the tile
        int y; // Y coordinate of the tile

        // Tile constructor
        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // Instance variables
    int boardWidth; // Width of the game board
    int boardHeight; // Height of the game board
    int tileSize = 50; // Size of each tile

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

        // Initialize obstacle grid
        obstacleGrid = new boolean[boardWidth / tileSize][boardHeight / tileSize];

        // Initialize snake
        snakeHead = new Tile(0, 5); // Initialize snake head with provided coordinates
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

        // Draw food
        g.setColor(Color.red);
        for (Tile foodTile : foodTiles) {
            g.fill3DRect(foodTile.x * tileSize, foodTile.y * tileSize, tileSize, tileSize, true);
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
            int xGameOver = (boardWidth - fm.stringWidth("Game Over")) / 2;
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
        while (foodTiles.size() < selectedFood) { // Ensure maximum of selectedFood food items on the screen
            do {
                int foodX = random.nextInt(boardWidth / tileSize);
                int foodY = random.nextInt(boardHeight / tileSize);

                // Check if the food position is not occupied by the snake or inside an obstacle
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
                foodTiles.add(new Tile(foodX, foodY));
                break;
            } while (true);
        }
    }

    // Method to move the snake
    public void move() {
        // Eat food
        for (Tile foodTile : foodTiles) {
            if (collision(snakeHead, foodTile)) {
                snakeBody.add(new Tile(foodTile.x, foodTile.y)); // Add new body segment
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

    // Action performed method for game loop
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // Move the snake
        repaint(); // Repaint the panel
        if (gameOver) {
            gameLoop.stop(); // Stop the game loop
            updateBestScore(); // Update best score
            playSound(gameOverClip); // Play game over sound
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
        snakeHead = new Tile(1, 5); // Reset snake head position
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
