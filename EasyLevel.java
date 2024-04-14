import java.io.*;

/**
 * Represents the Easy level of the Snake game.
 */
public class EasyLevel extends SnakeGame {

    /**
     * Constructor for the Easy level.
     *
     * @param boardWidth     The width of the game board.
     * @param boardHeight    The height of the game board.
     * @param selectedFood   The selected amount of food.
     */
    EasyLevel(int boardWidth, int boardHeight, int selectedFood) {
        super(boardWidth, boardHeight, selectedFood);

        loadBestScore();
        addObstacles();
        placeFood(selectedFood);
    }

    /**
     * Loads the best score for the Easy level from a file.
     */
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

    /**
     * Updates the best score for the Easy level and saves it to a file.
     */
    @Override
    public void updateBestScore() {
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
    
    /**
     * Adds obstacles to the Easy level.
     */
    private void addObstacles() {
        // Add obstacles logic goes here
    }
}
