import java.io.*;

public class EasyLevel extends SnakeGame {

  EasyLevel(int boardWidth, int boardHeight) {
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
            File file = new File("easy_level_best_score.txt");
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
            File file = new File("easy_level_best_score.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(bestScore));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
  private void addObstacles() {
      // Add obstacles in your desired pattern

    // Call placeFood() after adding obstacles
    placeFood();
  }
}
