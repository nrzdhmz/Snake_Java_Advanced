import java.awt.Color;
import java.io.*;

public class InsaneLevel extends SnakeGame {

    InsaneLevel(int boardWidth, int boardHeight, int selectedFood) {
        super(boardWidth, boardHeight, selectedFood);  // Call the constructor of the parent class
        
        // Load best score
        loadBestScore();
        // Add obstacles to the obstacleGrid
        addObstacles();
        // Place food after adding obstacles
        placeFood(selectedFood);
    }

    // Method to load best score from file
    private void loadBestScore() {
        try {
            File file = new File("insane_level_best_score.txt");
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                if (line != null) {
                    bestScore = Integer.parseInt(line);
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to update best score in file
    @Override
    public void updateBestScore() { // Corrected method signature
        super.updateBestScore();
        try {
            File file = new File("insane_level_best_score.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(bestScore));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
  private void addObstacles() {
      // Add obstacles in your desired pattern
      obstacleGrid[6][0] = true;
      obstacleGrid[6][1] = true;
      obstacleGrid[6][2] = true;
      obstacleGrid[6][3] = true;
      obstacleGrid[6][4] = true;
      obstacleGrid[6][5] = true;
      obstacleGrid[6][6] = true;
      obstacleGrid[6][7] = true;
      obstacleGrid[6][8] = true;
      obstacleGrid[6][9] = true;
      obstacleGrid[6][10] = true;
      obstacleGrid[6][11] = true;
      obstacleGrid[6][12] = true;

      obstacleGrid[0][6] = true;
      obstacleGrid[1][6] = true;
      obstacleGrid[2][6] = true;
      obstacleGrid[3][6] = true;
      obstacleGrid[4][6] = true;
      obstacleGrid[5][6] = true;
      obstacleGrid[7][6] = true;
      obstacleGrid[8][6] = true;
      obstacleGrid[9][6] = true;
      obstacleGrid[10][6] = true;
      obstacleGrid[11][6] = true;
      obstacleGrid[12][6] = true;

          // Call placeFood() after adding obstacles
          placeFood(selectedFood);  
        }
}
