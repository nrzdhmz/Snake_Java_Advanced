import java.awt.*;

public class GameRenderer {

    public static void drawGameOverMessage(Graphics g, int boardWidth, int boardHeight, boolean gamePaused) {
        if (gamePaused) {
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
    }

    public static void drawScore(Graphics g, int boardWidth, int boardHeight, int tileSize, int snakeBodySize, int bestScore, boolean gameOver) {
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
            String scoreString = "Score: " + snakeBodySize;
            int xScore = (boardWidth - fm.stringWidth(scoreString)) / 2;
            int yScore = (boardHeight / 2) + 50; // Position below the center
            g.drawString(scoreString, xScore, yScore);
        } else {
            // Score
            g.setColor(Color.green);
            g.drawString("Score: " + String.valueOf(snakeBodySize), tileSize - 26, tileSize);
            // Best Score
            g.setColor(Color.green);
            g.drawString("Best Score: " + bestScore, tileSize - 26, tileSize - 20);
        }
    }
}
