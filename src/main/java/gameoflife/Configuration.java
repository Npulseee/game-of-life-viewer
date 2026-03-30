package gameoflife;

/**
 * A record for storing a given configuration in Conway's game of life
 */
public record Configuration(int width, int height, int[] survive, int[] birth, boolean[][] pattern) {
}
