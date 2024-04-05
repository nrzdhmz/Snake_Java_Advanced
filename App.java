import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener {
    JComboBox<String> levelSelection;
    JComboBox<Integer> foodSelection;
    JComboBox<String> colorSelection;
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

        String[] snakeColors = {"Green", "Blue", "Orange"};
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

    private void startGame(String selectedLevel, int selectedFood, Color headColor, Color bodyColor) {
        SnakeGame snakeGame;
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

        snakeGame.setHeadColor(headColor);
        snakeGame.setBodyColor(bodyColor);

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

            String selectedColor = (String) colorSelection.getSelectedItem();
            Color headColor = null;
            Color bodyColor = null;

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

            startGame(selectedLevel, selectedFood, headColor, bodyColor);
        }
    }

    public static void main(String[] args) {
        new App();
    }
}
