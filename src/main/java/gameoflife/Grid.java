package gameoflife;

public class Grid {

    private int width, height;
    private boolean[][] is_alive_table;
    private int[] birth;
    private int[] survive;


    public Grid(int sizeX, int sizeY) {
        this.width = sizeX;
        this.height = sizeY;
        is_alive_table = new boolean[sizeX][sizeY];
    }


    public void loadConfigurationCentered(Configuration config) {

        // increase grid dimensions if the new pattern is larger
        if (width < config.width() || height < config.height()) {
            width = config.width() + 10;
            height = config.height() + 10;
        }
        birth = config.birth();
        survive = config.survive();
        is_alive_table = new boolean[width][height];

        int startX = width / 2 - config.width() / 2;
        int startY = height / 2 - config.height() / 2;
        for (int x = 0; x < config.width(); x++) {
            System.arraycopy(config.pattern()[x], 0, is_alive_table[startX + x], startY, config.height());
        }
    }


    public boolean[][] getValues() {
        return is_alive_table;
    }


    public void clear() {
        is_alive_table = new boolean[width][height];
    }


    public void setValueAt(int x, int y, boolean value) {
        is_alive_table[x][y] = value;
    }


    public void calculateNextGeneration() {
        boolean[][] result = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result[x][y] = determineFateForCell(x, y);
            }
        }
        is_alive_table = result;
    }


    private boolean determineFateForCell(int posX, int posY) {
        int count = getNumberOfAliveNeighbours(posX, posY);
        if (!is_alive_table[posX][posY] && count == 3) {
            return true;
        }
        return is_alive_table[posX][posY] && (count == 2 || count == 3);
    }


    private int getNumberOfAliveNeighbours(int posX, int posY) {
        int sum = 0;
        for (int x = posX - 1; x <= posX + 1; x++) {
            if (x >= 0 && x < width) {
                for (int y = posY - 1; y <= posY + 1; y++) {
                    if (y >= 0 && y < height && (x != posX || y != posY)) {
                        sum += is_alive_table[x][y] ? 1 : 0;
                    }
                }
            }
        }
        return sum;
    }



    public Configuration getCurrentConfiguration() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        boolean isEmpty = true;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (is_alive_table[x][y]) {
                    minY = Math.min(y, minY);
                    minX = minX == Integer.MAX_VALUE ? x : minX;
                    maxY = Math.max(y, maxY);
                    maxX = Math.max(x, maxX);
                    isEmpty = false;
                }
            }
        }

        if (isEmpty){
            return null;
        }

        int sizeX = maxX - minX + 1;
        int sizeY = maxY - minY + 1;

        boolean[][] result = new boolean[sizeX][sizeY];

        for (int x = 0; x < sizeX ; x++) {
            System.arraycopy(is_alive_table[minX + x], minY, result[x], 0, sizeY);
        }

        return new Configuration(sizeX, sizeY, survive, birth, result);
    }
}
