package com.drxlog.java.ordermanager.buffer;

import com.drxlog.java.ordermanager.WarningDialog;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RenameFile extends ChangeFile{
    private Path oldRenamePath;
    private Path renamePath;

    public RenameFile(Path oldRenamePath, Path renamePath) {
        this.oldRenamePath = oldRenamePath;
        this.renamePath = renamePath;
    }

    public Path getOldRenamePath() {
        return oldRenamePath;
    }

    public void  setOldRenamePath(Path oldRenamePath) {
        this.oldRenamePath = oldRenamePath;
    }

    public Path getRenamePath() {
        return renamePath;
    }

    public void  setRenamePath(Path renamePath) {
        this.renamePath = renamePath;
    }

    public void make() {
        System.out.println("Это класс RenameFile");
    }

    public void  recover() {
        if (Files.exists(renamePath)){
            File oldFile = new File(oldRenamePath.toString());
            File newFile = new File(renamePath.toString());
            if(newFile.renameTo(oldFile)){
                System.out.println("Файл "+oldFile.getName()+" успешно переименован в "+newFile.getName());
            }else{
                WarningDialog.display("Ошибка!", "Файл не был переименован!", "Возможно открыт в другой программе!");
            }
        }
    }
}
