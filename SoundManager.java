import javax.sound.sampled.*;
import java.io.File;

/**
 * Manages the playback of sound clips for various game events.
 */
public class SoundManager {
    private Clip eatFoodClip; // Sound clip for eating food
    private Clip gameOverClip; // Sound clip for game over
    private Clip collisionClip; // Sound clip for collision

    /**
     * Constructs a new SoundManager and loads the sound clips.
     */
    public SoundManager() {
        loadSoundClips();
    }

    /**
     * Loads the sound clips from audio files.
     */
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

    /**
     * Plays the sound clip for eating food.
     */
    public void playEatFoodClip() {
        playSound(eatFoodClip);
    }

    /**
     * Plays the sound clip for game over.
     */
    public void playGameOverClip() {
        playSound(gameOverClip);
    }

    /**
     * Plays the sound clip for collision.
     */
    public void playCollisionClip() {
        playSound(collisionClip);
    }

    /**
     * Plays the specified sound clip.
     *
     * @param clip The sound clip to be played.
     */
    private void playSound(Clip clip) {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }
}
