import java.time.LocalDateTime;

/**
 * Represents a tile on the game board.
 */
public class Tile {
    /** The x-coordinate of the tile. */
    int x;
    /** The y-coordinate of the tile. */
    int y;
    /** Indicates if the tile contains a yellow apple. */
    boolean isYellowApple;
    /** Indicates if the tile contains a purple apple. */
    boolean isPurpleApple;
    /** Indicates if the tile contains a special apple. */
    boolean isSpecialApple;
    /** The timestamp when the tile was created. */
    LocalDateTime creationTime;

    /**
     * Constructs a new Tile object with the specified coordinates.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.creationTime = LocalDateTime.now();
    }
}
