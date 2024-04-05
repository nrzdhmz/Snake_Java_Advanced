import java.io.*;

public class EasyLevel extends SnakeGame {

    EasyLevel(int boardWidth, int boardHeight, int selectedFood) {
        super(boardWidth, boardHeight, selectedFood);

        loadBestScore();
        addObstacles();
        placeFood(selectedFood);
    }

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
    
    private void addObstacles() {
    }
}
