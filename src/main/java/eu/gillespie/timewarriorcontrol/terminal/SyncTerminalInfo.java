package eu.gillespie.timewarriorcontrol.terminal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SyncTerminalInfo {
    String terminalOutput;
    String errorOutput;
}
