import java.time.LocalDateTime;

public class Tile {
    int x;
    int y;
    boolean isYellowApple;
    boolean isPurpleApple;
    boolean isSpecialApple; // New property to indicate if the tile is a special apple
    LocalDateTime creationTime;

    Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.creationTime = LocalDateTime.now();
    }
}
