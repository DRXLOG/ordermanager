package com.drxlog.java.ordermanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXML;


public class ChoiceDialog {
    private static Boolean choice = false;
    private static String title = "Окно без названия";
    private static String message = "Нет объявления";

    @FXML
    private Label lblInput;

    @FXML
    private Button btnYes;

    @FXML
    private Button btnNo;

    public void initialize() {
        lblInput.setText(message);
    }

    @FXML
    void actionNo(ActionEvent event) {
        choice = false;
        Stage stage = (Stage) btnNo.getScene().getWindow();
        stage.close();
    }

    @FXML
    void actionYes(ActionEvent event) {
        choice = true;
        Stage stage = (Stage) btnNo.getScene().getWindow();
        stage.close();
    }

    static Boolean display() {
        try {
            FXMLLoader loader = new FXMLLoader(ChoiceDialog.class.getResource("choicedialog.fxml"));
            Scene newScene = new Scene(loader.load());
            Stage choiceWindow = new Stage();
            choiceWindow.setTitle(title);
            choiceWindow.setScene(newScene);
            choiceWindow.showAndWait();

        } catch (IOException ex) {
            // TODO: handle error
            WarningDialog.display("Ошибка!!!", "Не удалось загрузить объект!", ex.getMessage());
        }
        return choice;
    }

    static Boolean display(String title, String message) {
        ChoiceDialog.message = message;
        try {
            FXMLLoader loader = new FXMLLoader(ChoiceDialog.class.getResource("choicedialog.fxml"));
            Scene newScene = new Scene(loader.load());
            Stage choiceWindow = new Stage();
            choiceWindow.setTitle(title);
            choiceWindow.initModality(Modality.APPLICATION_MODAL);
            choiceWindow.setScene(newScene);
            choiceWindow.showAndWait();

        } catch (IOException ex) {
            // TODO: handle error
            WarningDialog.display("Ошибка!!!", "Не удалось загрузить объект!", ex.getMessage());
        }
        return choice;
    }



}
