import java.awt.*;

public class ObstacleDrawer {

    public static void drawObstacles(Graphics g, int tileSize, boolean[][] obstacleGrid) {
        // Load Minecraft bedrock block texture (replace "minecraft_bedrock_texture.png" with your texture file)
        Image bedrockTexture = Toolkit.getDefaultToolkit().getImage("image.png");

        // Draw obstacles
        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[0].length; j++) {
                if (obstacleGrid[i][j]) {
                    // Draw bedrock block texture at obstacle position
                    g.drawImage(bedrockTexture, i * tileSize, j * tileSize, tileSize, tileSize, null);
                }
            }
        }
    }
}
