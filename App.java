import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class App extends JFrame implements ActionListener {
    JButton level1Button, level2Button, level3Button, level4Button; // Added level3Button
    static Clip backgroundMusicClip; // Static field to hold the background music clip

    public App() {
        setTitle("Snake Game");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        getContentPane().setBackground(Color.gray); // Set background color to black
        // Create buttons
        level1Button = new JButton("Easy Level");
        level2Button = new JButton("Medium Level");
        level3Button = new JButton("Hard Level");
        level4Button = new JButton("Insane Level");

        // Set action commands
        level1Button.setActionCommand("level1");
        level2Button.setActionCommand("level2");
        level3Button.setActionCommand("level3"); // Set action command for level3Button
        level4Button.setActionCommand("level4"); // Set action command for level3Button

        // Add action listeners
        level1Button.addActionListener(this);
        level2Button.addActionListener(this);
        level3Button.addActionListener(this); // Add action listener for level3Button
        level4Button.addActionListener(this); // Add action listener for level3Button

        // Set preferred sizes for buttons
        Dimension buttonSize = new Dimension(200, 80);
        level1Button.setPreferredSize(buttonSize);
        level2Button.setPreferredSize(buttonSize);
        level3Button.setPreferredSize(buttonSize);
        level4Button.setPreferredSize(buttonSize);

        // Set layout
        setLayout(new GridBagLayout());

        // Add buttons to the frame with constraints to center them
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(level1Button, gbc);

        gbc.gridy = 1;
        add(level2Button, gbc);

        gbc.gridy = 2;
        add(level3Button, gbc);

        gbc.gridy = 3;
        add(level4Button, gbc);

        setVisible(true);

        // Start background music
        playBackgroundMusic();
    }

    private void launchSnakeGame(SnakeGame game) {
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

        // Launch Snake game with the selected level
        if (command.equals("level1")) {
            launchSnakeGame(new EasyLevel(600, 600)); // Create EasyLevel instance
        } else if (command.equals("level2")) {
            launchSnakeGame(new MediumLevel(600, 600)); // Assuming you have a MediumLevel class
        } else if (command.equals("level3")) {
            launchSnakeGame(new HardLevel(600, 600)); // Assuming you have a HardLevel class
        } else if (command.equals("level4")) {
            launchSnakeGame(new InsaneLevel(650, 650)); // Assuming you have a HardLevel class
        }
    }

    // Method to play background music
    private static void playBackgroundMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("background_music.wav").getAbsoluteFile());
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioInputStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the background music continuously
        } catch (Exception ex) {
            System.out.println("Error playing background music: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new App();
    }
}
