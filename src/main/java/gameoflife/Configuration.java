package gameoflife;

public record Configuration(int width, int height, int[] survive, int[] birth, boolean[][] pattern) {
}
