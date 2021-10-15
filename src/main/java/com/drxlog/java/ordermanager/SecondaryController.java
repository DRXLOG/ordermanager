package com.drxlog.java.ordermanager;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void initialize() {
        //Stage primaryStage =
        //primaryStage.getScene().getWindow().setWidth(1300);
        //primaryStage.getScene().getWindow().setHeight(1300);
        //primaryStage.setMinWidth(1500);
        //primaryStage.setMinHeight(1200);
        //primaryStage.setScene();
    }
}