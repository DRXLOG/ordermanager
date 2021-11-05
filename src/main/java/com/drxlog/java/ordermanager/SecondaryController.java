package com.drxlog.java.ordermanager;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.sun.jna.platform.win32.W32FileUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.buffer.CircularBufferInputStream;

public class SecondaryController {

    @FXML
    private Label lblOrder;

    @FXML
    private TextField tfFindOrder;

    @FXML
    private Button btnSetDirOrder;

    @FXML
    private TextField tfDirOrder;

    @FXML
    private Button btnUpDirOrder;

    @FXML
    private Button btnRefreshTableOrder;

    @FXML
    private TableView<FileInfo> tblOrder;

    @FXML
    private TableColumn<FileInfo, String> columnNameInTableOrder;

    @FXML
    private Label lblCountElementToTableOrder;

    @FXML
    private Label lblCountSelectElementToTableOrder;

    @FXML
    private Button btnDeleteSelectElementInTableOrder;

    @FXML
    private Button btnClearTableOrder;

    @FXML
    private TableView<FileInfo> tblPreviewOrder;

    @FXML
    private TableColumn<FileInfo, String> columnNameInTablePreviewOrder;

    @FXML
    private Label lblCountElementToTablePreviewOrder;

    @FXML
    private Label lblCountSelectElementToTablePreviewOrder;

    @FXML
    private Button btnDeleteSelectElementInTablePreviewOrder;

    @FXML
    private Button btnClearTablePreviewOrder;

    @FXML
    private Button btnMoveElementToRight;

    @FXML
    private Button btnMoveElementToLeft;

    @FXML
    private Button btnCreateTable;

    @FXML
    private Button btnOpenSettings;

    @FXML
    private Label lblJob;

    @FXML
    private Button btnSetDirJob;

    @FXML
    private TextField tfDirJob;

    @FXML
    private Button btnUpDirJob;

    @FXML
    private Button btnRefreshTableJob;

    @FXML
    private TableView<FileInfo> tblJob;

    @FXML
    private TableColumn<FileInfo, String> columnNameInTableJob;

    @FXML
    private Label lblCountElementToTableJob;

    @FXML
    private Label lblCountSelectElementToTableJob;

    @FXML
    private Button btnDeleteSelectElementInTableJob;

    @FXML
    private Button btnClearTableJob;

    @FXML
    private TableView<FileInfo> tblPreviewJob;

    @FXML
    private TableColumn<FileInfo, String> columnNameInTablePreviewJob;

    @FXML
    private Label lblCountElementToTablePreviewJob;

    @FXML
    private Label lblCountSelectElementToTablePreviewJob;

    @FXML
    private Button btnDeleteSelectElementInTablePreviewJob;

    @FXML
    private Button btnClearTablePreviewJob;

    @FXML
    private ListView<String> lvNotificationConsole;

    public ObservableList<String> newMessage = FXCollections.observableArrayList();


    public void initialize() {
        columnNameInTableOrder.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIdName()));
        TableView.TableViewSelectionModel<FileInfo> selectionModel = tblOrder.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<FileInfo, String> countFiles = new TableColumn<FileInfo, String>("Количество");
        countFiles.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFilesCount()));
        tblOrder.getColumns().addAll(countFiles);


        columnNameInTablePreviewOrder.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIdName()));
        TableView.TableViewSelectionModel<FileInfo> selectionModelTableView = tblPreviewOrder.getSelectionModel();
        selectionModelTableView.setSelectionMode(SelectionMode.MULTIPLE);

        columnNameInTableJob.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIdName()));
        TableView.TableViewSelectionModel<FileInfo> selectionTableJob = tblJob.getSelectionModel();
        selectionTableJob.setSelectionMode(SelectionMode.MULTIPLE);

        columnNameInTablePreviewJob.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIdName()));
        TableView.TableViewSelectionModel<FileInfo> selectionModelTableViewJob = tblPreviewJob.getSelectionModel();
        selectionModelTableViewJob.setSelectionMode(SelectionMode.MULTIPLE);

        tblOrder.setEditable(true);
        columnNameInTableOrder.setCellFactory(TextFieldTableCell.forTableColumn());

        tblJob.setEditable(true);
        columnNameInTableJob.setCellFactory(TextFieldTableCell.forTableColumn());

        try {
            File file = new File("config.properties");
            Properties properties = new Properties();
            if (file.exists()) {
                properties.load(new FileReader(file));
            } else {
                properties.setProperty("ORDER_PATH", ".");
                properties.setProperty("OPERATOR_PATH", ".");
                properties.setProperty("OPERATOR_NAME", "KK");
                FileWriter writer = new FileWriter(file);
                properties.store(writer, "Настройки программы");
            }
            tfDirOrder.setText(properties.getProperty("ORDER_PATH"));
            tfDirJob.setText(properties.getProperty("OPERATOR_PATH"));
            updateTableOrder(Paths.get(tfDirOrder.getText()));
            updateTableJob(Paths.get(tfDirJob.getText()));
        } catch (IOException e) {
            outConsole("Не удалось загрузить файл конфигурации!");
            System.out.println(e.getMessage());
        }

        tblOrder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    try {
                        File file = new File(tblOrder.getSelectionModel().getSelectedItem().getFilePath());
                        if (file.isDirectory()) {
                            tfDirOrder.setText(tblOrder.getSelectionModel().getSelectedItem().getFilePath());
                            updateTableOrder(file.toPath());
                            outConsole("Вы Открыли каталог!!!");
                        } else {
                            outConsole("Файлы здесь не открываются!!!");
                        }
                    } catch (NullPointerException e) {
                        outConsole("Куда ты тыкаешь? Здесь пусто!");
                    }
                }
                if (event.getClickCount() == 1) {
                    try {
                        File file = new File(tblOrder.getSelectionModel().getSelectedItem().getFilePath());
                        if (file.isDirectory()) {
                            updateTablePreviewOrder(file.toPath());
                        } else {
                            outConsole("Это файл! превью нет!");
                        }
                        lblCountSelectElementToTableOrder.setText("Выбрано "+String.valueOf(tblOrder.getSelectionModel().getSelectedItems().size())+" элем.");
                    } catch (NullPointerException e) {
                        outConsole("Куда ты тыкаешь? Здесь пусто!");
                    }
                }
            }
        });

        tblPreviewOrder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    lblCountSelectElementToTablePreviewOrder.setText("Выбрано "+String.valueOf(tblPreviewOrder.getSelectionModel().getSelectedItems().size())+" элем.");
                }
            }
        });

        tblJob.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblCountSelectElementToTableJob.setText("Выбрано "+String.valueOf(tblJob.getSelectionModel().getSelectedItems().size())+" элем.");
                if (event.getClickCount() == 1) {
                    try {
                        File file = new File(tblJob.getSelectionModel().getSelectedItem().getFilePath());
                        if (file.isDirectory()) {
                            updateTablePreviewJob(file.toPath());
                        } else {
                            outConsole("Это файл! превью нет!");
                        }
                        //labelSelOrder.setText("Выбрано "+String.valueOf(tableJob.getSelectionModel().getSelectedItems().size())+" элем.");
                    } catch (NullPointerException e) {
                        outConsole("Куда ты тыкаешь? Здесь пусто!");
                    }
                }
                if (event.getClickCount() == 2) {
                    try {
                        File file = new File(tblJob.getSelectionModel().getSelectedItem().getFilePath());
                        if (file.isDirectory()) {
                            tfDirJob.setText(tblJob.getSelectionModel().getSelectedItem().getFilePath());
                            updateTableJob(file.toPath());
                            outConsole("Вы Открыли каталог!!!");
                        } else {
                            outConsole("Файлы здесь не открываются!!!");
                        }
                    } catch (NullPointerException e) {
                        outConsole("Куда ты тыкаешь? Здесь пусто!");
                    }
                }
            }
        });

        tblPreviewJob.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    lblCountSelectElementToTablePreviewJob.setText("Выбрано "+String.valueOf(tblPreviewJob.getSelectionModel().getSelectedItems().size())+" элем.");
                }
            }
        });

        columnNameInTableOrder.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FileInfo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FileInfo, String> event) {
                if (event.getNewValue().equals(tblOrder.getSelectionModel().getSelectedItem().getIdName())) {
                    System.out.println("Одинаковые значения!");
                } else {
                    if (ChoiceDialog.display("Переименовать?", "Переименовать элемент?")) {
                        TablePosition<FileInfo, String> pos = event.getTablePosition();
                        FileInfo oldItem = event.getTableView().getItems().get(pos.getRow());
                        File oldFile = new File(oldItem.getFilePath());
                        File newFile = new File(String.valueOf(Paths.get(oldItem.getFilePath()).getParent()+File.separator+event.getNewValue()));

                        if (oldFile.exists()) {
                            if(oldFile.renameTo(newFile)){
                                outConsole("Файл "+oldFile+" успешно переименован в "+newFile);
                            }else{
                                WarningDialog.display("Ошибка!", "Файл не был переименован!", "Возможно открыт в другой программе!");
                            }
                        } else {
                            WarningDialog.display("Ошибка!", "Файла который вы пытаетесь переименовать не существует!", "Такого файла нет!");
                        }

                        //System.out.println("Старое значение! "+oldItem.getIdName()+" Новое значение"+newIdName);
                        //System.out.println("Старый путь! "+oldFile.getPath()+" Новый путь!"+newFile.getPath());
                        updateTableOrder(Paths.get(tfDirOrder.getText()));
                    } else {
                        System.out.println("Переименование отменено!");
                    }
                }


            }
        });

        columnNameInTableJob.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FileInfo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FileInfo, String> event) {
                if (event.getNewValue().equals(tblJob.getSelectionModel().getSelectedItem().getIdName())) {
                    System.out.println("Одинаковые значения!");
                } else {
                    if (ChoiceDialog.display("Переименовать?", "Переименовать элемент?")) {
                        TablePosition<FileInfo, String> pos = event.getTablePosition();
                        FileInfo oldItem = event.getTableView().getItems().get(pos.getRow());
                        File oldFile = new File(oldItem.getFilePath());
                        File newFile = new File(String.valueOf(Paths.get(oldItem.getFilePath()).getParent()+File.separator+event.getNewValue()));

                        if (oldFile.exists()) {
                            if(oldFile.renameTo(newFile)){
                                outConsole("Файл "+oldFile+" успешно переименован в "+newFile);
                            }else{
                                WarningDialog.display("Ошибка!", "Файл не был переименован!", "Возможно открыт в другой программе!");
                            }
                        } else {
                            WarningDialog.display("Ошибка!", "Файла который вы пытаетесь переименовать не существует!", "Такого файла нет!");
                        }



                        System.out.println("Старое значение! "+oldItem.getIdName()+" Новое значение"+event.getNewValue());
                        System.out.println("Старый путь! "+oldFile.getPath()+" Новый путь!"+newFile.getPath());
                        updateTableJob(Paths.get(tfDirJob.getText()));

                    } else {
                        System.out.println("Переименование отменено!");
                    }
                }


            }
        });



    }

    @FXML
    void keyPressedTableOrder(KeyEvent event) {
        if (event.getCode().equals(KeyCode.F5)) {
            updateTableOrder(Paths.get(String.valueOf(tfDirOrder.getText())));
            outConsole("Обновлена таблица Order");
        }
        if (event.getCode().equals(KeyCode.BACK_SPACE)) {
            upDirOrder();
        }
        if (event.getCode().equals(KeyCode.DELETE)) {
            List<String> pathsList = new ArrayList<>();
            for (FileInfo itemDelete : tblOrder.getSelectionModel().getSelectedItems()) {
                pathsList.add(itemDelete.getFilePath());
            }
            if (ChoiceDialog.display("Удаление элементов", "Удалить элементы?")) {
                for (String pathDelete : pathsList) {
                    try {
                        if (Files.isDirectory(Paths.get(pathDelete))){
                            Path tmp = Paths.get(Paths.get(pathDelete).getRoot()+File.separator+"tmp");
                            System.out.println(tmp);
                            //FileUtils.deleteDirectory(new File("D:\\test\\2021 год\\Ноябрь\\02.11\\Кирилл\\1"));
                            if (Files.exists(tmp)){
                                System.out.println("tmp есть!");
                                Files.move(Paths.get(pathDelete), Paths.get(tmp+File.separator+Paths.get(pathDelete).getFileName()));
                                FileUtils.deleteDirectory(new File(String.valueOf(Paths.get(tmp+ File.separator+Paths.get(pathDelete).getFileName()))));
                                CircularBuffer.action(new FileDelete(Paths.get("C://"), Paths.get("C://")));
                            } else {
                                System.out.println("tmp нет!");
                                Files.createDirectory(tmp);
                                Files.move(Paths.get(pathDelete), Paths.get(tmp+File.separator+Paths.get(pathDelete).getFileName()));
                                FileUtils.deleteDirectory(new File(String.valueOf(String.valueOf(Paths.get(tmp+ File.separator+Paths.get(pathDelete).getFileName())))));

                            }

//                            W32FileUtils w32FileUtils = (W32FileUtils) W32FileUtils.getInstance();
//                            if (w32FileUtils.hasTrash()) {
//                                w32FileUtils.moveToTrash( new File[] {new File(String.valueOf(tmpFilePath))});
//                                updateTableOrder(Paths.get(tfDirOrder.getText()));
//                            }
                        } else {
                            if (Files.deleteIfExists(Paths.get(pathDelete))) {
                                outConsole(Paths.get(pathDelete).getFileName()+" удален!");
                                updateTableOrder(Paths.get(tfDirOrder.getText()));
                            } else {
                                WarningDialog.display("Ошибка", "Файл не может быть удален!", "Файл не существует или произошла какая-то ошибка при удалении");
                            }
                        }
                    } catch (IOException e) {
                        WarningDialog.display("Ошибка", "Файл не может быть удален!", e.getMessage()+e.getStackTrace());
                    }

                }
            }
        }
        if (event.isControlDown() && (event.getCode().equals(KeyCode.Z))) {
            System.out.println("КОНТРОЛ З");
            //String data = CircularBuffer.recover(new FileDelete(Paths.get("C://"), Paths.get("C://")), String.class);
            System.out.println(CircularBuffer.<String>recover("ХУЙ"));
        }

    }

    @FXML
    void keyPressedTableJob(KeyEvent event) {
        if (event.getCode().equals(KeyCode.F5)) {
            updateTableJob(Paths.get(String.valueOf(tfDirJob.getText())));
            outConsole("Обновлена таблица Order");
        }
        if (event.getCode().equals(KeyCode.BACK_SPACE)) {
            upDirJob();
        }
        if (event.getCode().equals(KeyCode.DELETE)) {
            if (ChoiceDialog.display("Удаление элементов", "Удалить элементы?")) {
                for (FileInfo item : tblJob.getSelectionModel().getSelectedItems()) {
                    File deleteFile = new File(item.getFilePath());
                    if (deleteFile.delete()) {
                        outConsole(deleteFile.getName()+" удален!");
                        updateTableJob(Paths.get(tfDirJob.getText()));
                    } else {
                        WarningDialog.display("Ошибка", "Файл не может быть удален!", "Файл не существует или произошла какая-то ошибка при удалении");
                    }
                }
            }
        }
    }

    @FXML
    void clearTableJob(ActionEvent event) {
        tblJob.getItems().clear();
    }

    @FXML
    void clearTableOrder(ActionEvent event) {
        tblOrder.getItems().clear();
    }

    @FXML
    void clearTablePreviewJob(ActionEvent event) {
        tblPreviewJob.getItems().clear();
    }

    @FXML
    void clearTablePreviewOrder(ActionEvent event) {
        tblPreviewOrder.getItems().clear();
    }

    @FXML
    void createTable(ActionEvent event) {

    }

    @FXML
    void deleteSelectElementInTableJob(ActionEvent event) {

    }

    @FXML
    void deleteSelectElementInTableOrder(ActionEvent event) {

    }

    @FXML
    void deleteSelectElementInTablePreviewJob(ActionEvent event) {
        ObservableList<FileInfo> deleteItem = FXCollections.observableArrayList();
        deleteItem.addAll(tblPreviewJob.getSelectionModel().getSelectedItems());
        tblPreviewJob.getItems().removeAll(deleteItem);
    }

    @FXML
    void deleteSelectElementInTablePreviewOrder(ActionEvent event) {
        ObservableList<FileInfo> deleteItem = FXCollections.observableArrayList();
        deleteItem.addAll(tblPreviewOrder.getSelectionModel().getSelectedItems());
        tblPreviewOrder.getItems().removeAll(deleteItem);
    }

    @FXML
    void moveElementToLeft(ActionEvent event) {

    }

    @FXML
    void moveElementToRight(ActionEvent event) {

    }

    @FXML
    void openSettings(ActionEvent event) {

    }

    @FXML
    void refreshTableJob(ActionEvent event) {
        updateTableJob(Paths.get(tfDirJob.getText()));
    }

    @FXML
    void refreshTableOrder(ActionEvent event) {
        updateTableOrder(Paths.get(tfDirOrder.getText()));
    }

    @FXML
    void setDirJob(ActionEvent event) {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            tfDirJob.setText(String.valueOf(selectedDirectory));
            updateTableJob(selectedDirectory.toPath());
        }
        outConsole("Установлена директория оператора");
    }

    @FXML
    void setDirOrder(ActionEvent event) {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            tfDirOrder.setText(String.valueOf(selectedDirectory));
            updateTableOrder(selectedDirectory.toPath());
        }
        outConsole("Установлена директория оператора");
    }

    @FXML
    void upDirJob() {
        Path upperPath = Paths.get(tfDirJob.getText()).getParent();
        if (upperPath != null) {
            tfDirJob.setText(upperPath.toString());
            updateTableJob(upperPath);
            outConsole("Открыт каталог выше!");
        } else {
            outConsole("Выше нет каталога!");
        }
    }

    @FXML
    void upDirOrder() {
        Path upperPath = Paths.get(tfDirOrder.getText()).getParent();
        if (upperPath != null) {
            tfDirOrder.setText(upperPath.toString());
            updateTableOrder(upperPath);
            outConsole("Открыт каталог выше тест коммит!");
        } else {
            outConsole("Выше нет каталога!");
        }
    }

    void updateTableOrder(Path path) {
        try {
            tblOrder.getItems().clear();
            if (path.getFileName().toString().equals("Заказ")) {
                lblOrder.setTextFill(Color.web("#19E619"));
                lblOrder.setText("Текущий заказ [Активно]");

                File dir = new File(path.toString());
                List<Path> list = new ArrayList<>();
                for (File item : dir.listFiles()){
                    if (item.isFile() | item.getName().equals("+Комплит")) {
                        outConsole("Не рабочий элемент "+item.getName()+" пропущен!");
                    } else {
                        list.add(Paths.get(item.getPath()));
                    }
                }

                tblOrder.getItems().addAll(list.stream().map(FileInfo::new).collect(Collectors.toList()));
                tblOrder.sort();
                lblCountElementToTableOrder.setText("Элементов: "+tblOrder.getItems().size());
                lblCountSelectElementToTableOrder.setText("Выбрано 0 элем.");
            } else {
                lblOrder.setTextFill(Color.web("#FC1F0F"));
                lblOrder.setText("Заказ [Не активно]");
                tblOrder.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
                tblOrder.sort();
                lblCountElementToTableOrder.setText("Элементов: "+tblOrder.getItems().size());
                lblCountSelectElementToTableOrder.setText("Выбрано 0 элем.");
            }
        } catch (IOException e) {
            outConsole("Не увдлось обновить таблицу Order");
        }

    }

    void updateTablePreviewOrder(Path path) {
        try {
            tblPreviewOrder.getItems().clear();
            tblPreviewOrder.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            tblPreviewOrder.sort();
            lblCountElementToTablePreviewOrder.setText("Элементов: "+tblPreviewOrder.getItems().size());
            lblCountSelectElementToTableOrder.setText("Выбрано 0 элем.");
        } catch (IOException e) {
            outConsole("Не удалось обновить таблицу PreviewOrder");
        }
    }

    void updateTableJob(Path path) {
        try {
            tblJob.getItems().clear();
            //tableModelViewJob.getItems().clear();
            //System.out.println(path.getFileName());
            if (path.getFileName().toString().equals("Кирилл")) {
                lblJob.setTextFill(Color.web("#19E619"));
                lblJob.setText("Рабочая папка [Активно]");
                File dir = new File(path.toString());
                List<Path> list = new ArrayList<>();
                //System.out.println(dir.listFiles());
                for (File item : dir.listFiles()){
                    //System.out.println(item.getName());
                    if (item.isFile() | item.getName().equals("+") | item.getName().equals("PR12") | item.getName().equals("PR28") | item.getName().equals("PR35")) {
                        outConsole("Не рабочий элемент "+item.getName()+" пропущен!");
                    } else {
                        list.add(Paths.get(item.getPath()));
                    }
                }
                Stream<Path> stream = list.stream();
                tblJob.getItems().addAll(stream.map(FileInfo::new).collect(Collectors.toList()));
                tblJob.sort();
                lblCountElementToTableJob.setText("Элементов: "+tblJob.getItems().size());
                lblCountSelectElementToTableJob.setText("Выбрано 0 элем.");
            } else {
                lblJob.setTextFill(Color.web("#FC1F0F"));
                lblJob.setText("Рабочая папка [Не активно]");
                tblJob.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
                tblJob.sort();
                lblCountElementToTableJob.setText("Элементов: "+tblJob.getItems().size());
                lblCountSelectElementToTableJob.setText("Выбрано 0 элем.");
            }
        } catch (IOException e) {
            outConsole("Не удалось обновить таблицу Job");
        }
    }

    void updateTablePreviewJob(Path path) {
        try {
            tblPreviewJob.getItems().clear();
            tblPreviewJob.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            tblPreviewJob.sort();
            lblCountElementToTablePreviewJob.setText("Элементов: "+tblPreviewJob.getItems().size());
            lblCountSelectElementToTablePreviewJob.setText("Выбрано 0 элем.");
        } catch (IOException e) {
            outConsole("Не удалось обновить таблицу PreviewJob");
        }
    }

    void outConsole(String message) {
        newMessage.add(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " " + message);
        lvNotificationConsole.setItems(newMessage);
        lvNotificationConsole.scrollTo(lvNotificationConsole.getItems().size());
    }

}
