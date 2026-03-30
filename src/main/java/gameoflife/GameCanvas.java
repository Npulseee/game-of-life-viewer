package gameoflife;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameCanvas extends Canvas {

    private double cubeSize;
    private GameGrid gameGrid;
    private double centerOffsetX, centerOffsetY;
    private int cubeOffsetX, cubeOffsetY;
    private double moveOffsetX, moveOffsetY;

    /**
     * Draws the grid to window
     */
    public void drawGrid() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        int cubeCountX = (int) (getWidth() / cubeSize);
        int cubeCountY = (int) (getHeight() / cubeSize);
        centerOffsetX = (getWidth() - cubeCountX * cubeSize) / 2;
        centerOffsetY = (getHeight() - cubeCountY * cubeSize) / 2;
        cubeOffsetX = (gameGrid.getWidth() - cubeCountX) / 2;
        cubeOffsetY = (gameGrid.getHeight() - cubeCountY) / 2;
        for (int x = 0; x < cubeCountX; x++) {
            for (int y = 0; y < cubeCountY; y++) {
                if (gameGrid.isCellAlive(x + cubeOffsetX + (int) (moveOffsetX / cubeSize), y + cubeOffsetY + (int) (moveOffsetY / cubeSize))) {
                    gc.fillRect(centerOffsetX + x * cubeSize, centerOffsetY + y * cubeSize, cubeSize, cubeSize);
                } else {
                    gc.strokeRect(centerOffsetX + x * cubeSize, centerOffsetY + y * cubeSize, cubeSize, cubeSize);
                }
            }
        };
    }


    public void setGame(GameGrid game) {
        this.gameGrid = game;
        moveOffsetY = 0;
        moveOffsetX = 0;
    }


    public void setCubeSize(double cubeSize) {
        double oldSize = this.cubeSize;
        this.cubeSize = cubeSize;

    }


    public double getCubeSize() {
        return cubeSize;
    }


    public void drawOverPosition(double screenPosX, double screenPosY,  boolean state) {
        int posX = (int) ((screenPosX - centerOffsetX) / cubeSize);
        int posY = (int) ((screenPosY - centerOffsetY) / cubeSize);
        gameGrid.setCell(posX + cubeOffsetX + (int) (moveOffsetX / cubeSize), posY + cubeOffsetY + (int) (moveOffsetY / cubeSize), state);
    }


    public void addDragOffset(double offsetX, double offsetY) {
        moveOffsetX += offsetX;
        moveOffsetY += offsetY;
    }
}
