import java.io.*;

/**
 * Represents a hard level in the SnakeGame.
 */
public class HardLevel extends SnakeGame {

    /**
     * Constructor for HardLevel.
     *
     * @param boardWidth    The width of the game board.
     * @param boardHeight   The height of the game board.
     * @param selectedFood  The number of food items to place on the board.
     */
    HardLevel(int boardWidth, int boardHeight, int selectedFood) {
        super(boardWidth, boardHeight, selectedFood);

        loadBestScore();
        addObstacles();
        placeFood(selectedFood);
    }

    /**
     * Loads the best score from the file.
     */
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

    /**
     * Updates the best score in the file.
     */
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

    /**
     * Adds obstacles to the game board.
     */
    private void addObstacles() {
        obstacleGrid[0][0] = true;
        obstacleGrid[1][0] = true;
        obstacleGrid[2][0] = true;
        obstacleGrid[0][1] = true;
        obstacleGrid[0][2] = true;

        obstacleGrid[5][4] = true;
        obstacleGrid[5][5] = true;
        obstacleGrid[4][5] = true;

        obstacleGrid[0][12] = true;
        obstacleGrid[0][13] = true;
        obstacleGrid[0][14] = true;
        obstacleGrid[1][14] = true;
        obstacleGrid[2][14] = true;

        obstacleGrid[9][4] = true;
        obstacleGrid[9][5] = true;
        obstacleGrid[10][5] = true;

        obstacleGrid[12][14] = true;
        obstacleGrid[13][14] = true;
        obstacleGrid[14][14] = true;
        obstacleGrid[14][12] = true;
        obstacleGrid[14][13] = true;

        obstacleGrid[9][10] = true;
        obstacleGrid[9][9] = true;
        obstacleGrid[10][9] = true;

        obstacleGrid[14][0] = true;
        obstacleGrid[14][1] = true;
        obstacleGrid[14][2] = true;
        obstacleGrid[13][0] = true;
        obstacleGrid[12][0] = true;

        obstacleGrid[5][10] = true;
        obstacleGrid[5][9] = true;
        obstacleGrid[4][9] = true;
    }
}
