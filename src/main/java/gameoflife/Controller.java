package gameoflife;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    private enum GUIMode {
        EDIT, VIEW
    }

    @FXML
    private GameCanvas gameCanvas;
    @FXML
    private Button modeButton;

    private GUIMode mode = GUIMode.VIEW;
    private AnimationTimer timer;
    private GameGrid gameGrid;
    private int canvasSizeX;
    private int canvasSizeY;
    private int lastX, lastY;
    private int startX, startY;
    private Configuration savedConfiguration;

    private boolean isPaused = true;

    public void initialize() {
        canvasSizeX = 100;
        canvasSizeY = 100;
        gameGrid = new GameGrid(canvasSizeX, canvasSizeY);

        Pane parent = (Pane) gameCanvas.getParent();
        gameCanvas.widthProperty().bind(parent.widthProperty());
        gameCanvas.heightProperty().bind(parent.heightProperty());
        gameCanvas.setGame(gameGrid);
        gameCanvas.setCubeSize(10);
        gameCanvas.getGraphicsContext2D().setLineWidth(.1);

        savedConfiguration = gameGrid.getCurrentConfiguration();
        addEventHandlerToGameGrid();
        startFreshTimer();
    }

    @FXML
    void onButtonRun() {
        isPaused = false;
    }

    @FXML
    void onButtonReset() {
        gameGrid.loadConfigurationCentered(savedConfiguration);
        gameCanvas.setGame(gameGrid);
    }

    @FXML
    void onButtonClear() {
        gameGrid.clear();
    }

    @FXML
    void onButtonImport() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("src\\main\\resources\\gameoflife"));
        File file = chooser.showOpenDialog(gameCanvas.getScene().getWindow());
        if (file != null && file.getName().endsWith(".rle")){
            Configuration res = RLE_Reader.importConfigurationFromFile(file.getName());
            gameGrid.loadConfigurationCentered(res);
            savedConfiguration = gameGrid.getCurrentConfiguration();
            gameCanvas.updateCanvasPosition();
        }
    }

    @FXML
    void onButtonSave() {
        savedConfiguration = gameGrid.getCurrentConfiguration();
    }

    @FXML
    void onButtonPause() {
        isPaused = true;
    }

    @FXML
    void onButtonMode() {
        mode = mode == GUIMode.VIEW ? GUIMode.EDIT : GUIMode.VIEW;
        modeButton.setText(mode == GUIMode.EDIT ? "👀" : "\uD83D\uDD8A");
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
                    if (!isPaused) {
                        gameGrid.calculateNextGeneration();
                    }
                    gameCanvas.drawGrid();
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    private void addEventHandlerToGameGrid() {

        gameCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if (mode == GUIMode.VIEW) {
                startX = (int) (event.getSceneX() / gameCanvas.getCubeSize());
                startY = (int) (event.getSceneY() / gameCanvas.getCubeSize());
            }
        });
        gameCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> {
            if (mode == GUIMode.VIEW) {
                gameCanvas.setTotals();
            }
        });
        gameCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
            int x = (int) (event.getSceneX() / gameCanvas.getCubeSize());
            int y = (int) (event.getSceneY() / gameCanvas.getCubeSize());
            if ((lastX != x || lastY != y) && x >= 0 && x < canvasSizeX - 1 && y >= 0 && y < canvasSizeY - 1) {
                if (mode == GUIMode.EDIT) {
                    lastX = x;
                    lastY = y;
                    gameGrid.setCell(x, y, event.isPrimaryButtonDown());
                } else {
                    gameCanvas.addToOffsetX(startX - x);
                    gameCanvas.addToOffsetY(startY - y);
                }
            }

        });
        gameCanvas.addEventHandler(ScrollEvent.SCROLL, (ScrollEvent event) -> {     //5, 10, 15, 20, 25, 30, 35, 40, 45, 50
            final int MIN_CUBE_SIZE = 1;
            final int MAX_CUBE_SIZE = 50;
            System.out.print(gameCanvas.getCubeSize() + "-> ");
            if ((gameCanvas.getCubeSize() / 3.0) * 2 >= MIN_CUBE_SIZE && event.getDeltaY() < 0 || gameCanvas.getCubeSize() * 1.5 <= MAX_CUBE_SIZE && event.getDeltaY() > 0) {
                int x = (int) (event.getSceneX() / gameCanvas.getCubeSize());
                int y = (int) (event.getSceneY() / gameCanvas.getCubeSize());
                gameCanvas.setCubeSize((float) (event.getDeltaY() < 0 ? (gameCanvas.getCubeSize() / 11.7) * 10 : gameCanvas.getCubeSize() * 1.17));
                canvasSizeX = (int) (gameCanvas.getWidth() / gameCanvas.getCubeSize());
                canvasSizeY = (int) (gameCanvas.getHeight() / gameCanvas.getCubeSize());
                System.out.println(gameCanvas.getCubeSize());
            }
        });
    }
}