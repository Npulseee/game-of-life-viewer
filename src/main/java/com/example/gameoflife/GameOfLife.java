package com.example.gameoflife;

import java.util.Random;

public class GameOfLife {
    private int sizeX, sizeY;
    private int[][] grid;
    public GameOfLife(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        grid = new int[sizeX][sizeY];
    }

    public void fillWithObjects() {
        grid[47][49] = 1;
        grid[47][50] = 1;
        grid[47][51] = 1;
        grid[47][57] = 1;
        grid[47][58] = 1;
        grid[47][59] = 1;
        grid[48][49] = 1;
        grid[48][52] = 1;
        grid[48][56] = 1;
        grid[48][59] = 1;
        grid[49][49] = 1;
        grid[49][59] = 1;
        grid[50][49] = 1;
        grid[50][59] = 1;
        grid[51][50] = 1;
        grid[51][52] = 1;
        grid[51][56] = 1;
        grid[51][58] = 1;
        grid[53][53] = 1;
        grid[53][54] = 1;
        grid[53][55] = 1;
        grid[54][52] = 1;
        grid[54][56] = 1;
        grid[55][52] = 1;
        grid[55][56] = 1;
        grid[56][51] = 1;
        grid[56][57] = 1;
        grid[58][52] = 1;
        grid[58][56] = 1;
        grid[59][53] = 1;
        grid[59][54] = 1;
        grid[59][55] = 1;
        grid[60][57] = 1;
        grid[61][56] = 1;
        grid[61][57] = 1;
        grid[61][58] = 1;
        grid[62][56] = 1;
        grid[62][58] = 1;
        grid[62][59] = 1;
        grid[63][59] = 1;
        grid[63][60] = 1;
        grid[64][59] = 1;
        grid[65][59] = 1;
        grid[65][60] = 1;
        grid[66][57] = 1;
        grid[66][59] = 1;
        grid[66][61] = 1;
        grid[66][62] = 1;
        grid[67][48] = 1;
        grid[67][53] = 1;
        grid[67][56] = 1;
        grid[67][57] = 1;
        grid[67][59] = 1;
        grid[67][61] = 1;
        grid[67][63] = 1;
        grid[68][47] = 1;
        grid[68][53] = 1;
        grid[68][54] = 1;
        grid[68][55] = 1;
        grid[68][57] = 1;
        grid[68][59] = 1;
        grid[68][61] = 1;
        grid[68][63] = 1;
        grid[68][65] = 1;
        grid[68][70] = 1;
        grid[68][71] = 1;
        grid[69][47] = 1;
        grid[69][53] = 1;
        grid[69][55] = 1;
        grid[69][57] = 1;
        grid[69][59] = 1;
        grid[69][61] = 1;
        grid[69][63] = 1;
        grid[69][65] = 1;
        grid[69][68] = 1;
        grid[69][69] = 1;
        grid[69][71] = 1;
        grid[69][72] = 1;
        grid[70][47] = 1;
        grid[70][48] = 1;
        grid[70][49] = 1;
        grid[70][50] = 1;
        grid[70][51] = 1;
        grid[70][55] = 1;
        grid[70][57] = 1;
        grid[70][59] = 1;
        grid[70][61] = 1;
        grid[70][63] = 1;
        grid[70][65] = 1;
        grid[70][66] = 1;
        grid[70][68] = 1;
        grid[70][69] = 1;
        grid[70][70] = 1;
        grid[70][71] = 1;
        grid[71][55] = 1;
        grid[71][57] = 1;
        grid[71][59] = 1;
        grid[71][61] = 1;
        grid[71][63] = 1;
        grid[71][68] = 1;
        grid[71][69] = 1;
        grid[71][70] = 1;
        grid[72][55] = 1;
        grid[72][57] = 1;
        grid[72][59] = 1;
        grid[72][61] = 1;
        grid[72][63] = 1;
        grid[72][64] = 1;
        grid[73][55] = 1;
        grid[73][57] = 1;
        grid[73][59] = 1;
        grid[73][61] = 1;
        grid[73][63] = 1;
        grid[73][68] = 1;
        grid[73][69] = 1;
        grid[73][70] = 1;
        grid[74][47] = 1;
        grid[74][48] = 1;
        grid[74][49] = 1;
        grid[74][50] = 1;
        grid[74][51] = 1;
        grid[74][55] = 1;
        grid[74][57] = 1;
        grid[74][59] = 1;
        grid[74][61] = 1;
        grid[74][63] = 1;
        grid[74][65] = 1;
        grid[74][66] = 1;
        grid[74][68] = 1;
        grid[74][69] = 1;
        grid[74][70] = 1;
        grid[74][71] = 1;
        grid[75][47] = 1;
        grid[75][53] = 1;
        grid[75][55] = 1;
        grid[75][57] = 1;
        grid[75][59] = 1;
        grid[75][61] = 1;
        grid[75][63] = 1;
        grid[75][65] = 1;
        grid[75][68] = 1;
        grid[75][69] = 1;
        grid[75][71] = 1;
        grid[75][72] = 1;
        grid[76][47] = 1;
        grid[76][53] = 1;
        grid[76][54] = 1;
        grid[76][55] = 1;
        grid[76][57] = 1;
        grid[76][59] = 1;
        grid[76][61] = 1;
        grid[76][63] = 1;
        grid[76][65] = 1;
        grid[76][70] = 1;
        grid[76][71] = 1;
        grid[77][48] = 1;
        grid[77][53] = 1;
        grid[77][56] = 1;
        grid[77][57] = 1;
        grid[77][59] = 1;
        grid[77][61] = 1;
        grid[77][63] = 1;
        grid[78][57] = 1;
        grid[78][59] = 1;
        grid[78][61] = 1;
        grid[78][62] = 1;
        grid[79][59] = 1;
        grid[79][60] = 1;
        grid[80][59] = 1;
        grid[81][59] = 1;
        grid[81][60] = 1;
        grid[82][56] = 1;
        grid[82][58] = 1;
        grid[82][59] = 1;
        grid[83][56] = 1;
        grid[83][57] = 1;
        grid[83][58] = 1;
        grid[84][57] = 1;
        grid[85][53] = 1;
        grid[85][54] = 1;
        grid[85][55] = 1;
        grid[86][52] = 1;
        grid[86][56] = 1;
        grid[88][51] = 1;
        grid[88][57] = 1;
        grid[89][52] = 1;
        grid[89][56] = 1;
        grid[90][52] = 1;
        grid[90][56] = 1;
        grid[91][53] = 1;
        grid[91][54] = 1;
        grid[91][55] = 1;
        grid[93][50] = 1;
        grid[93][52] = 1;
        grid[93][56] = 1;
        grid[93][58] = 1;
        grid[94][49] = 1;
        grid[94][59] = 1;
        grid[95][49] = 1;
        grid[95][59] = 1;
        grid[96][49] = 1;
        grid[96][52] = 1;
        grid[96][56] = 1;
        grid[96][59] = 1;
        grid[97][49] = 1;
        grid[97][50] = 1;
        grid[97][51] = 1;
        grid[97][57] = 1;
        grid[97][58] = 1;
        grid[97][59] = 1;
    }

    public void addObjectCenter(int[][] object) {
        grid = new int[sizeX][sizeY];
        int offsetX = sizeX / 2 - object.length / 2;
        int offsetY = sizeY / 2 - object[0].length / 2;
        for (int y = 0; y < object[0].length; y++) {
            for (int x = 0; x < object.length; x++) {
                grid[y + offsetY][x + offsetX] = object[object.length- 1 - x][object[0].length - 1- y];
            }
        }
    }

    public void fillWithRandomValues() {
        grid = new int[sizeX][sizeY];
        Random rand = new Random();
        for(int x = 0; x < sizeX ; x++) {
            for (int y = 0; y < sizeY ; y++) {
                grid[x][y] = rand.nextInt(2);//gives lifeMatrix[i][j] a int value of 0 or 1.
            }
        }
    }

    public int getValueAt(int x, int y) {
        return x >= sizeX || y >= sizeY ? 0 : grid[x][y];
    }

    public int[][] getGrid() {
        return grid;
    }
    public void setGrid(int[][] canvas) {
        this.grid = canvas;
        sizeX = canvas.length;
        sizeY =canvas[0].length;
        System.out.println(sizeX + ", " + sizeY);
    }
    public void setValueAt(int x, int y, int value) {
        grid[x][y] = value;
    }

    public void calculateNextGeneration() {
        int[][] result = new int[sizeX][sizeY];
        for (int x = 0; x < sizeX ; x++) {
            for (int y = 0; y < sizeY; y++) {
                result[x][y] = determineFaithForCell(x, y);
            }
        }
        grid = result;
    }

    private int determineFaithForCell(int posX, int posY) {
        int count = getNeighbourCount(posX, posY);
        if (grid[posX][posY] == 0 && count == 3) {
            return 1;
        }
        if (grid[posX][posY] == 1 && (count == 2 || count == 3)){
            return 1;
        }
        return 0;
    }
    private int getNeighbourCount(int posX, int posY) {     //-1 == sizeX - 1 bzw sizeY - 1
        int count = 0;                                      //sizeX == 0
        for (int x = posX -1; x <= posX + 1 ; x++) {
            for (int y = posY -1; y <= posY + 1 ; y++) {
                count += grid[x == -1 ? sizeX - 1 : x == sizeX ? 0 : x][y == -1 ? sizeY - 1 : y == sizeY ? 0 : y];
            }

        }
        return count - grid[posX][posY];
    }


    public void printData() {
        int[] minimums = getMinimums();
        if (minimums != null) {
            System.out.println(minimums[0] + ", " + minimums[1]);
            for (int x = 0; x < sizeX  ; x++) {
                for (int y = 0; y < sizeY; y++) {
                    if (grid[x][y] == 1){
                        System.out.println("grid[" + x + "]["+ y +"] = " + grid[x][y]+ ";");
                    }
                }
            }
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        } else {
            System.out.println("[ERROR]: Can not save empty field!");
        }

    }

    private int[] getMinimums() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (grid[x][y] == 1) {
                    minY = Math.min(y, minY);
                    minX = minX == Integer.MAX_VALUE ? x : minX;
                    maxY = Math.max(y, maxY);
                    maxX = Math.max(x, maxX);
                }
            }
        }

        return minX == Integer.MAX_VALUE ? null : new int[] {minX, minY};
    }
}
