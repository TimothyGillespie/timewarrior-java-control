package eu.gillespie.timewarriorcontrol.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class SyncTerminalHandler {

    private static final Runtime rt = Runtime.getRuntime();

    public static SyncTerminalInfo exec(String[] command) throws IOException, InterruptedException {
        Process p = rt.exec(command);
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader err = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String inLine = null;
        String errLine = null;

        LinkedList<String> allInLines = new LinkedList<>();
        LinkedList<String> allErrLines = new LinkedList<>();


        while ( (inLine = in.readLine()) != null || (errLine = err.readLine()) != null) {
            if(inLine != null)
                allInLines.add(inLine);

            if(errLine != null)
                allErrLines.add(errLine);
        }

        p.waitFor();

        return new SyncTerminalInfo(
                String.join("\\n", allInLines),
                String.join("\\n", allErrLines)
        );
    }
}
