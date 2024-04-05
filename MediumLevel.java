import java.io.*;

public class MediumLevel extends SnakeGame {

    MediumLevel(int boardWidth, int boardHeight, int selectedFood) {
        super(boardWidth, boardHeight, selectedFood); 

        loadBestScore();
        addObstacles();
        placeFood(selectedFood); 
    }

    private void loadBestScore() {
        try {
            File file = new File("medium_level_best_score.txt");
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
            File file = new File("medium_level_best_score.txt");
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
        obstacleGrid[3][0] = true;
        obstacleGrid[0][1] = true;
        obstacleGrid[0][2] = true;
        obstacleGrid[0][3] = true;

        obstacleGrid[0][8] = true;
        obstacleGrid[0][9] = true;
        obstacleGrid[0][10] = true;
        obstacleGrid[0][11] = true;
        obstacleGrid[1][11] = true;
        obstacleGrid[2][11] = true;
        obstacleGrid[3][11] = true;

        obstacleGrid[8][11] = true;
        obstacleGrid[9][11] = true;
        obstacleGrid[10][11] = true;
        obstacleGrid[11][11] = true;
        obstacleGrid[11][8] = true;
        obstacleGrid[11][9] = true;
        obstacleGrid[11][10] = true;

        obstacleGrid[11][0] = true;
        obstacleGrid[11][1] = true;
        obstacleGrid[11][2] = true;
        obstacleGrid[11][3] = true;
        obstacleGrid[10][0] = true;
        obstacleGrid[9][0] = true;
        obstacleGrid[8][0] = true;
    }
}
