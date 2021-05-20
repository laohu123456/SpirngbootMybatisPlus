package com.server.utils;

import java.io.*;

public class SerializationUtils {


    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream(1024);
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
        }finally {
            try {
                if(oos != null){
                    oos.close();
                }
                if(baos != null){
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return baos.toByteArray();
    }

    public static Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream ois = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(byteArrayInputStream);
            return ois.readObject();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to deserialize object", ex);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Failed to deserialize object type", ex);
        }finally {
            try {
                if(byteArrayInputStream != null){
                    byteArrayInputStream.close();
                }
                if(ois != null){
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
