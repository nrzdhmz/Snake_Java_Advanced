import java.time.LocalDateTime;

public class Tile {
    int x;
    int y;
    boolean isYellowApple;
    boolean isPurpleApple;
    LocalDateTime creationTime;

    Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.creationTime = LocalDateTime.now();
    }
}
