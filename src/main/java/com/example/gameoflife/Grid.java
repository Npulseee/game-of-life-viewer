package com.example.gameoflife;

import java.util.Random;

public class Grid {

    private int width, height;
    private int[][] values;


    public Grid(int sizeX, int sizeY) {
        this.width = sizeX;
        this.height = sizeY;
        values = new int[sizeX][sizeY];
    }


    public void loadConfigurationCentered(int[][] config) {
        values = new int[width][height];
        int sizeX = config.length;
        int sizeY = config[0].length;
        int startX = width / 2 - sizeX / 2;
        int startY = height / 2 - sizeY / 2;
        for (int x = 0; x < sizeX; x++) {
            System.arraycopy(config[x], 0, values[startX + x], startY, sizeY);
        }
    }


    public void fillWithRandomValues() {
        values = new int[width][height];
        Random rand = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                values[x][y] = rand.nextInt(2);
            }
        }
    }


    public int[][] getValues() {
        return values;
    }


    public void clear() {
        values = new int[width][height];
    }


    public void setValues(int[][] canvas) {
        this.values = canvas;
        width = canvas.length;
        height = canvas[0].length;
    }


    public void setValueAt(int x, int y, int value) {
        values[x][y] = value;
    }


    public void calculateNextGeneration() {
        int[][] result = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result[x][y] = determineFateForCell(x, y);
            }
        }
        values = result;
    }


    private int determineFateForCell(int posX, int posY) {
        int count = getNumberOfAliveNeighbours(posX, posY);
        if (values[posX][posY] == 0 && count == 3) {
            return 1;
        }
        if (values[posX][posY] == 1 && (count == 2 || count == 3)) {
            return 1;
        }
        return 0;
    }


    private int getNumberOfAliveNeighbours(int posX, int posY) {
        int sum = 0;
        for (int x = posX - 1; x <= posX + 1; x++) {
            if (x >= 0 && x < width) {
                for (int y = posY - 1; y <= posY + 1; y++) {
                    if (y >= 0 && y < height && (x != posX || y != posY)) {
                        sum += values[x][y];
                    }
                }
            }
        }
        return sum;
    }


    public int[][] getCurrentConfiguration() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        boolean isEmpty = true;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (values[x][y] == 1) {
                    minY = Math.min(y, minY);
                    minX = minX == Integer.MAX_VALUE ? x : minX;
                    maxY = Math.max(y, maxY);
                    maxX = Math.max(x, maxX);
                    isEmpty = false;
                }
            }
        }

        if (isEmpty){
            return new int[1][0];
        }

        int sizeX = maxX - minX + 1;
        int sizeY = maxY - minY + 1;

        int[][] result = new int[sizeX][sizeY];

        for (int x = 0; x < sizeX ; x++) {
            System.arraycopy(values[minX + x], minY, result[x], 0, sizeY);
        }

        return result;
    }
}
