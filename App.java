import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The main class for the Snake game application.
 */
public class App extends JFrame implements ActionListener {
    // Components for level selection, food amount selection, snake color selection, and start button
    JRadioButton easyRadioButton, mediumRadioButton, hardRadioButton, insaneRadioButton;
    JRadioButton oneFoodRadioButton, threeFoodRadioButton, fiveFoodRadioButton;
    JRadioButton greenRadioButton, blueRadioButton, orangeRadioButton;
    JButton startButton;
    JLabel gameplayTimeLabel; // Label to display gameplay time

    /**
     * Constructor to initialize the application.
     */
    public App() {
        setTitle("Snake Game"); // Set the title of the frame
        setSize(600, 600); // Set the size of the frame
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Set the default close operation
        setLocationRelativeTo(null); // Center the frame on the screen
        getContentPane().setBackground(Color.gray); // Set the background color of the content pane

        // Set the icon of the frame
        ImageIcon icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());

        // Initialize level selection radio buttons
        easyRadioButton = new JRadioButton("Easy");
        mediumRadioButton = new JRadioButton("Medium");
        hardRadioButton = new JRadioButton("Hard");
        insaneRadioButton = new JRadioButton("Insane");

        // Group level selection radio buttons
        ButtonGroup levelGroup = new ButtonGroup();
        levelGroup.add(easyRadioButton);
        levelGroup.add(mediumRadioButton);
        levelGroup.add(hardRadioButton);
        levelGroup.add(insaneRadioButton);

        // Initialize food amount selection radio buttons
        oneFoodRadioButton = new JRadioButton("2");
        threeFoodRadioButton = new JRadioButton("3");
        fiveFoodRadioButton = new JRadioButton("5");

        // Group food amount selection radio buttons
        ButtonGroup foodGroup = new ButtonGroup();
        foodGroup.add(oneFoodRadioButton);
        foodGroup.add(threeFoodRadioButton);
        foodGroup.add(fiveFoodRadioButton);

        // Initialize snake color selection radio buttons
        greenRadioButton = new JRadioButton("Green");
        blueRadioButton = new JRadioButton("Blue");
        orangeRadioButton = new JRadioButton("Orange");

        // Group snake color selection radio buttons
        ButtonGroup colorGroup = new ButtonGroup();
        colorGroup.add(greenRadioButton);
        colorGroup.add(blueRadioButton);
        colorGroup.add(orangeRadioButton);

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
        gbc.gridy = 0;
        gbc.gridx = 0;
        add(new JLabel("Select Level:"), gbc);
        gbc.gridx = 1;
        add(easyRadioButton, gbc);
        gbc.gridx = 2;
        add(mediumRadioButton, gbc);
        gbc.gridx = 3;
        add(hardRadioButton, gbc);
        gbc.gridx = 4;
        add(insaneRadioButton, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Select Food Amount:"), gbc);
        gbc.gridx = 1;
        add(oneFoodRadioButton, gbc);
        gbc.gridx = 2;
        add(threeFoodRadioButton, gbc);
        gbc.gridx = 3;
        add(fiveFoodRadioButton, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Select Snake Color:"), gbc);
        gbc.gridx = 1;
        add(greenRadioButton, gbc);
        gbc.gridx = 2;
        add(blueRadioButton, gbc);
        gbc.gridx = 3;
        add(orangeRadioButton, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        add(startButton, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        add(gameplayTimeLabel, gbc);

        setVisible(true); // Make the frame visible
    }

    /**
     * Method to start the game based on selected options.
     *
     * @param selectedLevel The selected level of difficulty.
     * @param selectedFood The selected amount of food.
     * @param headColor The color of the snake's head.
     * @param bodyColor The color of the snake's body.
     */
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
                snakeGame = new InsaneLevel(600, 600, selectedFood);
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

    /**
     * Method to update gameplay time label with content from the file.
     */
    private void updateGameplayTime() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("gameplay_time.txt"));
            String line = reader.readLine();
            if (line != null && line.startsWith("Working on this project for")) {
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

    /**
     * Method to launch the snake game in a new frame.
     *
     * @param game The SnakeGame instance to launch.
     * @param selectedFood The selected amount of food.
     */
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

    /**
     * Action performed method to handle button click events.
     *
     * @param e The ActionEvent object representing the event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (easyRadioButton.isSelected() || mediumRadioButton.isSelected() || hardRadioButton.isSelected() || insaneRadioButton.isSelected()) {
            if (oneFoodRadioButton.isSelected() || threeFoodRadioButton.isSelected() || fiveFoodRadioButton.isSelected()) {
                if (greenRadioButton.isSelected() || blueRadioButton.isSelected() || orangeRadioButton.isSelected()) {
                    String selectedLevel = easyRadioButton.isSelected() ? "Easy" :
                            mediumRadioButton.isSelected() ? "Medium" :
                            hardRadioButton.isSelected() ? "Hard" : "Insane";
                    int selectedFood = oneFoodRadioButton.isSelected() ? 1 :
                            threeFoodRadioButton.isSelected() ? 3 : 5;
                    Color headColor = greenRadioButton.isSelected() ? new Color(204, 0, 102) :
                            blueRadioButton.isSelected() ? new Color(55, 91, 152) :
                            new Color(255, 255, 0);
                    Color bodyColor = greenRadioButton.isSelected() ? new Color(76, 153, 0) :
                            blueRadioButton.isSelected() ? new Color(255, 187, 1) :
                            new Color(127, 0, 127);
                    startGame(selectedLevel, selectedFood, headColor, bodyColor);
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a snake color.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a food amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a level.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Main method to start the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new App(); // Create an instance of the application
    }
}
