package com.drxlog.java.ordermanager.buffer;

import java.nio.file.Path;

public class MoveFile extends ChangeFile{
    private Path srcPath;
    private Path movePath;

    MoveFile(Path srcPath, Path movePath) {
        this.srcPath = srcPath;
        this.movePath = movePath;
    }

    public Path getSrcPath() {
        return srcPath;
    }

    public void  setSrcPath(Path srcPath) {
        this.srcPath = srcPath;
    }

    public Path getMovePath() {
        return movePath;
    }

    public void  setMovePath(Path movePath) {
        this.movePath = movePath;
    }

    public void make() {
        System.out.println("Это класс MoveFile");
    }

    public void  recover() {

    }
}
