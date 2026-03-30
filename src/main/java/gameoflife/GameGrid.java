package gameoflife;

import java.util.Arrays;

public class GameGrid {

    private int width, height;  // the width and height of the grid
    private boolean[][] grid;   // contains the states of all cells (alive = true, dead = false)
    private int[] birth = {3};    // contains all "alive neighbour counts" that cause a cell to be born
    private int[] survive = {2, 3};  // contains all "alive neighbour counts" that cause a cell to survive


    public GameGrid(int sizeX, int sizeY) {
        this.width = sizeX;
        this.height = sizeY;
        grid = new boolean[sizeX][sizeY];
    }


    /**
     *  Clears the game grid and copies the new configuration centered into the grid
     */
    public void loadConfigurationCentered(Configuration config) {
        // increase grid dimensions if the new pattern is larger
        if (width < config.width() || height < config.height()) {
            width = config.width() + 10;
            height = config.height() + 10;
        }
        birth = config.birth();
        survive = config.survive();
        grid = new boolean[width][height];

        // copy the configuration centered into the grid
        int startX = width / 2 - config.width() / 2;
        int startY = height / 2 - config.height() / 2;
        for (int x = 0; x < config.width(); x++) {
            System.arraycopy(config.pattern()[x], 0, grid[startX + x], startY, config.height());
        }
    }


    /**
     *  Steps the simulation forward by one generation
     */
    public void calculateNextGeneration() {
        boolean[][] newGeneration = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                newGeneration[x][y] = determineFateForCell(x, y);
            }
        }
        grid = newGeneration;
    }


    /**
     *  Returns the new state of a cell, according to the rules of Conway's game of life
     */
    private boolean determineFateForCell(int posX, int posY) {
        int aliveNeighbours = getNumberOfAliveNeighbours(posX, posY);
        boolean canBeBirthed = Arrays.stream(birth).anyMatch(i -> i == aliveNeighbours);
        boolean canSurvive = Arrays.stream(survive).anyMatch(i -> i == aliveNeighbours);
        if (!isCellAlive(posX, posY) && canBeBirthed) {
            return true;
        }
        return isCellAlive(posX, posY) && canSurvive;
    }


    /**
     *  Returns the number of neighbours that are alive for a given cell
     */
    private int getNumberOfAliveNeighbours(int posX, int posY) {
        int sum = 0;
        for (int x = posX - 1; x <= posX + 1; x++) {
            // only columns that are inside the bounds of the grid can have alive cells
            if (x >= 0 && x < width) {
                for (int y = posY - 1; y <= posY + 1; y++) {
                    // only count alive cells that are inside the bounds of the grid and are not the input cell
                    if (y >= 0 && y < height && (x != posX || y != posY)) {
                        sum += isCellAlive(x, y) ? 1 : 0;
                    }
                }
            }
        }
        return sum;
    }


    /**
     *  Returns the pattern of this game grid as a configuration
     */
    public Configuration getCurrentConfiguration() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        boolean isEmpty = true;

        // find the smallest bounding rectangle that contains all alive cells
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isCellAlive(x, y)) {
                    minY = Math.min(y, minY);
                    minX = minX == Integer.MAX_VALUE ? x : minX;
                    maxY = Math.max(y, maxY);
                    maxX = Math.max(x, maxX);
                    isEmpty = false;
                }
            }
        }

        // return empty configuration when the grid has no alive cells
        if (isEmpty) return new Configuration(0, 0, survive, birth, new boolean[0][0]);

        // copy the pattern from the game grid and return it
        int sizeX = maxX - minX + 1;
        int sizeY = maxY - minY + 1;
        boolean[][] result = new boolean[sizeX][sizeY];
        for (int x = 0; x < sizeX ; x++) {
            System.arraycopy(grid[minX + x], minY, result[x], 0, sizeY);
        }

        return new Configuration(sizeX, sizeY, survive, birth, result);
    }


    /**
     *  Returns the state of the cell at the given x and y coordinate. Cells that are outside the bounds
     *  are considered dead.
     */
    public boolean isCellAlive(int x, int y) {
        boolean isInBounds = x >= 0 && x < width && y >= 0 && y < height;
        return isInBounds && grid[x][y];
    }


    /**
     *  Sets the state of the cell at the given x and y coordinates, if the coordinates are inside the bounds
     */
    public void setCell(int x, int y, boolean state) {
        boolean isInBounds = x >= 0 && x < width && y >= 0 && y < height;
        if (isInBounds) {
            grid[x][y] = state;
        }
    }


    public int getWidth(){
        return width;
    }


    public int getHeight() {
        return height;
    }


    public void clear() {
        grid = new boolean[width][height];
    }
}
