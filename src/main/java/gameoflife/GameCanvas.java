package gameoflife;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameCanvas extends Canvas {

    private double cubeSize;
    private GameGrid gameGrid;
    private double totalX, totalY;
    private int offsetX, offsetY;


    /**
     * Draws the grid to window
     */
    public void drawGrid() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        int cubeCountX = Math.min((int) (getWidth() / cubeSize), gameGrid.getWidth());
        int cubeCountY = Math.min((int) (getHeight() / cubeSize), gameGrid.getHeight());
        double startX = (getWidth() - cubeCountX * cubeSize) / 2;
        double startY = (getHeight() - cubeCountY * cubeSize) / 2;
        for (int x = 0; x < cubeCountX; x++) {
            for (int y = 0; y < cubeCountY; y++) {
                if (gameGrid.isCellAlive(x, y)) {
                    gc.fillRect(startX + x * cubeSize, startY + y * cubeSize, cubeSize, cubeSize);
                } else {
                    gc.strokeRect(startX + x * cubeSize, startY + y * cubeSize, cubeSize, cubeSize);
                }
            }
        };
    }


    public void setGame(GameGrid game) {
        this.gameGrid = game;
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
        totalX = -gameGrid.getWidth() / 2.0;
        totalY = -((double) gameGrid.getHeight() / 2 + (getHeight() / cubeSize) / 2);
    }


    public void addToOffsetX(int add) {
        offsetX = add;
    }


    public void addToOffsetY(int add) {
        offsetY = add;
    }
}
