import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener {
    JButton level1Button, level2Button, level3Button; // Added level3Button

    public App() {
        setTitle("Snake Game");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create buttons
        level1Button = new JButton("Easy Level");
        level2Button = new JButton("Medium Level");
        level3Button = new JButton("Hard Level");

        // Set action commands
        level1Button.setActionCommand("level1");
        level2Button.setActionCommand("level2");
        level3Button.setActionCommand("level3"); // Set action command for level3Button

        // Add action listeners
        level1Button.addActionListener(this);
        level2Button.addActionListener(this);
        level3Button.addActionListener(this); // Add action listener for level3Button

        // Set layout
        setLayout(new GridLayout(3, 1)); // Adjust layout for three buttons

        // Add buttons to the frame
        add(level1Button);
        add(level2Button);
        add(level3Button); // Add level3Button

        setVisible(true);
    }

    private void launchSnakeGame(SnakeGame game) {
        JFrame frame = new JFrame("Snake Game");

        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // Close the homepage
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Launch Snake game with the selected level
        if (command.equals("level1")) {
            launchSnakeGame(new EasyLevel(600, 600)); // Create EasyLevel instance
        } else if (command.equals("level2")) {
            launchSnakeGame(new MediumLevel(600, 600)); // Assuming you have a MediumLevel class
        } else if (command.equals("level3")) {
            launchSnakeGame(new HardLevel(600, 600)); // Assuming you have a HardLevel class
        }
    }

    public static void main(String[] args) {
        new App();
    }
}
