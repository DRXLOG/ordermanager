package com.drxlog.java.ordermanager;

import java.nio.file.Path;
import java.util.stream.BaseStream;

public class FileDelete implements BufferInterface {
    private Path tmpDeletePath;
    private Path resetPath;

    public FileDelete(){

    }

    public FileDelete(Path tmpDeletePath, Path resetPath){
        this.tmpDeletePath = tmpDeletePath;
        this.resetPath = resetPath;
    }

    public void action(){
        System.out.println("Файл удалён! " + tmpDeletePath);
    }

    public Object recover(){
        System.out.println("Файл Восстановлен! "+ tmpDeletePath);
        return true;
    }
}
