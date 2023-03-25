package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileUtils {
    public InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

//    public static void printInputStream(InputStream is) {
//
//        try (InputStreamReader streamReader =
//                     new InputStreamReader(is, StandardCharsets.UTF_8);
//             BufferedReader reader = new BufferedReader(streamReader)) {
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
