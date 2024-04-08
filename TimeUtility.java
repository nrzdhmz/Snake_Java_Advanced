import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

public class TimeUtility {
    // Method to load the start time from a file
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

    // Method to record the start time of the gameplay
    public static void recordStartTime(LocalDateTime startTime) {
        try (FileWriter writer = new FileWriter("start_time.txt")) {
            writer.write(startTime.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to save the start time into a text file
    public static void saveStartTime(LocalDateTime startTime) {
        try (FileWriter writer = new FileWriter("start_time.txt")) {
            writer.write(startTime.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to record the end time of the gameplay
    public static LocalDateTime recordEndTime() {
        return LocalDateTime.now();
    }

    // Method to calculate gameplay duration
    public static Duration calculateGameplayDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime);
    }

    // Method to save gameplay time into a text file
    public static void saveGameplayTime(Duration duration) {
        try (FileWriter writer = new FileWriter("gameplay_time.txt")) {
            writer.write("Working on this project for " + duration.toDays() + " days");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
