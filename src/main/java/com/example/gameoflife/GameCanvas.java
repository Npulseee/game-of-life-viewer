package com.example.gameoflife;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class GameCanvas extends Canvas {

    private double cubeSize;
    private Grid game;
    private double totalX, totalY;
    private int offsetX, offsetY;


    public void drawGrid() {
        int[][] grid = game.getValues();
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        int cubeCountX = Math.min((int) (getWidth() / cubeSize), grid.length);
        int cubeCountY = Math.min((int) (getHeight() / cubeSize), grid[0].length);
        double startX = (getWidth() - cubeCountX * cubeSize) / 2;
        double startY = (getHeight() - cubeCountY * cubeSize) / 2;
        for (int x = 0; x < cubeCountX; x++) {
            for (int y = 0; y < cubeCountY; y++) {
                try {
                    if (x >= grid.length || y >= grid[0].length || x < 0 || y < 0) {
                        continue;
                    }
                    if (grid[x][y] == 0) {
                        gc.strokeRect(startX + x * cubeSize, startY + y * cubeSize, cubeSize, cubeSize);//draws the empty cubes
                    } else {
                        gc.fillRect(startX + x * cubeSize, startY + y * cubeSize, cubeSize, cubeSize);//draws the black cubes
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

            }
        };
    }


    public void setGame(Grid game) {
        this.game = game;
        updateCanvasPosition();
    }

    public void setCubeSize(float cubeSize) {
        this.cubeSize = cubeSize;
    }

    public double getCubeSize() {
        return cubeSize;
    }

    public void setTotals() {
        totalX += offsetX;
        totalY += offsetY;
        offsetX = 0;
        offsetY = 0;
    }

    public void updateCanvasPosition() {
        totalX = -game.getValues()[0].length / 2.0;
        totalY = -(game.getValues()[0].length / 2 + (int) (getHeight() / cubeSize) / 2);
    }

    public void addToOffsetX(int add) {
        offsetX = add;
    }

    public void addToOffsetY(int add) {
        offsetY = add;
    }
}
