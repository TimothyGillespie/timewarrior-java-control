package eu.gillespie.test;

import eu.gillespie.test.shared.FileLoader;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestMethods {

    @Test
    void fileReaderWorks() throws IOException {
        assertEquals("test file loaded", FileLoader.load("fileLoaderTest.txt"));
    }
}
