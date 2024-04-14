import java.io.*;

/**
 * Represents an insane level in the SnakeGame.
 */
public class InsaneLevel extends SnakeGame {

    /**
     * Constructor for InsaneLevel.
     *
     * @param boardWidth    The width of the game board.
     * @param boardHeight   The height of the game board.
     * @param selectedFood  The number of food items to place on the board.
     */
    InsaneLevel(int boardWidth, int boardHeight, int selectedFood) {
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

    /**
     * Updates the best score in the file.
     */
    @Override
    public void updateBestScore() {
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

    /**
     * Adds obstacles to the game board.
     */
    private void addObstacles() {
        obstacleGrid[7][0] = true;
        obstacleGrid[7][1] = true;
        obstacleGrid[7][2] = true;
        obstacleGrid[7][3] = true;
        obstacleGrid[7][4] = true;
        obstacleGrid[7][5] = true;
        obstacleGrid[7][6] = true;
        obstacleGrid[7][8] = true;
        obstacleGrid[7][9] = true;
        obstacleGrid[7][10] = true;
        obstacleGrid[7][11] = true;
        obstacleGrid[7][12] = true;
        obstacleGrid[7][13] = true;
        obstacleGrid[7][14] = true;

        obstacleGrid[0][7] = true;
        obstacleGrid[1][7] = true;
        obstacleGrid[2][7] = true;
        obstacleGrid[3][7] = true;
        obstacleGrid[4][7] = true;
        obstacleGrid[5][7] = true;
        obstacleGrid[6][7] = true;
        obstacleGrid[7][7] = true;
        obstacleGrid[8][7] = true;
        obstacleGrid[9][7] = true;
        obstacleGrid[10][7] = true;
        obstacleGrid[11][7] = true;
        obstacleGrid[12][7] = true;
        obstacleGrid[13][7] = true;
        obstacleGrid[14][7] = true;
    }
}
