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
    @FXML
    private Button playButton;

    private GUIMode mode = GUIMode.VIEW;
    private AnimationTimer timer;
    private GameGrid gameGrid;
    private Configuration savedConfiguration;
    private double lastX, lastY;
    private boolean isPaused = true;


    public void initialize() {
        gameGrid = new GameGrid(1000, 1000);

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
        isPaused = !isPaused;
        playButton.setText(isPaused ? "▶" :"⏸");
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
            gameCanvas.setGame(gameGrid);
        }
    }

    @FXML
    void onButtonSave() {
        savedConfiguration = gameGrid.getCurrentConfiguration();
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
                lastX = event.getSceneX();
                lastY = event.getSceneY();
            }
        });

        gameCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
            if (mode == GUIMode.EDIT) {
                gameCanvas.drawOverPosition(event.getSceneX(), event.getSceneY(), event.isPrimaryButtonDown());
            } else {
                double deltaX = lastX - event.getSceneX();
                double deltaY = lastY - event.getSceneY();
                gameCanvas.addDragOffset(deltaX, deltaY);
                lastX = event.getSceneX();
                lastY = event.getSceneY();
            }
        });
        gameCanvas.addEventHandler(ScrollEvent.SCROLL, (ScrollEvent event) -> {     //5, 10, 15, 20, 25, 30, 35, 40, 45, 50
            final double MIN_CUBE_SIZE = 4;
            final double MAX_CUBE_SIZE = 30;
            final double STEP = 2;
            double newSize = event.getDeltaY() < 0 ? gameCanvas.getCubeSize() - STEP : gameCanvas.getCubeSize() + STEP;
            gameCanvas.setCubeSize(Math.clamp(newSize, MIN_CUBE_SIZE, MAX_CUBE_SIZE));
        });
    }
}