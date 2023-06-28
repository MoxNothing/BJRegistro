package com.example.cregistro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Reporte {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}