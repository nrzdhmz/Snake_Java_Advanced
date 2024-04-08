import java.awt.*;

public class ObstacleDrawer {

    public static void drawObstacles(Graphics g, int tileSize, boolean[][] obstacleGrid) {
        // Set color for obstacles
        g.setColor(Color.gray);

        // Draw obstacles
        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[0].length; j++) {
                if (obstacleGrid[i][j]) {
                    g.fill3DRect(i * tileSize, j * tileSize, tileSize, tileSize, true);
                }
            }
        }
    }
}
