package eu.gillespie.test.shared;

import sun.misc.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Scanner;

public class FileLoader {

    public static String load(String filePath) throws IOException {
        ClassLoader classLoader = FileLoader.class.getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }
}
