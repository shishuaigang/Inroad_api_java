package com.inroad.apitest.common;

import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class ReadJsonFile {

    public StringBuilder ReadFile(String path) {
        File file = new File(path);
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer;
    }
}
