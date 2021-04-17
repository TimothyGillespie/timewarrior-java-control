package eu.gillespie.test.terminal;

import eu.gillespie.timewarriorcontrol.terminal.SyncTerminalHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BlockTest {

    @Test
    void testIfTerminalBlocksForShortLine() throws IOException, InterruptedException {
        SyncTerminalHandler.exec(new String[] {
                "cat",
                getClass().getClassLoader().getResource("terminal/shortLine.txt").getPath()
        });
    }
    @Test
    void testIfTerminalBlocksForMediumLine() throws IOException, InterruptedException {
        SyncTerminalHandler.exec(new String[] {
                "cat",
                getClass().getClassLoader().getResource("terminal/mediumLine.txt").getPath()
        });
    }

    @Test
    void testIfTerminalBlocksForLongLine() throws IOException, InterruptedException {
        SyncTerminalHandler.exec(new String[] {
                "cat",
                getClass().getClassLoader().getResource("terminal/longLine.txt").getPath()
        });
    }

    @Test
    void testIfTerminalBlocksForExtremlyLongLine() throws IOException, InterruptedException {
        SyncTerminalHandler.exec(new String[] {
                "cat",
                getClass().getClassLoader().getResource("terminal/extremelyLongLine.txt").getPath()
        });
    }
}
