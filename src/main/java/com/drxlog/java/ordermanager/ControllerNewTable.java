package com.drxlog.java.ordermanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class ControllerNewTable {

    @FXML
    private Button btnYes;

    @FXML
    private Button btnNo;

    @FXML
    void actionBtnNo(ActionEvent event) {

    }

    @FXML
    void actionBtnYes(ActionEvent event) {
        Parent parentStage =  btnYes.getParent();

    }

}
