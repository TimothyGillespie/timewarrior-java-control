package eu.gillespie.timewarriorcontrol;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class Tracking {
    String annotation;
    List<Tag> tags;
    LocalDateTime startTime;
    LocalDateTime endTime;

}
