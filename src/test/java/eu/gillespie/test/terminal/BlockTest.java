package eu.gillespie.test.terminal;

import eu.gillespie.timewarriorcontrol.terminal.SyncTerminalHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockTest {

    @Test
    void testIfTerminalBlocksForShortLine() throws IOException, InterruptedException {
        this.performTestWithFile("terminal/shortLine.txt");
    }
    @Test
    void testIfTerminalBlocksForMediumLine() throws IOException, InterruptedException {
        this.performTestWithFile("terminal/mediumLine.txt");
    }

    @Test
    void testIfTerminalBlocksForLongLine() throws IOException, InterruptedException {
        this.performTestWithFile("terminal/longLine.txt");
    }

    @Test
    void testIfTerminalBlocksForExtremlyLongLine() throws IOException, InterruptedException {
        this.performTestWithFile("terminal/extremelyLongLine.txt");
    }

    @Test
    void testIfTerminalBlocksForLongFiles() throws IOException, InterruptedException {
        this.performTestWithFile("terminal/longFile.txt");
    }

    @Test
    void testIfTerminalBlocksForOneLineJson() throws IOException, InterruptedException {
        this.performTestWithFile("terminal/oneLineJson.json");
    }

    void performTestWithFile(String filePath) throws IOException, InterruptedException {
        SyncTerminalHandler.exec(new String[] {
                "cat",
                getClass().getClassLoader().getResource(filePath).getPath()
        });

        // Asserting that it did not get stuck by trivially passing it after
        assertTrue(true);
    }
}
