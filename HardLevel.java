import java.io.*;

public class HardLevel extends SnakeGame {

    HardLevel(int boardWidth, int boardHeight, int selectedFood) {
        super(boardWidth, boardHeight, selectedFood);

        loadBestScore();
        addObstacles();
        placeFood(selectedFood);
    }

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

    @Override
    public void updateBestScore() { 
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
    obstacleGrid[0][0] = true;
    obstacleGrid[1][0] = true;
    obstacleGrid[2][0] = true;
    obstacleGrid[0][1] = true;
    obstacleGrid[0][2] = true;
    
    obstacleGrid[4][3] = true;
    obstacleGrid[4][4] = true;
    obstacleGrid[3][4] = true;
    
    obstacleGrid[0][11] = true;
    obstacleGrid[0][12] = true;
    obstacleGrid[0][13] = true;
    obstacleGrid[1][13] = true;
    obstacleGrid[2][13] = true;
    
    obstacleGrid[7][3] = true;
    obstacleGrid[7][4] = true;
    obstacleGrid[8][4] = true;
    
    obstacleGrid[11][13] = true;
    obstacleGrid[12][13] = true;
    obstacleGrid[13][13] = true;
    obstacleGrid[13][11] = true;
    obstacleGrid[13][12] = true;
    
    obstacleGrid[7][10] = true;
    obstacleGrid[7][9] = true;
    obstacleGrid[8][9] = true;
    
    obstacleGrid[13][0] = true;
    obstacleGrid[13][1] = true;
    obstacleGrid[13][2] = true;
    obstacleGrid[12][0] = true;
    obstacleGrid[11][0] = true;
    
    obstacleGrid[4][10] = true;
    obstacleGrid[4][9] = true;
    obstacleGrid[3][9] = true;
    
    

        }
}
