package com.drxlog.java.ordermanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileInfo {
    private String idName = null;
    private String filePath = null;
    private String filesCount = null;

    public FileInfo() {
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String order) {
        this.idName = order;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String kappa) {
        this.filePath = kappa;
    }

    public String getFilesCount() {
        return filesCount;
    }

    public void setFilesCount(String count) {
        this.filesCount = count;
    }

    public FileInfo(Path path) {
        try {
            this.idName = path.getFileName().toString();
            this.filePath = path.toString();
            if (Files.isDirectory(path)) {
                long a = Files.list(path).count();
                this.filesCount = Long.toString(a);
            }
        } catch (IOException e) {
            WarningDialog.display("Ошибка!","Ошибка загрузки объектов директорий",e.getMessage());
        }

    }
}
