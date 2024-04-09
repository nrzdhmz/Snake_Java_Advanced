import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {
    private Clip eatFoodClip; // Sound clip for eating food
    private Clip gameOverClip; // Sound clip for game over
    private Clip collisionClip; // Sound clip for collision

    public SoundManager() {
        loadSoundClips();
    }

    private void loadSoundClips() {
        try {
            // Load eat food sound
            eatFoodClip = AudioSystem.getClip();
            AudioInputStream eatFoodInputStream = AudioSystem.getAudioInputStream(new File("eat.wav"));
            eatFoodClip.open(eatFoodInputStream);

            // Load game over sound
            gameOverClip = AudioSystem.getClip();
            AudioInputStream gameOverInputStream = AudioSystem.getAudioInputStream(new File("02.Gameover.wav"));
            gameOverClip.open(gameOverInputStream);

            // Load collision sound
            collisionClip = AudioSystem.getClip();
            AudioInputStream collisionInputStream = AudioSystem.getAudioInputStream(new File("01Collide.wav"));
            collisionClip.open(collisionInputStream);
        } catch (Exception ex) {
            System.out.println("Error loading sound clips: " + ex.getMessage());
        }
    }

    public void playEatFoodClip() {
        playSound(eatFoodClip);
    }

    public void playGameOverClip() {
        playSound(gameOverClip);
    }

    public void playCollisionClip() {
        playSound(collisionClip);
    }

    private void playSound(Clip clip) {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }
}
