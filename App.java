import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener {
    JComboBox<String> levelSelection; // Added JComboBox for level selection
    JComboBox<Integer> foodSelection; // Added JComboBox for food selection
    JButton startButton; // Added start button

    public App() {
        setTitle("Snake Game");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        getContentPane().setBackground(Color.gray); // Set background color to black

        // Create JComboBox for level selection
        String[] levels = {"Easy", "Medium", "Hard", "Insane"}; // Example level options
        levelSelection = new JComboBox<>(levels);

        // Create JComboBox for food selection
        Integer[] foodOptions = {1, 3, 5}; // Options for food generation
        foodSelection = new JComboBox<>(foodOptions);

        // Create start button
        startButton = new JButton("Start");

        // Set action commands
        startButton.setActionCommand("start");

        // Add action listeners
        startButton.addActionListener(this);

        // Set layout
        setLayout(new GridBagLayout());

        // Add components to the frame with constraints to center them
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
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

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Span over two columns
        add(startButton, gbc);

        setVisible(true);
    }

    private void startGame(String selectedLevel, int selectedFood) {
        // Logic to start the game with the selected level and food amount
        // Create an instance of the appropriate level class based on the selected level
        if (selectedLevel.equals("Easy")) {
            EasyLevel game = new EasyLevel(600, 600, selectedFood);
            launchSnakeGame(game, selectedFood);
        } else if (selectedLevel.equals("Medium")) {
            MediumLevel game = new MediumLevel(600, 600, selectedFood);
            launchSnakeGame(game, selectedFood);
        } else if (selectedLevel.equals("Hard")) {
            HardLevel game = new HardLevel(600, 600, selectedFood);
            launchSnakeGame(game, selectedFood);
        } else if (selectedLevel.equals("Insane")) {
            InsaneLevel game = new InsaneLevel(650, 650, selectedFood);
            launchSnakeGame(game, selectedFood);
        }
    }

    private void launchSnakeGame(SnakeGame game, int selectedFood) {
        JFrame frame = new JFrame("Snake Game");
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the game window
        frame.setVisible(true);
    
        // Close the homepage
        this.dispose();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("start")) {
            // Retrieve selected level and food amount
            String selectedLevel = (String) levelSelection.getSelectedItem();
            int selectedFood = (int) foodSelection.getSelectedItem();
            // Start the game with selected level and food amount
            startGame(selectedLevel, selectedFood);
        }
    }

    public static void main(String[] args) {
        new App();
    }
}
