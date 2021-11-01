package com.drxlog.java.ordermanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WarningDialog {
    private static String title = "Окно без названия";
    private static String message = "Нет объявления";
    private static String error = "Нет ошибки";

    @FXML
    private Label lblError;

    @FXML
    private TextArea textAreaError;

    @FXML
    private Button btnOk;

    public void initialize() {
        lblError.setText(message);
        textAreaError.setText(error);
    }

    @FXML
    void actionOk(ActionEvent event) {
        Stage stage = (Stage) lblError.getScene().getWindow();
        stage.close();
    }

    static void display() {
        try {
            FXMLLoader loader = new FXMLLoader(ChoiceDialog.class.getResource("warningdialog.fxml"));
            Scene newScene = new Scene(loader.load());
            Stage warningWindow = new Stage();
            warningWindow.setTitle(title);
            warningWindow.initModality(Modality.APPLICATION_MODAL);
            warningWindow.setScene(newScene);
            warningWindow.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не известная ошибка", ButtonType.OK);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
    }

    static void display(String title, String message, String error) {
        WarningDialog.message = message;
        WarningDialog.error = error;
        try {
            FXMLLoader loader = new FXMLLoader(ChoiceDialog.class.getResource("warningdialog.fxml"));
            Scene newScene = new Scene(loader.load());
            Stage warningWindow = new Stage();
            warningWindow.setTitle(title);
            warningWindow.initModality(Modality.APPLICATION_MODAL);
            warningWindow.setScene(newScene);
            warningWindow.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не известная ошибка", ButtonType.OK);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
    }

}