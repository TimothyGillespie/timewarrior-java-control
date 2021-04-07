package eu.gillespie.timewarriorcontrol;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Tracking {
    List<Tag> tags;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
