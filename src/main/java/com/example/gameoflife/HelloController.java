package com.example.gameoflife;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class HelloController {

    @FXML
    private GameGrid gameGrid;
    @FXML
    private ModeButton modeButton;
    private AnimationTimer timer;
    private int lastX, lastY;
    private int startX, startY;
    GameOfLife game;
    private int canvasSizeX;
    private int canvasSizeY;
    private final GameOfLife lastSavedGame = new GameOfLife(canvasSizeX, canvasSizeY);
    private final GameOfLife initGame = new GameOfLife(canvasSizeX, canvasSizeY);
    private final float initSize = 10;

    public void initialize() {
        gameGrid.setCubeSize(initSize);
        gameGrid.getGraphicsContext2D().setLineWidth(.1);;
        canvasSizeX = 1000;
        canvasSizeY = 1000;
        game = new GameOfLife(canvasSizeX, canvasSizeY);
        gameGrid.setGame(game);
        initGame.setGrid(game.getGrid());
        game.fillWithRandomValues();
        lastSavedGame.setGrid(game.getGrid());
        gameGrid.drawGrid();
        addEventHandlerToGameGrid();
    }

    @FXML
    void onButtonRun() {
        startFreshTimer();
    }
    @FXML
    void onButtonReset() {
        game.setGrid(lastSavedGame.getGrid());
        gameGrid.setGame(game);
        gameGrid.drawGrid();
    }

    @FXML
    void onButtonClear() {
        game.setGrid(initGame.getGrid());
        gameGrid.setGame(game);
        gameGrid.drawGrid();
    }

    @FXML
    void onButtonImport() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("src\\main\\resources\\com\\example\\gameoflife"));
        File file = chooser.showOpenDialog(gameGrid.getScene().getWindow());
        int[][] res  = RLE_Encoder.importFile(file.getName());
        game.addObjectCenter(res);
        lastSavedGame.setGrid(game.getGrid());
        gameGrid.setGame(game);
        gameGrid.drawGrid();
    }
    @FXML
    void onButtonSave() {
        lastSavedGame.setGrid(game.getGrid());
        game.printData();
    }

    @FXML
    void onButtonPause() {
        if (timer != null) {
            timer.stop();
        }
    }


    private void startFreshTimer() {
        if (timer != null) {
            timer.stop();
        }
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 62500000) {
                    game.calculateNextGeneration();//calculates the matrix for the next generation
                    gameGrid.drawGrid();
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    private void addEventHandlerToGameGrid() {

        gameGrid.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if (modeButton.getActiveMode() == GUIMode.VIEW) {
                startX = (int) (event.getSceneX() / gameGrid.getCubeSize());
                startY = (int) (event.getSceneY() / gameGrid.getCubeSize());
            }
        });
        gameGrid.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> {
            if (modeButton.getActiveMode() == GUIMode.VIEW) {
                gameGrid.setTotals();
            }
        });
        gameGrid.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
            int x = (int) (event.getSceneX() / gameGrid.getCubeSize());
            int y = (int) (event.getSceneY() / gameGrid.getCubeSize());
            if ((lastX != x || lastY != y) && x >= 0 && x < canvasSizeX - 1 && y >= 0 && y < canvasSizeY - 1) {
                if (modeButton.getActiveMode() == GUIMode.EDIT) {
                    lastX = x;
                    lastY = y;
                    drawOverCell(x, y, event);
                } else if (modeButton.getActiveMode() == GUIMode.VIEW) {
                    gameGrid.addToOffsetX(startX - x);
                    gameGrid.addToOffsetY(startY - y);
                    gameGrid.drawGrid();
                }
            }

        });
        gameGrid.addEventHandler(ScrollEvent.SCROLL, (ScrollEvent event) -> {     //5, 10, 15, 20, 25, 30, 35, 40, 45, 50
            final int MIN_CUBE_SIZE = 4;
            final int MAX_CUBE_SIZE = 50;
            System.out.print(gameGrid.getCubeSize()+ "-> ");
            if ((gameGrid.getCubeSize() / 3.0) * 2 >= MIN_CUBE_SIZE && event.getDeltaY() < 0 || gameGrid.getCubeSize() * 1.5 <= MAX_CUBE_SIZE && event.getDeltaY() > 0) {
                int x = (int) (event.getSceneX() / gameGrid.getCubeSize());
                int y = (int) (event.getSceneY() / gameGrid.getCubeSize());
                gameGrid.setCubeSize((float) (event.getDeltaY() < 0 ? (gameGrid.getCubeSize() / 11.7) * 10 : gameGrid.getCubeSize() * 1.17));
                canvasSizeX = (int) (gameGrid.getWidth() / gameGrid.getCubeSize());
                canvasSizeY = (int) (gameGrid.getHeight() / gameGrid.getCubeSize());
                System.out.println(gameGrid.getCubeSize());
                gameGrid.drawGrid();
            }
        });
    }

    private void drawOverCell(int x, int y, MouseEvent event) {
        float cubeSize = gameGrid.getCubeSize();
        GraphicsContext gc = gameGrid.getGraphicsContext2D();
        if (game.getValueAt(x, y) == 0 && event.isPrimaryButtonDown()) {
            game.setValueAt(x, y, 1);
            gc.fillRect(x* cubeSize, y* cubeSize, cubeSize, cubeSize);//draws the empty cubes
        } else if (game.getValueAt(x, y) == 1 && event.isSecondaryButtonDown()){
            game.setValueAt(x, y, 0);
            gc.clearRect(x* cubeSize + 1, y* cubeSize + 1,  cubeSize - 1, cubeSize - 1);
            gc.strokeRect(x* cubeSize, y* cubeSize, cubeSize, cubeSize);//draws the empty cubes
        }
    }

    public enum GUIMode {
        EDIT, VIEW
    }
}