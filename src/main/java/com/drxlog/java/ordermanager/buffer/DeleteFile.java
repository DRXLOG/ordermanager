package com.drxlog.java.ordermanager.buffer;

import java.nio.file.Path;

public class DeleteFile extends ChangeFile{
    private String nameElement;
    private Path srcPath;
    private Path tmpPath;

    DeleteFile(Path srcPath, Path tmpPath) {
        this.srcPath = srcPath.getFileName();
        this.srcPath = srcPath;
        this.tmpPath = tmpPath;
    }

    public Path getSrcPath() {
        return srcPath;
    }

    public void  setSrcPath(Path srcPath) {
        this.srcPath = srcPath;
    }

    public Path getTmpPath() {
        return tmpPath;
    }

    public void  setTmpPath(Path tmpPath) {
        this.tmpPath = tmpPath;
    }

    public void make() {
        System.out.println("Это класс DeleteFile");
    }

    public void  recover() {

    }
}
