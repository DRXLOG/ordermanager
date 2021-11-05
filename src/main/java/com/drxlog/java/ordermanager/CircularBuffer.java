package com.drxlog.java.ordermanager;

import java.util.List;
import java.util.Objects;

public class CircularBuffer implements BufferInterface{

    private static List<Object> circularBuffer;

    public static void action(Object object) {
        circularBuffer.add(object);

    }

    public static <T> T recover(Object obj, Class<T> clazz) {
        return (T) obj;
    }

    public static <T> T recover(Object obj) {
        return (T) obj;
    }

}
