import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener {
    JComboBox<String> levelSelection;
    JComboBox<Integer> foodSelection;
    JComboBox<String> colorSelection; // Added JComboBox for snake color selection
    JButton startButton;

    public App() {
        setTitle("Snake Game");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.gray);

        String[] levels = {"Easy", "Medium", "Hard", "Insane"};
        levelSelection = new JComboBox<>(levels);

        Integer[] foodOptions = {1, 3, 5};
        foodSelection = new JComboBox<>(foodOptions);

        // Create JComboBox for snake color selection
        String[] snakeColors = {"Green", "Blue", "Orange"}; // Add more colors as needed
        colorSelection = new JComboBox<>(snakeColors);

        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Select Level:"), gbc);

        gbc.gridx = 1;
        add(levelSelection, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Select Food Amount:"), gbc);

        gbc.gridx = 1;
        add(foodSelection, gbc);

        // Add snake color selection to the frame
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Select Snake Color:"), gbc);

        gbc.gridx = 1;
        add(colorSelection, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(startButton, gbc);

        setVisible(true);
    }

    private void startGame(String selectedLevel, int selectedFood, Color snakeColor) {
        SnakeGame snakeGame = new SnakeGame(600, 600, selectedFood);

        // Set snake color based on the selected color
        snakeGame.setHeadColor(snakeColor);
        snakeGame.setBodyColor(snakeColor.darker()); // Set body color to a darker shade of the selected color

        launchSnakeGame(snakeGame, selectedFood);
    }

    private void launchSnakeGame(SnakeGame game, int selectedFood) {
        game.placeFood(selectedFood);
        JFrame frame = new JFrame("Snake Game");

        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("start")) {
            String selectedLevel = (String) levelSelection.getSelectedItem();
            int selectedFood = (int) foodSelection.getSelectedItem();

            // Get the selected snake color
            String selectedColor = (String) colorSelection.getSelectedItem();
            Color snakeColor = null;

            // Set snake color based on the selected color
            switch (selectedColor) {
                case "Green":
                    snakeColor = Color.GREEN;
                    break;
                case "Blue":
                    snakeColor = Color.BLUE;
                    break;
                case "Orange":
                    snakeColor = Color.ORANGE;
                    break;
                // Add more cases for additional colors
            }

            startGame(selectedLevel, selectedFood, snakeColor);
        }
    }

    public static void main(String[] args) {
        new App();
    }
}
