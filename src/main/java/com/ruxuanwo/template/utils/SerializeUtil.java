package com.ruxuanwo.template.utils;

import java.io.*;

/**
 * 序列化
 *
 * @author ruxuanwo
 */
public class SerializeUtil {

    private SerializeUtil() {
    }

    /**
     * 序列化
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        byte[] byt;
        try (
                ByteArrayOutputStream bai = new ByteArrayOutputStream();
                ObjectOutputStream obi = new ObjectOutputStream(bai)
        ) {
            obi.writeObject(obj);
            byt = bai.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byt;
    }

    /**
     * 反序列化
     *
     * @param byt
     * @param <T>
     * @return
     */
    public static <T> T unSerialize(byte[] byt) {
        T t;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(byt);
             ObjectInputStream oii = new ObjectInputStream(bis)) {
            t = (T) oii.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return t;
    }
}
