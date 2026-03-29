package com.example.gameoflife;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class GameCanvas extends Canvas {

    private float cubeSize;
    private Grid game;
    private int totalX, totalY;
    private int offsetX, offsetY;


    public void drawGrid() {
        int[][] grid = game.getValues();
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        int countX = (int) (getWidth() / cubeSize);
        int countY = (int) (getHeight() / cubeSize);
        int starX = totalX + offsetX;
        int starY = totalY + offsetY;
        for (int x = starX; x < starX + countX; x++) {
            for (int y = starY; y < starY + countY; y++) {
                try {
                    if (Math.abs(x) >= grid.length || Math.abs(y) >= grid[0].length || Math.abs(x) < 0 || Math.abs(y) < 0) {
                        continue;
                    }
                    if (grid[Math.abs(x)][Math.abs(y)] == 0) {
                        gc.strokeRect((x - starX) * cubeSize, (y - starY) * cubeSize, cubeSize, cubeSize);//draws the empty cubes
                    } else {
                        gc.fillRect((x - starX) * cubeSize, (y - starY) * cubeSize, cubeSize, cubeSize);//draws the black cubes
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

    public float getCubeSize() {
        return cubeSize;
    }

    public void setTotals() {
        totalX += offsetX;
        totalY += offsetY;
        offsetX = 0;
        offsetY = 0;
    }

    public void updateCanvasPosition() {
        totalX = -(game.getValues().length / 2 + (int) (getWidth() / cubeSize) / 2);
        totalY = -(game.getValues()[0].length / 2 + (int) (getHeight() / cubeSize) / 2);
    }

    public void addToOffsetX(int add) {
        offsetX = add;
    }

    public void addToOffsetY(int add) {
        offsetY = add;
    }
}
