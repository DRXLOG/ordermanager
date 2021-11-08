package com.drxlog.java.ordermanager.buffer;

import java.util.ArrayList;
import java.util.List;

public class CircularBuffer {
    private static List list = new ArrayList();

    public static void addElement(Object object){
        list.add(object);
    }

    public static boolean removeLastElement() {
        if (list.size() == 0) {
            return false;
        } else {
            Object object = list.get(list.size()-1);
            if (object instanceof ChangeFile) {
                ((ChangeFile) object).recover();
            }
            list.remove(list.size()-1);
            return true;
        }
    }

    public static void getElement() {
        for (Object item: list) {
            if (item instanceof ChangeFile) {
                ((ChangeFile) item).make();
            }
        }
    }

}
