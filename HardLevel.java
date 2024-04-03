import java.io.*;

public class HardLevel extends SnakeGame {

    HardLevel(int boardWidth, int boardHeight) {
        super(boardWidth, boardHeight); // Call the constructor of the parent class

        // Load best score
        loadBestScore();
        // Add obstacles to the obstacleGrid
        addObstacles();
        // Place food after adding obstacles
        placeFood();
    }

    // Method to load best score from file
    private void loadBestScore() {
        try {
            File file = new File("hard_level_best_score.txt");
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
    void updateBestScore() {
        super.updateBestScore();
        try {
            File file = new File("hard_level_best_score.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(bestScore));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
  private void addObstacles() {
      // Add obstacles in your desired pattern
          obstacleGrid[0][0] = true;
          obstacleGrid[1][0] = true;
          obstacleGrid[2][0] = true;
          obstacleGrid[0][1] = true;
          obstacleGrid[0][2] = true;

          obstacleGrid[4][3] = true;
          obstacleGrid[4][4] = true;
          obstacleGrid[3][4] = true;

          obstacleGrid[0][9] = true;
          obstacleGrid[0][10] = true;
          obstacleGrid[0][11] = true;
          obstacleGrid[1][11] = true;
          obstacleGrid[2][11] = true;

          obstacleGrid[7][3] = true;
          obstacleGrid[7][4] = true;
          obstacleGrid[8][4] = true;

          obstacleGrid[9][11] = true;
          obstacleGrid[10][11] = true;
          obstacleGrid[11][11] = true;
          obstacleGrid[11][9] = true;
          obstacleGrid[11][10] = true;

          obstacleGrid[7][8] = true;
          obstacleGrid[7][7] = true;
          obstacleGrid[8][7] = true;

          obstacleGrid[11][0] = true;
          obstacleGrid[11][1] = true;
          obstacleGrid[11][2] = true;
          obstacleGrid[10][0] = true;
          obstacleGrid[9][0] = true;

          obstacleGrid[4][8] = true;
          obstacleGrid[4][7] = true;
          obstacleGrid[3][7] = true;

          // Call placeFood() after adding obstacles
          placeFood();
  }
}
