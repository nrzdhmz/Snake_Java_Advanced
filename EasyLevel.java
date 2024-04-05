import java.awt.Color;
import java.io.*;

public class EasyLevel extends SnakeGame {

    EasyLevel(int boardWidth, int boardHeight, int selectedFood) {
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
    public void updateBestScore() { // Corrected method visibility to match superclass
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
    
    // Method to add obstacles
    private void addObstacles() {
        // Add obstacles in your desired pattern
        placeFood(selectedFood);
    }
}
