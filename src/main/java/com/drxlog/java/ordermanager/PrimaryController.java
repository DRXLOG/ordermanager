package com.drxlog.java.ordermanager;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrimaryController {

    @FXML
    private TableView<FileInfo> tableOrder;

    @FXML
    private TableColumn<FileInfo, String> columnIdOrder;

    //@FXML
    private TableColumn<FileInfo, String> columnModelOrder;

    @FXML
    private TableView<FileInfo> tableModelView;

    @FXML
    private TableColumn<FileInfo, String> columnIdModelView;

    @FXML
    private TableView<FileInfo> tableJob;

    @FXML
    private TableColumn<FileInfo, String> columnIdJob;

    @FXML
    private TableView<FileInfo> tableModelViewJob;

    @FXML
    private TableColumn<FileInfo, String> columnIdModelViewJob;

    @FXML
    private Button btnMoveRightElements;

    @FXML
    private Button btnMoveLeftElements;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSetDirOrder;

    @FXML
    private TextField fieldDirOrder;

    @FXML
    private Button btnUpDirOrder;

    @FXML
    private Button btnRefreshOrder;

    @FXML
    private ListView<String> mainConsole;

    @FXML
    private Button btnCreateJobTable;

    @FXML
    private Button btnClearJobTable;

    @FXML
    private Button btnSetDirJob;

    @FXML
    private TextField fieldDirJob;

    @FXML
    private Button btnUpDirJob;

    @FXML
    private Button btnRefreshJob;

    @FXML
    private Button btnAddAuthor;

    @FXML
    private Button btnOpenMagic;

    @FXML
    private Button btnClearTableViewJob;

    @FXML
    private Label labelOrder;

    @FXML
    private Label labelView;

    @FXML
    private Label labelJob;

    @FXML
    private Label labelSelOrder;

    @FXML
    private Label labelSelView;

    @FXML
    private Label labelSelJob;

    @FXML
    private Label labelOrderDir;

    @FXML
    private Label labelJobDir;

    @FXML
    private Label labelViewJob;

    @FXML
    private Label labelSelViewJob;

    public ObservableList<String> newLangs = FXCollections.observableArrayList();

    @FXML
    void createJobTable(ActionEvent event) {
        try {

            List<String> list = new ArrayList<>();
            for (FileInfo item : tableModelViewJob.getItems()) {
                list.add(item.getIdName().replace(".stl", ""));
            }
            Path newDir = Paths.get(fieldDirJob.getText()+ File.separator+new DirRename(list).getCompleteName());
            Files.createDirectory(newDir);
            for (FileInfo item : tableModelViewJob.getItems()) {
                //System.out.println(item.getFilePath());
                String idName = Paths.get(item.getFilePath()).getParent().getFileName().toString();
                File completeDir = new File(String.valueOf(Paths.get(fieldDirOrder.getText()+ File.separator+"+??????????????"+File.separator+idName)));
                if (completeDir.exists()) {
                    Files.copy(Paths.get(item.getFilePath()), Paths.get(fieldDirOrder.getText()+File.separator+"+??????????????"+File.separator+idName+File.separator+item.getIdName()));
                } else {
                    Files.createDirectory(completeDir.toPath());
                    Files.copy(Paths.get(item.getFilePath()), Paths.get(fieldDirOrder.getText()+File.separator+"+??????????????"+File.separator+idName+File.separator+item.getIdName()));
                }

                Files.copy(Paths.get(item.getFilePath()), Paths.get(newDir.toString()+File.separator+item.getIdName()));
                Files.deleteIfExists(Paths.get(item.getFilePath()));
            }

            //System.out.println(new DirRename(list).getCompleteName());
            updateList(Paths.get(fieldDirJob.getText()),"job");
            tableModelViewJob.getItems().clear();
        } catch (IOException e) {
            outConsole("???? ?????????????? ?????????????? ????????????????????!");
        }

        outConsole("???????????????????????? ???????????????????? ????????????");
    }



    @FXML
    void openMagic(ActionEvent event) throws IOException, InterruptedException {
        //Runtime.getRuntime().exec("C:\\Program Files\\Materialise\\Magics 20.03\\Magics.exe");
        //ProcessHandle.allProcesses().forEach(process -> System.out.println(processDetails(process)));
        outConsole("?????? ???? ?????????????? MAGIC");
        System.out.println("Creating Process");


        //ProcessBuilder builder = new ProcessBuilder("C:\\\\Program Files\\\\Materialise\\\\Magics 20.03\\\\Magics.exe");



        //Process pro = builder.start();

        //Writer w = new OutputStreamWriter(pro.getOutputStream(), "UTF-8");
        //FileOutputStream newoutput = new FileOutputStream("D:\\test\\2021 ??????\\??????????????\\07.10\\????????????\\a6458_12n13v+a10636_7n7v8n8v9n9v+KK\\a6458_12n.stl");
        //Thread.sleep(10000);
        //pro.getInputStream().readAllBytes();
        //pro.getOutputStream().write(newoutput.);
        //w.write(String.valueOf(newoutput));
        //System.out.println(pro.getInputStream());
        //builder.redirectOutput()
        // ?????????? 10 ????????????

        System.out.println("Waiting");
        //Thread.sleep(10000);
        // ?????????? ??????????????
        //pro.destroy();

        System.out.println("Process destroyed");
    }



    @FXML
    void addAuthor(ActionEvent event) {
        for (FileInfo item : tableOrder.getSelectionModel().getSelectedItems()) {
            File dir = new File(item.getFilePath());
            dir.renameTo(new File(dir.getPath()+" KK"));
        }
        updateList(Paths.get(fieldDirOrder.getText()), "order");
        outConsole("???????????? ???????????????? ??????????");
    }

    @FXML
    void clearTableViewJob(ActionEvent event) {
        tableModelViewJob.getItems().clear();
    }

    @FXML
    void moveRightElements(ActionEvent event) {

        try {
            tableModelViewJob.getItems().addAll(tableModelView.getSelectionModel().getSelectedItems());
            labelViewJob.setText("??????????????????: "+tableModelViewJob.getItems().size());
            labelSelViewJob.setText("?????????????? 0 ????????.");

            tableModelView.getItems().removeAll(tableModelView.getSelectionModel().getSelectedItems());
            labelView.setText("??????????????????: "+tableModelView.getItems().size());
            labelSelView.setText("?????????????? 0 ????????.");


//            File dirComplete = new File(fieldDirOrder.getText()+File.separator+"+??????????????");
//            Files.createDirectories(Paths.get(dirComplete.toString()+File.separator+Paths.get(tableModelView.getSelectionModel().getSelectedItem().getFilePath()).getParent().getFileName()));
//
//            Path viewPath = Paths.get(tableModelView.getSelectionModel().getSelectedItem().getFilePath()).getParent();
//            for (FileInfo item : tableModelView.getSelectionModel().getSelectedItems()) {
//                Files.copy(Paths.get(item.getFilePath()), Paths.get(dirComplete.toString()+File.separator+Paths.get(tableModelView.getSelectionModel().getSelectedItem().getFilePath()).getParent().getFileName()+File.separator+item.getIdName()));
//                Files.delete(Paths.get(item.getFilePath()));
//            }
//
//            if (Files.list(viewPath).count() == 0) {
//                Files.deleteIfExists(viewPath);
//            } else {
//                updateList(Paths.get(fieldDirOrder.getText()), "order");
//                updateList(viewPath, "view");
//            }

            outConsole("???????????????????? ???????????????? ???????????????????? ????????????");
        } catch (NullPointerException e) {
            outConsole("?????????????? NewTable.fxml ???? ????????????????????");
        }
    }

    @FXML
    void moveLeftElements(ActionEvent event) {
        tableModelView.getItems().addAll(tableModelViewJob.getSelectionModel().getSelectedItems());
        tableModelView.sort();
        labelView.setText("??????????????????: "+tableModelView.getItems().size());
        labelSelView.setText("?????????????? 0 ????????.");

        tableModelViewJob.getItems().removeAll(tableModelViewJob.getSelectionModel().getSelectedItems());
        tableModelViewJob.sort();
        labelViewJob.setText("??????????????????: "+tableModelViewJob.getItems().size());
        labelSelViewJob.setText("?????????????? 0 ????????.");
        outConsole("?????????????????? ???????????????? ???????????????????? ??????????");
    }

    @FXML
    void openSettings(ActionEvent event) throws IOException {
        App.setRoot("secondary");
        //outConsole("?????????????? ??????????????????");
    }

    @FXML
    void refreshJob(ActionEvent event) {
        updateList(Paths.get(fieldDirJob.getText()),"job");
        outConsole("?????????????????? ?????????????? ??????????????????");
    }

    @FXML
    void refreshOrder(ActionEvent event) {
        updateList(Paths.get(fieldDirOrder.getText()),"order");
        outConsole("?????????????????? ?????????????? ??????????");
    }

    @FXML
    void setDirJob(ActionEvent event) {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            fieldDirJob.setText(String.valueOf(selectedDirectory));
            updateList(selectedDirectory.toPath(),"job");
        }
        outConsole("?????????????????????? ???????????????????? ??????????????????");
    }

    @FXML
    void setDirOrder(ActionEvent event) {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            fieldDirOrder.setText(String.valueOf(selectedDirectory));
            updateList(selectedDirectory.toPath(),"order");
        }
        outConsole("?????????????????????? ???????????????????? ??????????");
    }

    @FXML
    void upDirJob(ActionEvent event) {
        Path upperPath = Paths.get(fieldDirJob.getText()).getParent();
        if (upperPath != null) {
            fieldDirJob.setText(upperPath.toString());
            updateList(upperPath,"job");
            outConsole("???????????? ?????????????? ????????!");
        } else {
            outConsole("???????? ?????? ????????????????!");
        }
    }

    @FXML
    void upDirOrder(ActionEvent event) {
        Path upperPath = Paths.get(fieldDirOrder.getText()).getParent();
        if (upperPath != null) {
            fieldDirOrder.setText(upperPath.toString());
            updateList(upperPath,"order");
            outConsole("???????????? ?????????????? ???????? ???????? ????????????!");
        } else {
            outConsole("???????? ?????? ????????????????!");
        }

    }

    @FXML
    void clearJobTable(ActionEvent event) {
        outConsole("?????????????? ?????????????? ????????????");
    }

    public void initialize() {

        columnIdOrder.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIdName()));
        TableView.TableViewSelectionModel<FileInfo> selectionModel = tableOrder.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<FileInfo, String> countFiles = new TableColumn<FileInfo, String>("????????????????????");
        countFiles.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFilesCount()));
        tableOrder.getColumns().addAll(countFiles);

        columnIdModelView.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIdName()));
        TableView.TableViewSelectionModel<FileInfo> selectionModelTableView = tableModelView.getSelectionModel();
        selectionModelTableView.setSelectionMode(SelectionMode.MULTIPLE);

        columnIdJob.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIdName()));
        TableView.TableViewSelectionModel<FileInfo> selectionTableJob = tableJob.getSelectionModel();
        selectionTableJob.setSelectionMode(SelectionMode.MULTIPLE);

        columnIdModelViewJob.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIdName()));
        TableView.TableViewSelectionModel<FileInfo> selectionModelTableViewJob = tableModelViewJob.getSelectionModel();
        selectionModelTableViewJob.setSelectionMode(SelectionMode.MULTIPLE);

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
                properties.store(writer, "?????????????????? ??????????????????");
            }
            fieldDirOrder.setText(properties.getProperty("ORDER_PATH"));
            fieldDirJob.setText(properties.getProperty("OPERATOR_PATH"));
            updateList(Paths.get(fieldDirOrder.getText()),"order");
            updateList(Paths.get(fieldDirJob.getText()),"job");
        } catch (IOException e) {
            outConsole("???? ?????????????? ?????????????????? ???????? ????????????????????????!");
            System.out.println(e.getMessage());
        }


        tableOrder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    try {
                        File file = new File(tableOrder.getSelectionModel().getSelectedItem().getFilePath());
                        if (file.isDirectory()) {
                            fieldDirOrder.setText(tableOrder.getSelectionModel().getSelectedItem().getFilePath());
                            updateList(file.toPath(),"order");
                            outConsole("???? ?????????????? ??????????????!!!");
                        } else {
                            outConsole("?????????? ?????????? ???? ??????????????????????!!!");
                        }
                    } catch (NullPointerException e) {
                        outConsole("???????? ???? ??????????????? ?????????? ??????????!");
                    }
                }
                if (event.getClickCount() == 1) {
                    try {
                        File file = new File(tableOrder.getSelectionModel().getSelectedItem().getFilePath());
                        if (file.isDirectory()) {
                            updateList(file.toPath(),"view");
                        } else {
                            outConsole("?????? ????????! ???????????? ??????!");
                        }
                        labelSelOrder.setText("?????????????? "+String.valueOf(tableOrder.getSelectionModel().getSelectedItems().size())+" ????????.");
                    } catch (NullPointerException e) {
                        outConsole("???????? ???? ??????????????? ?????????? ??????????!");
                    }
                }
            }
        });

        tableModelView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    labelSelView.setText("?????????????? "+String.valueOf(tableModelView.getSelectionModel().getSelectedItems().size())+" ????????.");
                }
            }
        });

        tableJob.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                labelSelJob.setText("?????????????? "+String.valueOf(tableJob.getSelectionModel().getSelectedItems().size())+" ????????.");
                if (event.getClickCount() == 1) {
                    try {
                        File file = new File(tableJob.getSelectionModel().getSelectedItem().getFilePath());
                        if (file.isDirectory()) {
                            updateList(file.toPath(),"viewJob");
                        } else {
                            outConsole("?????? ????????! ???????????? ??????!");
                        }
                        //labelSelOrder.setText("?????????????? "+String.valueOf(tableJob.getSelectionModel().getSelectedItems().size())+" ????????.");
                    } catch (NullPointerException e) {
                        outConsole("???????? ???? ??????????????? ?????????? ??????????!");
                    }
                }
                if (event.getClickCount() == 2) {
                    try {
                        File file = new File(tableJob.getSelectionModel().getSelectedItem().getFilePath());
                        if (file.isDirectory()) {
                            fieldDirJob.setText(tableJob.getSelectionModel().getSelectedItem().getFilePath());
                            updateList(file.toPath(),"job");
                            outConsole("???? ?????????????? ??????????????!!!");
                        } else {
                            outConsole("?????????? ?????????? ???? ??????????????????????!!!");
                        }
                    } catch (NullPointerException e) {
                        outConsole("???????? ???? ??????????????? ?????????? ??????????!");
                    }
                }
            }
        });

        tableModelViewJob.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    labelSelViewJob.setText("?????????????? "+String.valueOf(tableModelViewJob.getSelectionModel().getSelectedItems().size())+" ????????.");
                }
            }
        });
    }

    public void outConsole(String message) {
        newLangs.add(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " " + message);
        mainConsole.setItems(newLangs);
    }

    public void updateList(Path path,String table) {
        try {
            if (table.equals("order")) {
                tableOrder.getItems().clear();
                //tableModelView.getItems().clear();
                if (path.getFileName().toString().equals("??????????")) {
                    labelOrderDir.setTextFill(Color.web("#19E619"));
                    labelOrderDir.setText("?????????????? ?????????? [??????????????]");
                    File dir = new File(path.toString());
                    List<Path> list = new ArrayList<>();
                    //System.out.println(dir.listFiles());
                    for (File item : dir.listFiles()){
                        //System.out.println(item.getName());
                        if (item.isFile() | item.getName().equals("+??????????????")) {
                            outConsole("???? ?????????????? ?????????????? ????????????????!");
                        } else {
                            list.add(Paths.get(item.getPath()));
                        }
                    }
                    //Stream<Path> stream = list.stream();;

                    //System.out.println(stream);
                    tableOrder.getItems().addAll(list.stream().map(FileInfo::new).collect(Collectors.toList()));
                    tableOrder.sort();
                    labelOrder.setText("??????????????????: "+tableOrder.getItems().size());
                    labelSelOrder.setText("?????????????? 0 ????????.");
                } else {
                    labelOrderDir.setTextFill(Color.web("#FC1F0F"));
                    labelOrderDir.setText("?????????? [???? ??????????????]");
                    tableOrder.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
                    tableOrder.sort();
                    labelOrder.setText("??????????????????: "+tableOrder.getItems().size());
                    labelSelOrder.setText("?????????????? 0 ????????.");
                }

            }
            if (table.equals("job")) {
                tableJob.getItems().clear();
                //tableModelViewJob.getItems().clear();
                //System.out.println(path.getFileName());
                if (path.getFileName().toString().equals("????????????")) {
                    labelJobDir.setTextFill(Color.web("#19E619"));
                    labelJobDir.setText("?????????????? ?????????? [??????????????]");
                    File dir = new File(path.toString());
                    List<Path> list = new ArrayList<>();
                    //System.out.println(dir.listFiles());
                    for (File item : dir.listFiles()){
                        //System.out.println(item.getName());
                        if (item.isFile() | item.getName().equals("+") | item.getName().equals("PR12") | item.getName().equals("PR28") | item.getName().equals("PR35")) {
                            outConsole("???? ?????????????? ?????????????? ????????????????!");
                        } else {
                            list.add(Paths.get(item.getPath()));
                        }
                    }
                    Stream<Path> stream = list.stream();;
                    tableJob.getItems().addAll(stream.map(FileInfo::new).collect(Collectors.toList()));
                    tableJob.sort();
                    labelJob.setText("??????????????????: "+tableJob.getItems().size());
                    labelSelJob.setText("?????????????? 0 ????????.");
                } else {
                    labelJobDir.setTextFill(Color.web("#FC1F0F"));
                    labelJobDir.setText("?????????????? ?????????? [???? ??????????????]");
                    tableJob.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
                    tableJob.sort();
                    labelJob.setText("??????????????????: "+tableJob.getItems().size());
                    labelSelJob.setText("?????????????? 0 ????????.");
                }

            }
            if (table.equals("view")) {
                tableModelView.getItems().clear();
                tableModelView.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
                tableModelView.sort();
                labelView.setText("??????????????????: "+tableModelView.getItems().size());
                labelSelView.setText("?????????????? 0 ????????.");
            }
            if (table.equals("viewJob")) {
                tableModelViewJob.getItems().clear();
                tableModelViewJob.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
                tableModelViewJob.sort();
                labelViewJob.setText("??????????????????: "+tableModelViewJob.getItems().size());
                labelSelViewJob.setText("?????????????? 0 ????????.");
            }
            File file = new File("config.properties");
            Properties properties = new Properties();
            properties.setProperty("ORDER_PATH", fieldDirOrder.getText());
            properties.setProperty("OPERATOR_PATH", fieldDirJob.getText());
            FileWriter writer = new FileWriter(file);
            properties.store(writer, "?????????????????? ??????????????????");
        } catch (IOException | NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "???? ?????????????? ?????????????????? ??????????????????", ButtonType.OK);
            alert.showAndWait();
        }

    }

}
