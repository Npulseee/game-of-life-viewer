package com.example.gameoflife;

import javafx.scene.control.Button;

public class ModeButton extends Button {
    private HelloController.GUIMode activeMode;

    public ModeButton() {
        activeMode = HelloController.GUIMode.VIEW;
        setText("🖊");
        setOnMouseClicked(e -> {
            switch (activeMode) {
                case VIEW -> {
                    activeMode = HelloController.GUIMode.EDIT;
                    setText("👀");
                }
                case EDIT -> {
                    activeMode = HelloController.GUIMode.VIEW;
                    setText("🖊");
                }
            }
        });
        setStyle("-fx-pref-height: 35px;\n" +
                "    -fx-pref-width: 50px; -fx-font-size: 20");
    }

    public HelloController.GUIMode getActiveMode() {
        return activeMode;
    }
}
