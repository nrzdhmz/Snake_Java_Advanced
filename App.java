import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App extends JFrame implements ActionListener {
    // Components for username input, level selection, food amount selection, snake color selection, and start button
    JTextField usernameField;
    JComboBox<String> levelSelection;
    JComboBox<Integer> foodSelection;
    JComboBox<String> colorSelection;
    JButton startButton;
    JLabel gameplayTimeLabel; // Label to display gameplay time

    // Constructor to initialize the application
    public App() {
        setTitle("Snake Game"); // Set the title of the frame
        setSize(600, 600); // Set the size of the frame
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Set the default close operation
        setLocationRelativeTo(null); // Center the frame on the screen
        getContentPane().setBackground(Color.gray); // Set the background color of the content pane

        // Initialize username input field
        usernameField = new JTextField(20);

        // Initialize level selection combo box with options
        String[] levels = {"Easy", "Medium", "Hard", "Insane"};
        levelSelection = new JComboBox<>(levels);

        // Initialize food amount selection combo box with options
        Integer[] foodOptions = {1, 3, 5};
        foodSelection = new JComboBox<>(foodOptions);

        // Initialize snake color selection combo box with options
        String[] snakeColors = {"Green", "Blue", "Orange"};
        colorSelection = new JComboBox<>(snakeColors);

        // Initialize start button
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this); // Add action listener to the start button

        // Initialize gameplay time label
        gameplayTimeLabel = new JLabel();
        gameplayTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gameplayTimeLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        updateGameplayTime(); // Update gameplay time label initially

        // Set layout for the frame
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Add components to the frame with grid bag constraints
        add(new JLabel("Enter Username:"), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Select Level:"), gbc);
        gbc.gridx = 1;
        add(levelSelection, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Select Food Amount:"), gbc);
        gbc.gridx = 1;
        add(foodSelection, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Select Snake Color:"), gbc);
        gbc.gridx = 1;
        add(colorSelection, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(startButton, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(gameplayTimeLabel, gbc);

        setVisible(true); // Make the frame visible
    }

    // Method to start the game based on selected options
    private void startGame(String selectedLevel, int selectedFood, Color headColor, Color bodyColor) {
        SnakeGame snakeGame;
        // Create the appropriate level of the snake game based on selected level
        switch (selectedLevel) {
            case "Easy":
                snakeGame = new EasyLevel(600, 600, selectedFood);
                break;
            case "Medium":
                snakeGame = new MediumLevel(600, 600, selectedFood);
                break;
            case "Hard":
                snakeGame = new HardLevel(600, 600, selectedFood);
                break;
            case "Insane":
                snakeGame = new InsaneLevel(650, 650, selectedFood);
                break;
            default:
                // Default to Easy level if no valid level selected
                snakeGame = new SnakeGame(600, 600, selectedFood);
        }

        // Set the head and body color of the snake
        snakeGame.setHeadColor(headColor);
        snakeGame.setBodyColor(bodyColor);

        // Launch the snake game
        launchSnakeGame(snakeGame, selectedFood);
    }

        // Method to update gameplay time label with content from the file
        private void updateGameplayTime() {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("gameplay_time.txt"));
                String line = reader.readLine();
                if (line != null && line.startsWith("Gameplay Time:")) {
                    gameplayTimeLabel.setText(line);
                } else {
                    gameplayTimeLabel.setText("Gameplay Time: N/A");
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                gameplayTimeLabel.setText("Gameplay Time: N/A");
            }
        }

    // Method to launch the snake game in a new frame
    private void launchSnakeGame(SnakeGame game, int selectedFood) {
        game.placeFood(selectedFood); // Place food on the game board
        JFrame frame = new JFrame("Snake Game");

        frame.add(game); // Add the game panel to the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        frame.pack(); // Pack the frame
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true); // Make the frame visible

        this.dispose(); // Close the current frame
    }

    // Action performed method to handle button click events
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("start")) {
            // Get selected options for level, food amount, and snake color
            String selectedLevel = (String) levelSelection.getSelectedItem();
            int selectedFood = (int) foodSelection.getSelectedItem();
            String selectedColor = (String) colorSelection.getSelectedItem();
            Color headColor = null;
            Color bodyColor = null;

            // Set head and body color based on selected snake color
            switch (selectedColor) {
                case "Green":
                    headColor = new Color(0, 250, 0);
                    bodyColor = new Color(0, 150, 0);
                    break;
                case "Blue":
                    headColor = new Color(3, 74, 252);
                    bodyColor = new Color(0, 35, 122);
                    break;
                case "Orange":
                    headColor = new Color(255, 191, 0);
                    bodyColor = new Color(143, 107, 0);
                    break;
            }

            // Start the game with selected options
            startGame(selectedLevel, selectedFood, headColor, bodyColor);
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        new App(); // Create an instance of the application
    }
}
