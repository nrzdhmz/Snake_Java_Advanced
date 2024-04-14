import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Utility class for handling game time-related operations.
 */
public class TimeUtility {
    /**
     * Loads the start time from a file if available, or records the current time as the start time if the file doesn't exist.
     *
     * @return The start time of the gameplay.
     */
    public static LocalDateTime loadStartTime() {
        LocalDateTime startTime = null;
        File file = new File("start_time.txt");
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                String startTimeString = scanner.nextLine();
                startTime = LocalDateTime.parse(startTimeString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            startTime = LocalDateTime.now();
            saveStartTime(startTime); // Record start time if file doesn't exist
        }
        return startTime;
    }

    /**
     * Records the start time of the gameplay.
     *
     * @param startTime The start time to be recorded.
     */
    public static void recordStartTime(LocalDateTime startTime) {
        try (FileWriter writer = new FileWriter("start_time.txt")) {
            writer.write(startTime.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the start time into a text file.
     *
     * @param startTime The start time to be saved.
     */
    public static void saveStartTime(LocalDateTime startTime) {
        try (FileWriter writer = new FileWriter("start_time.txt")) {
            writer.write(startTime.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Records the end time of the gameplay.
     *
     * @return The end time of the gameplay.
     */
    public static LocalDateTime recordEndTime() {
        return LocalDateTime.now();
    }

    /**
     * Calculates the duration of gameplay.
     *
     * @param startTime The start time of the gameplay.
     * @param endTime   The end time of the gameplay.
     * @return The duration of the gameplay.
     */
    public static Duration calculateGameplayDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime);
    }

    /**
     * Saves the gameplay time into a text file.
     *
     * @param duration The duration of the gameplay.
     */
    public static void saveGameplayTime(Duration duration) {
        try (FileWriter writer = new FileWriter("gameplay_time.txt")) {
            writer.write("Working on this project for " + duration.toDays() + " days");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
