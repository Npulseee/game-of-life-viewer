package gameoflife;

import javafx.scene.control.Button;

public class ModeButton extends Button {
    private Controller.GUIMode activeMode;

    public ModeButton() {
        activeMode = Controller.GUIMode.VIEW;
        setText("🖊");
        setOnMouseClicked(e -> {
            switch (activeMode) {
                case VIEW -> {
                    activeMode = Controller.GUIMode.EDIT;
                    setText("👀");
                }
                case EDIT -> {
                    activeMode = Controller.GUIMode.VIEW;
                    setText("🖊");
                }
            }
        });
    }

    public Controller.GUIMode getActiveMode() {
        return activeMode;
    }
}
